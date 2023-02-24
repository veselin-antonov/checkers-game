package bg.reachup.edu.buisness.exceptions.game;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Game with specified id has already been completed")
public class GameAlreadyCompletedException extends RuntimeException {
}
