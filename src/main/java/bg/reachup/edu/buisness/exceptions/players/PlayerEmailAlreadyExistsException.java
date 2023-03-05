package bg.reachup.edu.buisness.exceptions.players;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class PlayerEmailAlreadyExistsException extends ResponseStatusException {
    public PlayerEmailAlreadyExistsException() {
        super(HttpStatus.CONFLICT, "Player with such email already exists");
    }
}