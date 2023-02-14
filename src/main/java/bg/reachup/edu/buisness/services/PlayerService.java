package bg.reachup.edu.buisness.services;

import bg.reachup.edu.buisness.exceptions.players.*;
import bg.reachup.edu.data.entities.Player;
import bg.reachup.edu.data.repositories.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

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

    public void loadPlaceholderData() {
        if (!repository.findAll().isEmpty()) {
            throw new PlayerTableNotEmptyException();
        }
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

    public Player registerPlayer(Player player) {
        try {
            if (player.getUsername() == null || player.getUsername().isBlank()) {
                throw new MissingUsernameException();
            }
            if (player.getEmail() == null || player.getEmail().isBlank()) {
                throw new MissingEmailException();
            }
            return repository.save(player);
        } catch (DataIntegrityViolationException e) {
            throw new PlayerAlreadyExistsException();
        }
    }

    public Player searchByUsername(String player) {
        return repository.findByUsername(player);
    }
}
