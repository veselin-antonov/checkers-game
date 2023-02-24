package bg.reachup.edu.buisness.exceptions.state;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Illegal action")
public class IllegalActionException extends RuntimeException {}
