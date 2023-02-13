package bg.reachup.edu.buisness.exceptions;

public class BoardFileAccessException extends RuntimeException{
    @Override
    public String getMessage() {
        return "Failed to access board file!";
    }
}
