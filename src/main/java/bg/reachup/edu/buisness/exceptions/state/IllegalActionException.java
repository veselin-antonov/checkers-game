package bg.reachup.edu.buisness.exceptions.state;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class IllegalActionException extends ResponseStatusException {
    public IllegalActionException() {
        super(HttpStatus.BAD_REQUEST, "Illegal action");
    }
}
