package bg.reachup.edu.buisness.services;

import bg.reachup.edu.buisness.exceptions.game.DuplicateUnfinishedGameException;
import bg.reachup.edu.data.entities.Game;
import bg.reachup.edu.data.entities.Player;
import bg.reachup.edu.data.repositories.GameRepository;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.SpringProperties;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

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
        repository.save(new Game(
                playerService.searchByID(1L),
                playerService.searchByID(2L),
                "_,X,_,X,_,X,_,X",
                true,
                false
        ));
    }

    public List<Game> getAll() {
        return repository.findAll();
    }

    @Value("${checkers.game.initial-setup}")
    private String getStartSetup() {
        return "_,O,_,O,_,O,_,O\nO,_,O,_,O,_,O,_\n_,O,_,O,_,O,_,O\n_,_,_,_,_,_,_,_\n_,_,_,_,_,_,_,_\nX,_,X,_,X,_,X,_\n_,X,_,X,_,X,_,X\nX,_,X,_,X,_,X,_";
    }

    public Game createNewGame(String player1, String player2) {
        if (repository.existsByPlayer1UsernameAndPlayer2UsernameAndIsFinishedIs(player1, player2, false)) {
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
}
