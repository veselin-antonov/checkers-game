package bg.reachup.edu.buisness.services;

import bg.reachup.edu.buisness.exceptions.game.*;
import bg.reachup.edu.data.converters.BoardConverter;
import bg.reachup.edu.data.entities.*;
import bg.reachup.edu.data.repositories.GameRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class GameService {

    private final GameRepository repository;
    private final PlayerService playerService;
    private final StateService stateService;
    private final BoardConverter boardConverter;
    private static final Logger LOGGER = LoggerFactory.getLogger(GameService.class);

    @Autowired
    public GameService(GameRepository repository, PlayerService playerService, StateService stateService, BoardConverter boardConverter) {
        this.repository = repository;
        this.playerService = playerService;
        this.stateService = stateService;
        this.boardConverter = boardConverter;
    }

    public List<Game> getAll() {
        return repository.findAll();
    }

    public Game getByID(Long id) {
        Game game = repository.findById(id).orElseThrow(NoSuchGameIDException::new);
        LOGGER.info("%n-------------------%n%s%n-------------------".formatted(game));
        return game;
    }

    public Game createNewGame(Game game) {
        Player player1 = playerService.searchByUsername(game.getPlayer1().getUsername());
        Player player2 = null;

        if (game.getMode() == GameMode.MULTIPLAYER) {
            player2 = playerService.searchByUsername(game.getPlayer2().getUsername());

            ExampleMatcher ignoringMatcher = ExampleMatcher
                    .matchingAll()
                    .withIgnorePaths("isPlayer1Turn");

            Example<Game> example1 = Example.of(
                    new Game(
                            null,
                            player1,
                            player2,
                            null
                    ),
                    ignoringMatcher);

            Example<Game> example2 = Example.of(
                    new Game(
                            null,
                            player2,
                            player1,
                            null
                    ),
                    ignoringMatcher);

            if (repository.exists(example1) || repository.exists(example2)) {
                throw new DuplicateUnfinishedGameException();
            }
        }

        Board board = boardConverter.convertToEntityAttribute(
                """
                        _,O,_,O,_,O,_,O
                        O,_,O,_,O,_,O,_
                        _,O,_,O,_,O,_,O
                        _,_,_,_,_,_,_,_
                        _,_,_,_,_,_,_,_
                        X,_,X,_,X,_,X,_
                        _,X,_,X,_,X,_,X
                        X,_,X,_,X,_,X,_
                        """
        );
        State state = new State(board, true);
        game.setState(state);
        return repository.save(new Game(
                        game.getMode(),
                        game.getDifficulty(),
                        player1,
                        player2,
                        state
                )
        );
    }

    /*
     * Executes a move in a game with teh specified {gameID}
     *
     * Validation is done for:
     * - existence of game with the specified ID
     * - player executing the move is on turn
     *
     * 1. The new state after executing the action is generated by the StateService#executeAction method
     * 2. In a singleplayer game, if possible - the bot plays his move overwriting the new state.
     * 3. If the new state is final - the statistics of the players are updated and the state finished flag is activated
     * 4. The state of the game is updated with the StateService#update method
     * 5. The game gets updated in the database
     */
    public Game makeMove(Game game, Action action) {
        if (game.getState().isFinished()) {
            throw new GameAlreadyCompletedException();
        }

        if (game.getMode() == GameMode.MULTIPLAYER && game.getPlayer2() == null) {
            throw new PlayerMissingException();
        }

        State gameState = game.getState();

        Player actionExecutor = playerService.searchByUsername(action.executor());
        Player currentPlayer = gameState.isPlayer1Turn() ? game.getPlayer1() : game.getPlayer2();
        if (!actionExecutor.equals(currentPlayer)) {
            throw new IncorrectExecutorException();
        }

        State newGameState = stateService.executeAction(action, gameState);

        if (
                game.getMode() == GameMode.SINGLEPLAYER
                        && !game.getState().isPlayer1Turn()
                        && !game.getState().isFinished()
        ) {
            newGameState = stateService.findBestMove(newGameState, game.getDifficulty().value());
            stateService.updateState(gameState, newGameState);
        }

        if (stateService.isFinal(newGameState)) {
            int gameEvaluation = stateService.evaluate(newGameState);
            if (gameEvaluation > 0) {
                game.getPlayer1().giveWin();
                game.getPlayer2().giveLoss();
            } else if (gameEvaluation < 0) {
                game.getPlayer1().giveLoss();
                game.getPlayer2().giveWin();
            } else {
                game.getPlayer1().giveTie();
                game.getPlayer2().giveTie();
            }
            newGameState.setFinished(true);
        }

        stateService.updateState(gameState, newGameState);
        repository.save(game);

        LoggerFactory.getLogger(Game.class).info("%n%s%n".formatted(action));
        LoggerFactory.getLogger(GameService.class).info("%n-------------------%n%s%n-------------------".formatted(game));
        return game;
    }

    public Game joinGame(Long id, String playerUsername) {
        Game game = getByID(id);
        Player player = playerService.searchByUsername(playerUsername);
        if (game.getMode() == GameMode.SINGLEPLAYER) {
            throw new SingleplayerGameException();
        }
        if (game.getPlayer2() != null) {
            throw new GameAlreadyFullException();
        }
        game.setPlayer2(player);
        return repository.save(game);
    }

    @PostConstruct
    private void loadTestData() {
        if (repository.count() == 0) {
            repository.saveAll(List.of(
                    new Game(
                            GameMode.MULTIPLAYER,
                            playerService.searchByID(1L),
                            playerService.searchByID(2L),
                            new State(
                                    boardConverter.convertToEntityAttribute("_,X,_,X,_,X,_,X"),
                                    true
                            )
                    ),
                    new Game(
                            GameMode.MULTIPLAYER,
                            playerService.searchByID(3L),
                            playerService.searchByID(2L),
                            new State(
                                    boardConverter.convertToEntityAttribute("_,Oo,_,O,_,O,_,O"),
                                    true
                            )
                    ),
                    new Game(
                            GameMode.SINGLEPLAYER,
                            Difficulty.NORMAL,
                            playerService.searchByID(1L),
                            new State(
                                    boardConverter.convertToEntityAttribute("_,X,_,X,_,Xx,_,X"),
                                    true
                            )
                    )
            ));
        }
    }
}
