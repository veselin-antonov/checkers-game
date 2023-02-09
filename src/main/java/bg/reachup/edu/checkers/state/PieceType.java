package bg.reachup.edu.checkers.state;

/**
 * Enumeration representing the types of pieces in the game of Checkers as well as their visual representation as characters
 */
public enum PieceType {
    /**
     * Represents the white game pieces
     */
    WHITE('\u25CB', '\u25B3'),
    /**
     * Represents the black game pieces
     */
    BLACK('\u2B24', '\u25B2');

    private char symbol;
    private char queenSymbol;

    PieceType(char symbol, char queenSymbol) {
        this.symbol = symbol;
        this.queenSymbol = queenSymbol;
    }

    /**
     * Returns the character representing the normal game piece of the corresponding color
     * @return the character representing the normal game piece of the corresponding color
     */
    public char getSymbol() {
        return symbol;
    }

    /**
     * Returns the character representing the normal game piece of the corresponding color
     * @return the character representing the normal game piece of the corresponding color
     */
    public char getQueenSymbol() {
        return queenSymbol;
    }
}
