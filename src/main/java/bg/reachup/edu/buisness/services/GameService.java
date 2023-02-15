package bg.reachup.edu.buisness.services;

import bg.reachup.edu.buisness.Action;
import bg.reachup.edu.buisness.Board;
import bg.reachup.edu.buisness.exceptions.game.DuplicateUnfinishedGameException;
import bg.reachup.edu.buisness.exceptions.game.GameTableNotEmptyException;
import bg.reachup.edu.buisness.exceptions.game.NoSuchGameIDException;
import bg.reachup.edu.data.entities.Game;
import bg.reachup.edu.data.repositories.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GameService {

    private final GameRepository repository;
    private final PlayerService playerService;


    @Autowired
    public GameService(GameRepository repository, PlayerService playerService) {
        this.repository = repository;
        this.playerService = playerService;
    }

    public void insertTestData() {
        if (repository.count() > 0) {
            throw new GameTableNotEmptyException();
        }
        repository.saveAll(List.of(
                new Game(
                    playerService.searchByID(1L),
                    playerService.searchByID(2L),
                    Board.parseFromString("_,X,_,X,_,X,_,X"),
                    true,
                    false),
                new Game(
                        playerService.searchByID(3L),
                        playerService.searchByID(2L),
                        Board.parseFromString("_,Oo,_,O,_,O,_,O"),
                        true,
                        false),
                new Game(
                        playerService.searchByID(1L),
                        playerService.searchByID(3L),
                        Board.parseFromString("_,X,_,X,_,Xx,_,X"),
                        true,
                        false)
        ));
    }

    public List<Game> getAll() {
        return repository.findAll();
    }

    public Game getByID(Long id) {
        Optional<Game> toReturn = repository.findById(id);
        if (toReturn.isPresent()) {
            throw new NoSuchGameIDException();
        }
        return toReturn.get();
    }

    private Board getStartSetup() {
        return Board.parseFromString("_,O,_,O,_,O,_,O\nO,_,O,_,O,_,O,_\n_,O,_,O,_,O,_,O\n_,_,_,_,_,_,_,_\n_,_,_,_,_,_,_,_\nX,_,X,_,X,_,X,_\n_,X,_,X,_,X,_,X\nX,_,X,_,X,_,X,_");
    }

    public Game createNewGame(String player1, String player2) {
        if (repository.existsByPlayer1UsernameAndPlayer2UsernameAndIsFinished(player1, player2, false)
            || repository.existsByPlayer1UsernameAndPlayer2UsernameAndIsFinished(player2, player1, false)) {
            throw new DuplicateUnfinishedGameException();
        }
        return repository.save(new Game(
                playerService.searchByUsername(player1),
                playerService.searchByUsername(player2),
                getStartSetup(),
                true,
                false
        ));
    }



//    public Game makeMove(Action move) {
//
//    }
}
