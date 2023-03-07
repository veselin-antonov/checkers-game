package bg.reachup.edu.presentation.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record GameGetDTO(
        String mode,
        String difficulty,
        String player1,
        String player2,
        StateDTO state
) {}