package bg.reachup.edu.buisness.exceptions.players;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class NoSuchPlayerIDException extends ResponseStatusException {
    public NoSuchPlayerIDException() {
        super(HttpStatus.NOT_FOUND, "Player with such {id} was not found");
    }
}
