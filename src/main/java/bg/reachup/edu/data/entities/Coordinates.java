package bg.reachup.edu.data.entities;

/**
 * Two-dimensional coordinates for the {@link Board} class
 * @param row - the first value of the coordinates
 * @param column - the second value of the coordinates
 */
public record Coordinates(int row, int column) {

    /**
     * Returns a new {@link Coordinates} instance with copy of these coordinates' fields.
     * @return new {@link Coordinates} instance with copy of these coordinates as fields
     */
    public Coordinates copy() {
        return new Coordinates(row, column);
    }

    /**
     * Returns a new {@link Coordinates} instance with the specified values added to this instance's fields
     * @param v1 value to be added to the row field
     * @param v2 value to be added to the column field
     * @return new {@link Coordinates} instance with the specified values added to this instance's fields
     */
    public Coordinates modified(int v1, int v2) {
        return new Coordinates(row + v1, column + v2);
    }

    /**
     * Returns a string representation of the coordinates in the format: [row, column]
     * @return string representation of the coordinates
     */
    @Override
    public String toString() {
        return String.format("[%d, %d]", row, column);
    }
}