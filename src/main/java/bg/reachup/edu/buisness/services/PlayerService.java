package bg.reachup.edu.buisness.services;

import bg.reachup.edu.buisness.exceptions.players.*;
import bg.reachup.edu.data.dtos.PlayerDTO;
import bg.reachup.edu.data.entities.Player;
import bg.reachup.edu.data.mappers.PlayerMapper;
import bg.reachup.edu.data.repositories.PlayerRepository;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlayerService {
    PlayerRepository repository;
    PlayerMapper mapper;

    @Autowired
    public PlayerService(PlayerRepository repository, PlayerMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public List<PlayerDTO> getAllPlayers() {
        return repository.findAll().stream().map(mapper::toDTO).toList();
    }

    public PlayerDTO searchByID(Long id) {
        Optional<Player> byId = repository.findById(id);
        if (byId.isEmpty()) {
            throw new NoSuchPlayerIDException();
        }
        return mapper.toDTO(byId.get());
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

    public PlayerDTO registerPlayer(PlayerDTO playerDTO) {
        try {
            if (playerDTO.username() == null || playerDTO.username().isBlank()) {
                throw new MissingUsernameException();
            }
            if (playerDTO.email() == null || playerDTO.email().isBlank()) {
                throw new MissingEmailException();
            }
            Player player = new Player();
            mapper.updateEntity(playerDTO, player);
            LoggerFactory.getLogger("logger").info(player.toString());
            return mapper.toDTO(repository.save(player));
        } catch (DataIntegrityViolationException e) {
            throw new PlayerAlreadyExistsException();
        }
    }

    public Player searchByUsername(String player) {
        return repository.findByUsername(player);
    }
}
