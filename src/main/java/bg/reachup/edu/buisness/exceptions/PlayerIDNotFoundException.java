package bg.reachup.edu.buisness.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Player with such {id} was not found")
public class PlayerIDNotFoundException extends RuntimeException {
    private Long id;
    public PlayerIDNotFoundException(Long id) {

    }

    @Override
    public String getMessage() {
        return "Player with id {%d} was not found!".formatted(id);
    }
}
