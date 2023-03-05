package bg.reachup.edu.buisness.exceptions.game;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class SingleplayerGameException extends ResponseStatusException {
    public SingleplayerGameException() {
        super(HttpStatus.BAD_REQUEST, "Second player cannot join singleplayer game!");
    }
}
