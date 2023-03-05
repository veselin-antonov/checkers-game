package bg.reachup.edu.buisness.exceptions.state;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class NoPieceAtCoordinatesException extends ResponseStatusException {
    public NoPieceAtCoordinatesException() {
        super(HttpStatus.BAD_REQUEST, "No piece at the given position");
    }
}
