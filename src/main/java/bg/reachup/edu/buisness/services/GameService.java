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

    private static final Logger LOGGER = LoggerFactory.getLogger(GameService.class);
    private final GameRepository repository;
    private final PlayerService playerService;
    private final StateService stateService;
    private final GameEndHandlerService gameEndHandlerService;
    private final BotService botService;

    private final Board startupBoard;

    @Autowired
    public GameService(GameRepository repository, PlayerService playerService, StateService stateService, GameEndHandlerService gameEndHandlerService, BotService botService, Board startupBoard) {
        this.repository = repository;
        this.playerService = playerService;
        this.stateService = stateService;
        this.gameEndHandlerService = gameEndHandlerService;
        this.botService = botService;
        this.startupBoard = startupBoard;
    }

    public List<Game> getAll() {
        return repository.findAll();
    }

    public Game getByID(Long id) {
        return repository.findById(id).orElseThrow(() -> new NoSuchGameIDException(id));
    }

    public Game createNewGame(Game game) {
        Player player1 = playerService.searchByUsername(game.getPlayer1().getUsername());
        Player player2 = null;

        if (game.getMode() == GameMode.SINGLEPLAYER && game.getPlayer2() != null) {
            throw new SingleplayerGameException();
        }

        if (game.getMode() == GameMode.MULTIPLAYER && game.getPlayer2() != null) {
            player2 = playerService.searchByUsername(game.getPlayer2().getUsername());
        }

        if (player1.equals(player2)) {
            throw new SamePlayerException();
        }

        game.setPlayer1(player1);
        game.setPlayer2(player2);

        checkForConflict(game);

        Board board = getStartupBoard();

        State state = new State(board, true);
        game.setState(state);
        return repository.save(new

                        Game(
                        game.getMode(),
                        game.getDifficulty(),
                        player1,
                        player2,
                        state
                )
        );
    }

    public Game joinGame(Long id, String playerUsername) {
        Game game = getByID(id);
        if (game.getMode() == GameMode.SINGLEPLAYER) {
            throw new SingleplayerGameException();
        }
        if (game.getPlayer2() != null) {
            throw new GameAlreadyFullException();
        }
        Player player = playerService.searchByUsername(playerUsername);
        game.setPlayer2(player);

        checkForConflict(game);

        return repository.save(game);
    }

    private void checkForConflict(Game game) {
        ExampleMatcher matcher = ExampleMatcher
                .matchingAll()
                .withIncludeNullValues()
                .withIgnorePaths("id", "state");

        Example<Game> example1 = Example.of(
                new Game(
                        game.getMode(),
                        game.getDifficulty(),
                        game.getPlayer1(),
                        game.getPlayer2(),
                        null
                ),
                matcher
        );

        Example<Game> example2 = Example.of(
                new Game(
                        game.getMode(),
                        game.getDifficulty(),
                        game.getPlayer2(),
                        game.getPlayer1(),
                        null
                ),
                matcher
        );

        if (repository.exists(example1) && !repository.findOne(example1).get().getState().isFinished()) {
            throw new DuplicateUnfinishedGameException();
        }

        if (repository.exists(example2) && !repository.findOne(example2).get().getState().isFinished()) {
            throw new DuplicateUnfinishedGameException();
        }
    }

    public void deleteByID(Long gameID) {
        Game game = getByID(gameID);
        repository.delete(game);
    }

    /*
     * Executes a move in a game with teh specified {gameID}
     *
     * Validation is done for:
     * - existence of game with the specified ID
     * - player executing the move is on turn
     *
     * 1. The new state after executing the action is generated by the StateService#executeAction method
     * 2. If the new state is final - the statistics of the players are updated and the state finished flag is activated
     * 3. The new state of the game is saved to the database with the StateService#update method
     * 4. In a singleplayer game, the BotService#botMakeMove is executed so the bot can play his move
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
        stateService.updateState(gameState.getId(), newGameState);

        gameEndHandlerService.checkForGameEnd(game);

        LOGGER.info("%n%s%n".formatted(action));
        LOGGER.info("%n-------------------%n%s%n-------------------".formatted(game));

        botService.botMakeMove(game);

        return game;
    }

    public Board getStartupBoard() {
        return new Board(startupBoard);
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
                                    getStartupBoard(),
                                    true
                            )
                    ),
                    new Game(
                            GameMode.MULTIPLAYER,
                            playerService.searchByID(3L),
                            playerService.searchByID(2L),
                            new State(
                                    getStartupBoard(),
                                    true
                            )
                    ),
                    new Game(
                            GameMode.SINGLEPLAYER,
                            Difficulty.NORMAL,
                            playerService.searchByID(1L),
                            new State(
                                    getStartupBoard(),
                                    true
                            )
                    )
            ));
        }
    }
}