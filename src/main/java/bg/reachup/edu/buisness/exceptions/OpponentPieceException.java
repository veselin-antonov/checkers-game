package bg.reachup.edu.buisness.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "You cannot move opponent pieces")
public class OpponentPieceException extends RuntimeException {
}
