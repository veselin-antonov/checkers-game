package bg.reachup.edu.buisness.exceptions.game;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Unfinished game between these two players already exists")
public class DuplicateUnfinishedGameException extends RuntimeException {
}
