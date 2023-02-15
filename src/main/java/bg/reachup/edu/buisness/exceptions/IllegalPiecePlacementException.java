package bg.reachup.edu.buisness.exceptions;

import bg.reachup.edu.buisness.Coordinates;

public class IllegalPiecePlacementException extends RuntimeException {
    private final Coordinates coordinates;

    public IllegalPiecePlacementException(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    @Override
    public String getMessage() {
        return String.format("Cannot place piece on square %s", coordinates.toString());
    }
}
