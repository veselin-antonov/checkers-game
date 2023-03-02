package bg.reachup.edu.buisness.services;

import bg.reachup.edu.buisness.Pair;
import bg.reachup.edu.buisness.exceptions.game.DuplicateUnfinishedGameException;
import bg.reachup.edu.buisness.exceptions.game.GameAlreadyCompletedException;
import bg.reachup.edu.buisness.exceptions.game.IncorrectExecutorException;
import bg.reachup.edu.buisness.exceptions.game.NoSuchGameIDException;
import bg.reachup.edu.data.converters.BoardConverter;
import bg.reachup.edu.data.entities.*;
import bg.reachup.edu.data.repositories.GameRepository;
import bg.reachup.edu.presentation.dtos.ActionDTO;
import bg.reachup.edu.presentation.mappers.ActionMapper;
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
    private final ActionMapper actionMapper;
    private final BoardConverter boardConverter;
    private static final Logger LOGGER = LoggerFactory.getLogger(GameService.class);

    @Autowired
    public GameService(GameRepository repository, PlayerService playerService, StateService stateService, ActionMapper actionMapper, BoardConverter boardConverter) {
        this.repository = repository;
        this.playerService = playerService;
        this.stateService = stateService;
        this.actionMapper = actionMapper;
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

    public Game makeMove(Long gameID, ActionDTO actionDTO) {
        Game game = repository.findById(gameID).orElseThrow(NoSuchGameIDException::new);

        if (game.getState().isFinished()) {
            throw new GameAlreadyCompletedException();
        }

        Action action = actionMapper.toEntity(actionDTO);
        State gameState = game.getState();

        Player actionExecutor = playerService.searchByUsername(action.executor());
        Player currentPlayer = gameState.isPlayer1Turn() ? game.getPlayer1() : game.getPlayer2();
        if (!actionExecutor.equals(currentPlayer)) {
            throw new IncorrectExecutorException();
        }

        State newGameState = stateService.executeAction(action, gameState);
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

        if (
                game.getMode() == GameMode.SINGLEPLAYER
                        && !game.getState().isPlayer1Turn()
                        && !game.getState().isFinished()
        ) {
            State afterBotMoves = findBestMove(newGameState, game.getDifficulty().value()).value1();
            stateService.updateState(gameState, afterBotMoves);

        }

        repository.save(game);

        LoggerFactory.getLogger(Game.class).info("%n%s%n".formatted(action));
        LoggerFactory.getLogger(GameService.class).info("%n-------------------%n%s%n-------------------".formatted(game));
        return game;
    }


    public Pair<State, Integer> findBestMove(State state, int depth) {
        return minMaxAB(state, depth, state.getMinStateScore(), state.getMaxStateScore());
    }

    private Pair<State, Integer> minMaxAB(State state, int depth, int alpha, int beta) {
        if (stateService.isFinal(state)) {
            return new Pair<>(state, stateService.evaluate(state));
        }
        if (depth == 0) {
            return new Pair<>(state, stateService.evaluate(state));
        }
        List<State> children = stateService.getChildren(state, null);
        State firstChild = children.get(0);
        Pair<State, Integer> bestChoice = new Pair<>(
                firstChild,
                minMaxAB(firstChild, depth - 1, alpha, beta).value2()
        );
        for (int i = 0; i < children.size(); i++) {
            State child = children.get(i);
            Pair<State, Integer> possibleBestChoice = new Pair<>(
                    child,
                    minMaxAB(child, depth - 1, alpha, beta).value2()
            );
            bestChoice = betterChoice(bestChoice, possibleBestChoice, state.isPlayer1Turn()) ?
                    bestChoice : possibleBestChoice;
            if (state.isPlayer1Turn()) {
                alpha = Math.max(alpha, bestChoice.value2());
            } else {
                beta = Math.min(beta, bestChoice.value2());
            }
            if (alpha >= beta) {
                break;
            }
        }
        return bestChoice;
    }

    private boolean betterChoice(Pair<State, Integer> choice1, Pair<State, Integer> choice2, boolean isMaximisingPlayersTurn) {
        if (choice1 == null) {
            return false;
        } else if (isMaximisingPlayersTurn) {
            return choice1.value2() > choice2.value2();
        } else {
            return choice1.value2() < choice2.value2();
        }
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
