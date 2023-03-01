package bg.reachup.edu.presentation.dtos;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public record GameGetDTO(
        @NotNull
        @Pattern(regexp = "[a-zA-Z1-9_]{6,18}", message = "Invalid player username!")
        String player1Username,
        @Pattern(regexp = "[a-zA-Z1-9_]{6,18}", message = "Invalid player username!")
        String player2username,
        @Valid
        StateDTO state
) {}