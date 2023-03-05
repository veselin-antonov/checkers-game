package bg.reachup.edu.buisness.exceptions.game;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class DuplicateUnfinishedGameException extends ResponseStatusException {
    public DuplicateUnfinishedGameException() {
        super(HttpStatus.BAD_REQUEST, "Unfinished game between these two players already exists");
    }
}
