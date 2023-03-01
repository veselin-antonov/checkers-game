package bg.reachup.edu.presentation.dtos;

import javax.validation.constraints.*;

public record PlayerDTO(
        @NotNull(message = "Missing player username!")
        @Pattern(regexp = "[a-zA-Z1-9_]{6,18}", message = "Invalid player username!")
        String username,
        @NotNull(message = "Missing player email!")
        @Email(message = "Invalid player email!")
        String email,
        @Null(message = "Cannot alter player game count!")
        Integer gamesPlayed
) {
}
