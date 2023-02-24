package bg.reachup.edu.data.entities;

import bg.reachup.edu.data.exceptions.InvalidPieceStringException;

/**
 * A game piece used to play the game Checkers.
 * <p>Each piece is represented by its actionType, coordinates and if it's either a normal or a queen piece.</p>
 */
public class Piece {
    /**
     * The integer cost of a normal piece used to determine its value
     */
    public static final int PIECE_VALUE = 1;
    /**
     * The integer cost of a queen piece used to determine its value
     */
    public static final int QUEEN_PIECE_VALUE = 3;
    private boolean isQueen;
    private final boolean isWhite;
    private Coordinates coordinates;

    /**
     * Creates an instance of the {@link Piece} class described by the specified arguments
     *
     * @param isWhite     the piece's actionType
     * @param isQueen     the value that sets if the piece is a queen
     * @param coordinates the piece's coordinates
     */
    public Piece(boolean isWhite, boolean isQueen, Coordinates coordinates) {
        this.isWhite = isWhite;
        this.isQueen = isQueen;
        this.coordinates = coordinates;
    }

    /**
     * Creates an instance of the {@link Piece} class with copies of the specified piece's fields
     *
     * @param otherPiece piece which fields will be copied
     */
    public Piece(Piece otherPiece) {
        this.isWhite = otherPiece.isWhite;
        this.isQueen = otherPiece.isQueen;
        this.coordinates = otherPiece.coordinates.copy();
    }

    /**
     * Returns an instance of this class, that is described by the input parameters.
     * <p>The {@link String} parameter determines the actionType and if the piece is a queen or not,
     * while the integers - the coordinates of the piece.\nIf no such actionType exists, the method
     * throws an exception.</p>
     *
     * @param type   the string representation of the piece actionType
     * @param row    the first value of the piece's coordinates
     * @param column the second value of the piece's coordinates
     * @return instance of this class, that is described by the input parameters
     * @throws InvalidPieceStringException if there is no such actionType as the given argument
     */
    public static Piece getOfType(String type, int row, int column) {
        return switch (type) {
            case "X" -> new Piece(true, false, new Coordinates(row, column));
            case "O" -> new Piece(false, false, new Coordinates(row, column));
            case "Xx" -> new Piece(true, true, new Coordinates(row, column));
            case "Oo" -> new Piece(false, true, new Coordinates(row, column));
            case "_" -> null;
            default -> throw new InvalidPieceStringException(type);
        };
    }

    /**
     * Returns the coordinates of this piece
     *
     * @return the coordinates of this piece
     */
    public Coordinates getCoordinates() {
        return coordinates;
    }

    /**
     * Sets the coordinates field to the specified argument
     *
     * @param coordinates coordinates to be set
     */
    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    /**
     * Returns true if the specified piece is of different color
     * <p>If the specified piece is null the method returns false</p>
     *
     * @param piece piece to be checked
     * @return true if the specified piece is of different color, false if the specified piece is null
     */
    public boolean isOpponent(Piece piece) {
        return piece != null && this.isWhite ^ piece.isWhite;
    }

    /**
     * Returns true if this piece is a queen
     *
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
     * The value is determined by the constants {@link Piece#PIECE_VALUE} and {@link Piece#QUEEN_PIECE_VALUE},
     * depending on the piece being e normal or queen piece respectively.
     *
     * @return the integer value of this piece
     */
    public int getValue() {
        return isQueen ? QUEEN_PIECE_VALUE : PIECE_VALUE;
    }

    /**
     * Returns true if the piece is white
     *
     * @return true if the piece is white
     */
    public boolean isWhite() {
        return isWhite;
    }

    /**
     * Returns true if the piece is black
     *
     * @return true if the piece is black
     */
    public boolean isBlack() {
        return !isWhite;
    }

    /**
     *  Returns a {@link String} representation of this piece. The pieces are represented as follows:<br>
     *  <ul>
     *      <li>White piece -> X</li>
     *      <li>White queen piece -> Xx</li>
     *      <li>Black piece -> O</li>
     *      <li>Black queen piece -> Oo</li>
     *  </ul>
     */
    @Override
    public String toString() {
        if (isWhite) {
            return isQueen ? "Xx" : "X";
        } else {
            return isQueen ? "Oo" : "O";
        }
    }
}
