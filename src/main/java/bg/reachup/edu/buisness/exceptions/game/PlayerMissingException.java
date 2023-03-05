package bg.reachup.edu.buisness.exceptions.game;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class PlayerMissingException extends ResponseStatusException {
    public PlayerMissingException() {
        super(HttpStatus.BAD_REQUEST, "Cannot start game without an opponent!");
    }
}
