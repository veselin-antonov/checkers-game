package bg.reachup.edu.buisness.services;

import bg.reachup.edu.buisness.Action;
import bg.reachup.edu.buisness.Board;
import bg.reachup.edu.buisness.State;
import bg.reachup.edu.buisness.exceptions.GameAlreadyCompletedException;
import bg.reachup.edu.buisness.exceptions.game.DuplicateUnfinishedGameException;
import bg.reachup.edu.buisness.exceptions.game.IncorrectExecutorException;
import bg.reachup.edu.buisness.exceptions.game.NoSuchGameIDException;
import bg.reachup.edu.data.converters.BoardConverter;
import bg.reachup.edu.data.dtos.ActionDTO;
import bg.reachup.edu.data.entities.Game;
import bg.reachup.edu.data.entities.Player;
import bg.reachup.edu.data.mappers.ActionMapper;
import bg.reachup.edu.data.repositories.GameRepository;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.SpringProperties;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameService {

    private final GameRepository repository;
    private final ActionMapper actionMapper;
    private final PlayerService playerService;
    private final BoardConverter boardConverter;

    @Autowired
    public GameService(GameRepository repository, ActionMapper actionMapper, PlayerService playerService, BoardConverter boardConverter) {
        this.repository = repository;
        this.actionMapper = actionMapper;
        this.playerService = playerService;
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
        String boardString = "_,O,_,O,_,O,_,O\nO,_,O,_,O,_,O,_\n_,O,_,O,_,O,_,O\n_,_,_,_,_,_,_,_\n_,_,_,_,_,_,_,_\nX,_,X,_,X,_,X,_\n_,X,_,X,_,X,_,X\nX,_,X,_,X,_,X,_";
        return repository.save(new Game(
                        player1,
                        player2,
                        boardConverter.convertToEntityAttribute(boardString),
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

        State gameState = new State(game.getBoard(), game.isPlayer1Turn());
        State newGameState = gameState.executeAction(action);
        game.setBoard(newGameState.getBoard());
        game.setPlayer1Turn(!game.isPlayer1Turn());
        game.setFinished(newGameState.isFinal());
        repository.save(game);
        LoggerFactory.getLogger(Game.class).info("%n%s%n".formatted(action));
        LoggerFactory.getLogger(GameService.class).info("%n-------------------%n%s%n-------------------".formatted(game));
        return game;
    }
}
