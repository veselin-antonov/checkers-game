package bg.reachup.edu.buisness.exceptions.game;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class NoSuchGameIDException extends ResponseStatusException {
    public NoSuchGameIDException(Long id) {
        super(
                HttpStatus.NOT_FOUND,
                "Game with id: '%d' was not found".formatted(id)
        );
    }
}
