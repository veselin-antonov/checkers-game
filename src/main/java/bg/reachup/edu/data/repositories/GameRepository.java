package bg.reachup.edu.data.repositories;

import bg.reachup.edu.data.entities.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {
    boolean existsByPlayer1UsernameAndPlayer2UsernameAndIsFinishedIs(String player1, String player2, boolean isFinished);
}
