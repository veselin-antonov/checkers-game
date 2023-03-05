package bg.reachup.edu.buisness.exceptions.players;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class NoSuchUsernameFoundException extends ResponseStatusException {
    public NoSuchUsernameFoundException() {
        super(HttpStatus.NOT_FOUND, "Player with such username was not found");
    }
}
