package bg.reachup.edu.buisness.exceptions;

public class CoordinatesOutOfBoundsException extends RuntimeException{
    @Override
    public String getMessage() {
        return "Coordinates were outside the bounds of the board";
    }
}
