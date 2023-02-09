package bg.reachup.edu.utils.csv_reader.exceptions;

public class FileReadingException extends RuntimeException{
    @Override
    public String getMessage() {
        return "Failed to read from the provided file!";
    }
}
