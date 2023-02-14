package bg.reachup.edu.buisness.exceptions.players;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "Database already contains some data")
public class PlayerTableNotEmptyException extends RuntimeException {}
