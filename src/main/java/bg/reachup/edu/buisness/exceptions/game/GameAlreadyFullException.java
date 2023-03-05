package bg.reachup.edu.buisness.exceptions.game;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class GameAlreadyFullException extends ResponseStatusException {
    public GameAlreadyFullException() {
        super(HttpStatus.BAD_REQUEST, "The game already has two players!");
    }
}
