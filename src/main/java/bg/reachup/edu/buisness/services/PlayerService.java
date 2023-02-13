package bg.reachup.edu.buisness.services;

import bg.reachup.edu.buisness.exceptions.PlayerAlreadyExistsException;
import bg.reachup.edu.buisness.exceptions.PlayerIDNotFoundException;
import bg.reachup.edu.data.entities.Player;
import bg.reachup.edu.data.entities.repositories.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.Optional;

@Service
public class PlayerService {
    @Autowired
    PlayerRepository repository;

    public List<Player> getAllPlayers() {
        return repository.findAll();
    }

    public Player searchByID(Long id) {
        Optional<Player> byId = repository.findById(id);
        if (byId.isEmpty()) {
            throw new PlayerIDNotFoundException(id);
        }
        return byId.get();
    }

    public boolean loadPlaceholderData() {
        if (!repository.findAll().isEmpty()) {
            return false;
        }
        repository.saveAll(List.of(
                        new Player(
                                "ivan@example.com",
                                10
                        ),
                        new Player(
                                "gosho@example.com",
                                7
                        ),
                        new Player(
                                "pesho_1997@example.com",
                                81
                        )
                )
        );
        return true;
    }

    public Player registerPlayer(Player player) {
        try {
            return repository.save(player);
        } catch (RuntimeException e) {
            throw new PlayerAlreadyExistsException();
        }
    }
}
