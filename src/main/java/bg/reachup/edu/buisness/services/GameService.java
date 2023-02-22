package bg.reachup.edu.buisness.services;

import bg.reachup.edu.buisness.Action;
import bg.reachup.edu.buisness.Board;
import bg.reachup.edu.buisness.exceptions.GameAlreadyCompletedException;
import bg.reachup.edu.buisness.exceptions.game.DuplicateUnfinishedGameException;
import bg.reachup.edu.buisness.exceptions.game.IncorrectExecutorException;
import bg.reachup.edu.buisness.exceptions.game.NoSuchGameIDException;
import bg.reachup.edu.data.converters.BoardConverter;
import bg.reachup.edu.data.dtos.ActionDTO;
import bg.reachup.edu.data.entities.Game;
import bg.reachup.edu.data.entities.Player;
import bg.reachup.edu.data.entities.State;
import bg.reachup.edu.data.mappers.ActionMapper;
import bg.reachup.edu.data.repositories.GameRepository;
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
        LoggerFactory.getLogger(GameService.class).info("%n-------------------%n%s%n-------------------".formatted(repository.findById(id).orElseThrow(NoSuchGameIDException::new)));
        return repository.findById(id).orElseThrow(NoSuchGameIDException::new);
    }

    public Game createNewGame(String player1Username, String player2Username) {
        Player player1 = playerService.searchByUsername(player1Username);
        Player player2 = playerService.searchByUsername(player2Username);

        ExampleMatcher ignoringMatcher = ExampleMatcher
                .matchingAll()
                .withIgnorePaths("isPlayer1Turn");

        Example<Game> example1 = Example.of(
                new Game(
                        player1,
                        player2,
                        null,
                        false,
                        false
                ),
                ignoringMatcher);

        Example<Game> example2 = Example.of(
                new Game(
                        player2,
                        player1,
                        null,
                        false,
                        false
                ),
                ignoringMatcher);

        if (repository.exists(example1) || repository.exists(example2)) {
            throw new DuplicateUnfinishedGameException();
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
        return repository.save(new Game(
                        player1,
                        player2,
                        state,
                        true,
                        false
                )
        );
    }

    public Game makeMove(Long gameID, ActionDTO actionDTO) {
        Game game = repository.findById(gameID).orElseThrow(NoSuchGameIDException::new);

        if (game.isFinished()) {
            throw new GameAlreadyCompletedException();
        }

        Action action = actionMapper.toEntity(actionDTO);

        Player actionExecutor = playerService.searchByUsername(action.executor());
        Player currentPlayer = game.isPlayer1Turn() ? game.getPlayer1() : game.getPlayer2();
        if (!actionExecutor.equals(currentPlayer)) {
            throw new IncorrectExecutorException();
        }

        State gameState = game.getState();
        State newGameState = stateService.executeAction(action, gameState);
        game.setState(newGameState);
        repository.save(game);
        LoggerFactory.getLogger(Game.class).info("%n%s%n".formatted(action));
        LoggerFactory.getLogger(GameService.class).info("%n-------------------%n%s%n-------------------".formatted(game));
        return game;
    }

    @PostConstruct
    private void loadTestData() {
        if (repository.count() == 0) {
            repository.saveAll(List.of(
                    new Game(
                            playerService.searchByID(1L),
                            playerService.searchByID(2L),
                            new State(
                                    boardConverter.convertToEntityAttribute("_,X,_,X,_,X,_,X"),
                                    true
                            ),
                            true,
                            false),
                    new Game(
                            playerService.searchByID(3L),
                            playerService.searchByID(2L),
                            new State(
                                    boardConverter.convertToEntityAttribute("_,Oo,_,O,_,O,_,O"),
                                    true
                            ),
                            true,
                            false),
                    new Game(
                            playerService.searchByID(1L),
                            playerService.searchByID(3L),
                            new State(
                                    boardConverter.convertToEntityAttribute("_,X,_,X,_,Xx,_,X"),
                                    true
                            ),
                            true,
                            false)
            ));
        }
    }
}
