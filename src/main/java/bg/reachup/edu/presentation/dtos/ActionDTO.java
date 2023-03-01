package bg.reachup.edu.presentation.dtos;

import bg.reachup.edu.data.entities.ActionType;
import bg.reachup.edu.data.entities.Direction;
import bg.reachup.edu.presentation.validation.EnumValue;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public record ActionDTO (
        @NotNull(message = "Action type must not be null!")
        @EnumValue(enumClass = ActionType.class, message = "Invalid action type!")
        String actionType,
        @NotBlank(message = "Direction must not be null!")
        @EnumValue(enumClass = Direction.class, message = "Invalid direction!")
        String direction,
        @Valid
        @NotNull(message = "Piece position must not be null!")
        CoordinatesDTO piecePosition,
        @NotBlank(message = "Executor must not be null!")
        String executor
) {}
