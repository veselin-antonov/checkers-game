package bg.reachup.edu.buisness.exceptions.players;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class PlayerUsernameAlreadyExistsException extends ResponseStatusException {
    public PlayerUsernameAlreadyExistsException() {
        super(HttpStatus.CONFLICT, "Player with such username already exists");
    }
}
