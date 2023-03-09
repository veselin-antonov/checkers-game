package bg.reachup.edu.presentation.dtos;

import bg.reachup.edu.presentation.validation.Username;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

public record PlayerDTO(
        @NotNull(message = "Missing player username!")
        @Username
        String username,
        @NotNull(message = "Missing player email!")
        @Email(regexp = "[\\w.+-]+@[a-z0-9.-]+\\.[a-z]{2,3}", message = "Invalid player email!")
        String email,
        @Null(message = "Cannot alter player game statistics!")
        Integer wins,
        @Null(message = "Cannot alter player game statistics!")
        Integer losses,
        @Null(message = "Cannot alter player game statistics!")
        Integer ties
) {
}
