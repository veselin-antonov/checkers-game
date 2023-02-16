package bg.reachup.edu.buisness.services;

import bg.reachup.edu.buisness.Board;
import bg.reachup.edu.buisness.exceptions.game.DuplicateUnfinishedGameException;
import bg.reachup.edu.buisness.exceptions.game.GameTableNotEmptyException;
import bg.reachup.edu.buisness.exceptions.game.NoSuchGameIDException;
import bg.reachup.edu.data.dtos.GameDTO;
import bg.reachup.edu.data.entities.Game;
import bg.reachup.edu.data.mappers.GameMapper;
import bg.reachup.edu.data.repositories.GameRepository;
import bg.reachup.edu.data.repositories.PlayerRepository;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GameService {

    private final GameRepository repository;
    private final GameMapper mapper;
    private final PlayerRepository playerRepository;


    @Autowired
    public GameService(GameRepository repository, GameMapper mapper, PlayerRepository playerRepository) {
        this.repository = repository;
        this.mapper = mapper;
        this.playerRepository = playerRepository;
    }

    public void insertTestData() {
        if (repository.count() > 0) {
            throw new GameTableNotEmptyException();
        }
        repository.saveAll(List.of(
                new Game(
                        playerRepository.findById(1L).get(),
                        playerRepository.findById(2L).get(),
                        Board.parseFromString("_,X,_,X,_,X,_,X"),
                        true,
                        false),
                new Game(
                        playerRepository.findById(3L).get(),
                        playerRepository.findById(2L).get(),
                        Board.parseFromString("_,Oo,_,O,_,O,_,O"),
                        true,
                        false),
                new Game(
                        playerRepository.findById(1L).get(),
                        playerRepository.findById(3L).get(),
                        Board.parseFromString("_,X,_,X,_,Xx,_,X"),
                        true,
                        false)
        ));
    }

    public List<GameDTO> getAll() {
        return repository.findAll().stream().map(mapper::toDTO).toList();
    }

    public GameDTO getByID(Long id) {
        Optional<Game> toReturn = repository.findById(id);
        if (toReturn.isEmpty()) {
            throw new NoSuchGameIDException();
        }
        LoggerFactory.getLogger(GameService.class).info(toReturn.get().toString());
        return mapper.toDTO(toReturn.get());
    }

    private Board getStartSetup() {
        return Board.parseFromString("_,O,_,O,_,O,_,O\nO,_,O,_,O,_,O,_\n_,O,_,O,_,O,_,O\n_,_,_,_,_,_,_,_\n_,_,_,_,_,_,_,_\nX,_,X,_,X,_,X,_\n_,X,_,X,_,X,_,X\nX,_,X,_,X,_,X,_");
    }

    public GameDTO createNewGame(String player1, String player2) {
        if (repository.existsByPlayer1UsernameAndPlayer2UsernameAndIsFinished(player1, player2, false)
                || repository.existsByPlayer1UsernameAndPlayer2UsernameAndIsFinished(player2, player1, false)) {
            throw new DuplicateUnfinishedGameException();
        }
        return mapper.toDTO(
                repository.save(new Game(
                        playerRepository.findByUsername(player1),
                        playerRepository.findByUsername(player2),
                        getStartSetup(),
                        true,
                        false
                ))
        );
    }


//    public Game makeMove(Action move) {
//
//    }
}
