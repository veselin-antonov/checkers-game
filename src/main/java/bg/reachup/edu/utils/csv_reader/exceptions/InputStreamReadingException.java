package bg.reachup.edu.utils.csv_reader.exceptions;

public class InputStreamReadingException extends RuntimeException{
    @Override
    public String getMessage() {
        return "Failed to read from provided InputStream!";
    }
}
