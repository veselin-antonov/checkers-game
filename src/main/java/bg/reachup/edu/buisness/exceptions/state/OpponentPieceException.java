package bg.reachup.edu.buisness.exceptions.state;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class OpponentPieceException extends ResponseStatusException {
    public OpponentPieceException() {
        super(HttpStatus.BAD_REQUEST, "You cannot move opponent pieces");
    }
}
