package bg.reachup.edu.data.repositories;

import bg.reachup.edu.data.entities.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface GameRepository extends JpaRepository<Game, Long>, QueryByExampleExecutor<Game> {

}
