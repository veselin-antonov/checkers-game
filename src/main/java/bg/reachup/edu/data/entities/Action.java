package bg.reachup.edu.data.entities;

/**
 * Record describing an action that can be performed during a game of checkers.
 * @param actionType the type of the action
 * @param direction the direction in which the action is executed
 * @param piecePosition the position of the piece that is to be moved
 * @param executor the username of the player executing the action
 */
public record Action(ActionType actionType, Direction direction, Coordinates piecePosition, String executor) {
    @Override
    public String toString() {
        return "%s %sd %s with %s".formatted(
                executor,
                actionType.toString().toLowerCase(),
                direction.toString().replace("_", " ").toLowerCase(),
                piecePosition
        );
    }
}
