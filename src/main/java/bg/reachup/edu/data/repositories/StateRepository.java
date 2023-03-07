package bg.reachup.edu.data.repositories;

import bg.reachup.edu.data.entities.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("stateRepository")
public interface StateRepository extends JpaRepository<State, Long> {

}
