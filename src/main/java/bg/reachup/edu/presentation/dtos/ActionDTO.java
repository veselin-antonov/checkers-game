package bg.reachup.edu.presentation.dtos;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public record ActionDTO (
        @NotBlank(message = "Action type must not be null!")
        String actionType,
        @NotBlank(message = "Action direction must not be null!")
        String direction,
        @Valid
        @NotNull(message = "Piece position must not be null!")
        CoordinatesDTO piecePosition,
        @NotBlank(message = "Action executor must not be null!")
        String executor
) {}
