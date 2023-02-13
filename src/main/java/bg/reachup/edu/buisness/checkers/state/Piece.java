package bg.reachup.edu.buisness.checkers.state;

import bg.reachup.edu.buisness.exceptions.InvalidPieceStringException;

/**
 * A game piece used to play the game Checkers.
 * <p>Each piece is represented by its actionType, coordinates and if it's either a normal or a queen piece.</p>
 *
 */
public class Piece {
    private final PieceType type;

    public static final int PIECE_COST = 1;
    public static final int QUEEN_PIECE_COST = 3;
    private boolean isQueen;
    private Coordinates coordinates;

    /**
     * Creates an instance of the {@link Piece} class described by the specified arguments
     * @param type the piece's actionType
     * @param isQueen the value that sets if the piece is a queen
     * @param coordinates the piece's coordinates
     */
    public Piece(PieceType type, boolean isQueen, Coordinates coordinates) {
        this.type = type;
        this.isQueen = isQueen;
        this.coordinates = coordinates;
    }

    /**
     * Creates an instance of the {@link Piece} class with copies of the specified piece's fields
     * @param otherPiece piece which fields will be copied
     */
    public Piece(Piece otherPiece) {
        this.type = otherPiece.type;
        this.isQueen = otherPiece.isQueen;
        this.coordinates = otherPiece.coordinates.copy();
    }

    /**
     * Returns an instance of this class, that is described by the input parameters.
     *<p>The {@link String} parameter determines the actionType and if the piece is a queen or not,
     *  while the integers - the coordinates of the piece.\nIf no such actionType exists, the method
     *  throws an exception.</p>
     *
     * @param type the string representation of the piece actionType
     * @param row the first value of the piece's coordinates
     * @param column the second value of the piece's coordinates
     * @return instance of this class, that is described by the input parameters
     * @throws InvalidPieceStringException if there is no such actionType as the given argument
     */
    public static Piece getOfType(String type, int row, int column) {
        return switch (type) {
            case "X" -> new Piece(PieceType.WHITE, false, new Coordinates(row, column));
            case "O" -> new Piece(PieceType.BLACK, false, new Coordinates(row, column));
            case "Xx" -> new Piece(PieceType.WHITE, true, new Coordinates(row, column));
            case "Oo" -> new Piece(PieceType.BLACK, true, new Coordinates(row, column));
            case "_" -> null;
            default -> throw new InvalidPieceStringException(type);
        };
    }

    /**
     * Returns the type of this piece
     * @return the type of this piece
     */
    public PieceType getType() {
        return type;
    }

    /**
     * Returns the coordinates of this piece
     * @return the coordinates of this piece
     */
    public Coordinates getCoordinates() {
        return coordinates;
    }

    /**
     * Sets the coordinates field to the specified argument
     * @param coordinates coordinates to be set
     */
    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    /**
     * Returns true if the specified piece has a different {@link PieceType}
     * <p>If the specified piece is null the method returns false</p>
     * @param piece piece to be checked
     * @return true if the specified piece has a different {@link PieceType}, false if the specified piece is null
     */
    public boolean isOpponent(Piece piece) {
        return piece != null && this.type != piece.type;
    }

    /** Returns true if this piece is a queen
     * @return true if this piece is a queen
     */
    public boolean isQueen() {
        return isQueen;
    }

    /**
     * Promotes this piece to a queen
     */
    public void promote() {
        isQueen = true;
    }

    /**
     * Returns the integer value of this piece.
     * The value is determined by the constants {@link Piece#PIECE_COST} and {@link Piece#QUEEN_PIECE_COST},
     * depending on the piece being e normal or queen piece respectively.
     * @return the integer value of this piece
     */
    public int getValue() {
        return isQueen ? QUEEN_PIECE_COST : PIECE_COST;
    }

    /**
     * Returns true if the piece is of actionType {@link PieceType#WHITE}
     * @return true if the piece is of actionType {@link PieceType#WHITE}
     */
    public boolean isWhite() {
        return type == PieceType.WHITE;
    }

    /**
     * Returns true if the piece is of actionType {@link PieceType#BLACK}
     * @return true if the piece is of actionType {@link PieceType#BLACK}
     */
    public boolean isBlack() {
        return type == PieceType.BLACK;
    }

    /**
     * Returns a string representation of this piece. The string representation consists of the string returned by
     * the method {@link PieceType#getSymbol()} if the piece is normal and {@link PieceType#getQueenSymbol()} if it's a queen.
     * @return string representation of this piece
     */
    @Override
    public String toString() {
        return String.valueOf(isQueen ? type.getQueenSymbol() : type.getSymbol());
    }
}
