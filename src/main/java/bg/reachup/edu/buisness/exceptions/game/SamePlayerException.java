package bg.reachup.edu.buisness.exceptions.game;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class SamePlayerException extends ResponseStatusException {
    public SamePlayerException() {
        super(HttpStatus.BAD_REQUEST, "Player cannot play against himself!");
    }
}
