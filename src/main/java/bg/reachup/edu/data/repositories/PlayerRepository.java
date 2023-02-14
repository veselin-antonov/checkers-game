package bg.reachup.edu.data.repositories;

import bg.reachup.edu.data.entities.Player;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerRepository extends JpaRepository<Player, Long> {
    Player findByUsername(String username);
}
