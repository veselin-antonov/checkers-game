package bg.reachup.edu.buisness.exceptions.game;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class GameAlreadyCompletedException extends ResponseStatusException {
    public GameAlreadyCompletedException() {
        super(HttpStatus.BAD_REQUEST, "Game with specified id has already been completed");
    }
}
