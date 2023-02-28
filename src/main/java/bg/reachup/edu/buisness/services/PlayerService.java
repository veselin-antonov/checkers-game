package bg.reachup.edu.buisness.services;

import bg.reachup.edu.buisness.exceptions.players.NoSuchPlayerIDException;
import bg.reachup.edu.buisness.exceptions.players.NoSuchUsernameFoundException;
import bg.reachup.edu.buisness.exceptions.players.PlayerEmailAlreadyExistsException;
import bg.reachup.edu.buisness.exceptions.players.PlayerUsernameAlreadyExistsException;
import bg.reachup.edu.data.entities.Player;
import bg.reachup.edu.data.repositories.PlayerRepository;
import bg.reachup.edu.presentation.mappers.PlayerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;

@Service
public class PlayerService {
    final PlayerRepository repository;
    final PlayerMapper mapper;

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

    public Player registerPlayer(Player player) {
        ExampleMatcher playerMatcher = ExampleMatcher
                .matchingAny()
                .withIgnoreNullValues()
                .withIgnorePaths("gamesPlayed")
                .withIgnorePaths("id")
                .withStringMatcher(ExampleMatcher.StringMatcher.EXACT);
        Example<Player> playerExample = Example.of(player, playerMatcher);
        List<Player> playerOptional = repository.findAll(playerExample);
        if(playerOptional.isEmpty()) {
            player.setGamesPlayed(0);
            return repository.save(player);
        }
        Player existingPlayer = playerOptional.get(0);
        if (existingPlayer.getUsername().equals(player.getUsername())) {
            throw new PlayerUsernameAlreadyExistsException();
        } else {
            throw new PlayerEmailAlreadyExistsException();
        }
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
