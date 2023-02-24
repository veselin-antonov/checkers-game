package bg.reachup.edu.buisness.exceptions.state;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "No piece at the given position")
public class NoPieceAtCoordinatesException extends RuntimeException {
}
