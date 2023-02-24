package bg.reachup.edu.data.exceptions;

public class InvalidPieceStringException extends RuntimeException {
    private final String symbol;

    public InvalidPieceStringException(String symbol) {
        this.symbol = symbol;
    }

    @Override
    public String getMessage() {
        return String.format("Invalid string found when parsing pieces: %s", symbol);
    }
}
