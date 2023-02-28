package bg.reachup.edu.buisness.exceptions.players;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code= HttpStatus.CONFLICT, reason = "Player with such username already exists")
public class PlayerUsernameAlreadyExistsException extends RuntimeException {}
