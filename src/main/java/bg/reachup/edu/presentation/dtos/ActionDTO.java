package bg.reachup.edu.presentation.dtos;

import bg.reachup.edu.data.entities.ActionType;
import bg.reachup.edu.data.entities.Direction;
import bg.reachup.edu.presentation.validation.EnumValue;
import bg.reachup.edu.presentation.validation.Username;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public record ActionDTO (
        @EnumValue(enumClass = ActionType.class, message = "Invalid action type!")
        @NotNull(message = "Action type must not be null!")
        String actionType,
        @EnumValue(enumClass = Direction.class, message = "Invalid direction!")
        @NotNull(message = "Direction must not be null!")
        String direction,
        @Valid
        @NotNull(message = "Piece position must not be null!")
        CoordinatesDTO piecePosition,
        @Username
        @NotNull(message = "Executor must not be null!")
        String executor
) {
        public ActionDTO {
                if (actionType != null) {
                        actionType = actionType.toUpperCase();
                }
                if (direction != null) {
                        direction = direction.toUpperCase().replace(' ', '_');
                }
        }
}
