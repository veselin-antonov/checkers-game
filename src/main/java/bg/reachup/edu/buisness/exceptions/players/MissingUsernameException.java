package bg.reachup.edu.buisness.exceptions.players;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Missing player username")
public class MissingUsernameException extends RuntimeException {}
