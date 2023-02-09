package bg.reachup.edu.checkers.exceptions;

public class BoardFileAccessException extends RuntimeException{
    @Override
    public String getMessage() {
        return "Failed to access board file!";
    }
}
