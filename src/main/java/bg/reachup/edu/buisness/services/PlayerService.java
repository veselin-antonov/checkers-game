package bg.reachup.edu.buisness.services;

import bg.reachup.edu.buisness.exceptions.players.MissingEmailException;
import bg.reachup.edu.buisness.exceptions.players.MissingUsernameException;
import bg.reachup.edu.buisness.exceptions.players.NoSuchPlayerIDException;
import bg.reachup.edu.buisness.exceptions.players.NoSuchUsernameFoundException;
import bg.reachup.edu.data.dtos.PlayerDTO;
import bg.reachup.edu.data.entities.Player;
import bg.reachup.edu.data.mappers.PlayerMapper;
import bg.reachup.edu.data.repositories.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class PlayerService {
    PlayerRepository repository;
    PlayerMapper mapper;

    @Autowired
    public PlayerService(PlayerRepository repository, PlayerMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public List<Player> getAllPlayers() {
        return repository.findAll();
    }

    public Player searchByID(Long id) {
        return repository.findById(id).orElseThrow(NoSuchPlayerIDException::new);
    }

    public Player registerPlayer(PlayerDTO playerDTO) {
        if (playerDTO.username() == null || playerDTO.username().isBlank()) {
            throw new MissingUsernameException();
        }
        if (playerDTO.email() == null || playerDTO.email().isBlank()) {
            throw new MissingEmailException();
        }
        Player player = mapper.toEntity(playerDTO);
        return repository.save(player);
    }

    public Player searchByUsername(String player) {
        return repository.findByUsername(player).orElseThrow(NoSuchUsernameFoundException::new);
    }

    @PostConstruct
    private void loadTestData() {
        if (repository.count() == 0) {
            repository.saveAll(List.of(
                            new Player(
                                    "Ivancho_07",
                                    "ivan@example.com",
                                    10
                            ),
                            new Player(
                                    "xX_Gosho_Xx",
                                    "gosho@example.com",
                                    7
                            ),
                            new Player(
                                    "Petar4o",
                                    "pesho_1997@example.com",
                                    81
                            )
                    )
            );
        }
    }
}
