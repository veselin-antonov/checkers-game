package bg.reachup.edu.presentation.dtos;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public record GamePostDTO(
        @NotNull(message = "Missing player username!")
        @Pattern(regexp = "[a-zA-Z1-9_]{6,18}", message = "Invalid player username!")
        String player1,
        @NotNull(message = "Missing player username!")
        @Pattern(regexp = "[a-zA-Z1-9_]{6,18}", message = "Invalid player username!")
        String player2
) {}
