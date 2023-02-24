package bg.reachup.edu.data.exceptions;

public class CoordinatesOutOfBoundsException extends RuntimeException{
    @Override
    public String getMessage() {
        return "Coordinates were outside the bounds of the board";
    }
}
