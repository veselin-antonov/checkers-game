package bg.reachup.edu.buisness.checkers.state.actions;

import bg.reachup.edu.buisness.checkers.state.Coordinates;
import bg.reachup.edu.buisness.checkers.state.Piece;

public record Action(ActionType actionType, Direction direction, Piece piece, Coordinates originPosition) {
    @Override
    public String toString() {
        return  String.format(
                "%s %ss %s with %s%s",
                piece.getType().toString(),
                actionType.toString().toLowerCase(),
                direction.toString().toLowerCase(),
                piece,
                originPosition
            );
    }
}
