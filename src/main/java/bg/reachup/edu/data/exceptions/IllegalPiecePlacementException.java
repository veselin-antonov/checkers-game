package bg.reachup.edu.data.exceptions;

import bg.reachup.edu.data.entities.Coordinates;

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
