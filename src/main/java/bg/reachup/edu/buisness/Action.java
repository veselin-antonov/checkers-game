package bg.reachup.edu.buisness;

public record Action(ActionType actionType, Direction direction, Coordinates piecePosition, String executor) {
    @Override
    public String toString() {
        return "%s %sed %s with %s".formatted(
                executor,
                actionType.toString().toLowerCase(),
                direction.toString().replace("_", " ").toLowerCase(),
                piecePosition
        );
    }
}
