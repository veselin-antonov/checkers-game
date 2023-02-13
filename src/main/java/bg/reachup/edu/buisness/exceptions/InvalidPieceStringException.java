package bg.reachup.edu.buisness.exceptions;

public class InvalidPieceStringException extends RuntimeException {
    private String symbol;

    public InvalidPieceStringException(String symbol) {
        this.symbol = symbol;
    }

    @Override
    public String getMessage() {
        return String.format("Invalid string found when parsing pieces: %s", symbol);
    }
}
