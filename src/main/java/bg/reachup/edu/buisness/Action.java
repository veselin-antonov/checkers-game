package bg.reachup.edu.buisness;

public record Action (ActionType actionType, Direction direction, Piece piece, Coordinates originPosition) {

}
