package bg.reachup.edu.buisness.exceptions.game;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class NoSuchGameIDException extends ResponseStatusException {
    public NoSuchGameIDException() {
        super(HttpStatus.UNAUTHORIZED, "Cannot play when it's the other player's turn");
    }
}
