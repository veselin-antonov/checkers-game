package bg.reachup.edu.data.entities;

import bg.reachup.edu.data.exceptions.CoordinatesOutOfBoundsException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.StringJoiner;

/**
 * Class representing a Checkers game board.
 */
public class Board {
    private final Piece[][] board;

    public Board(Piece[][] board) {
        this.board = board;
    }

    public Board(Board board) {
        this.board = deepClone(board.board);
    }

    /**
     * Fills the specified lists with references to all the
     * corresponding pieces on the board
     *
     * @param whitePieces list for the white pieces
     * @param blackPieces list for the black pieces
     */
    public void getPieces(List<Piece> whitePieces, List<Piece> blackPieces) {
        for (Piece[] pieces : board) {
            for (Piece currentPiece : pieces) {
                if (currentPiece == null) {
                    continue;
                }
                if (currentPiece.isWhite()) {
                    whitePieces.add(currentPiece);
                } else {
                    blackPieces.add(currentPiece);
                }
            }
        }
    }

    /**
     * Returns the Piece object at the specified coordinates
     *
     * @param coordinates the coordinates of the piece to be returned
     * @return The piece at the specified coordinates
     * @throws CoordinatesOutOfBoundsException if the coordinates are outside the bounds of the board
     */
    public Optional<Piece> getAt(Coordinates coordinates) {
        if (!isWithin(coordinates)) {
            throw new CoordinatesOutOfBoundsException();
        }
        Piece piece = board[coordinates.row()][coordinates.column()];
        return piece == null ? Optional.empty() : Optional.of(piece);
    }

    /**
     * Removes the Piece object at the specified coordinates and returns it
     *
     * @param coordinates the coordinates of the piece to be removed
     * @return The piece that was removed
     * @throws CoordinatesOutOfBoundsException if the coordinates are outside the bounds of the board
     */
    public Piece removeAt(Coordinates coordinates) {
        if (!isWithin(coordinates)) {
            throw new CoordinatesOutOfBoundsException();
        }
        Piece toRemove = board[coordinates.row()][coordinates.column()];
        board[coordinates.row()][coordinates.column()] = null;
        return toRemove;
    }

    /**
     * Moves the piece at the specified coordinates to the destination coordinates
     *
     * @param piecePosition the coordinates of the piece to be moved
     * @param destination   the destination of the piece
     * @return A deep copy of the board with the piece repositioned
     * @throws CoordinatesOutOfBoundsException if either the piece's or the destination's coordinates are
     *                                         outside the bounds of the board
     */
    public Board movePiece(Coordinates piecePosition, Coordinates destination) {
        if (!isWithin(destination) || !isWithin(piecePosition)) {
            throw new CoordinatesOutOfBoundsException();
        }
        int newRow = destination.row();
        int newColumn = destination.column();
        Board newBoard = new Board(deepClone(board));
        Piece toMove = newBoard.removeAt(piecePosition);
        newBoard.board[newRow][newColumn] = toMove;
        toMove.setCoordinates(new Coordinates(newRow, newColumn));
        if (toMove.isWhite() && toMove.getCoordinates().row() == 0) {
            toMove.promote();
        }
        if (toMove.isBlack() && toMove.getCoordinates().row() == board.length - 1) {
            toMove.promote();
        }
        return newBoard;
    }

    private Piece[][] deepClone(Piece[][] original) {
        Piece[][] clone = new Piece[original.length][original[0].length];
        for (int i = 0; i < original.length; i++) {
            for (int j = 0; j < original[i].length; j++) {
                Piece currentPiece = original[i][j];
                clone[i][j] = currentPiece == null ? null : new Piece(currentPiece);
            }
        }
        return clone;
    }

    /**
     * Returns true if the specified coordinates are within the bounds of the board
     *
     * @param coordinates the coordinates to be checked
     * @return true if the coordinates are within the board
     */
    public boolean isWithin(Coordinates coordinates) {
        return coordinates.row() >= 0
                && coordinates.row() < board.length
                && coordinates.column() >= 0
                && coordinates.column() < board[0].length;
    }

    /**
     * Returns true if there is no piece at the specified coordinates
     * @param coordinates coordinates to be checked
     * @return true if there is no piece at the specified coordinates
     */
    public boolean isFree(Coordinates coordinates) {
        if (!isWithin(coordinates)) {
            throw new CoordinatesOutOfBoundsException();
        }
        return getAt(coordinates).isEmpty();
    }

    /**
     * Returns a string representation of this object.
     * <p>The string representation consists of all the pieces, converted to {@link java.lang.String} as per the {@link Piece#toString} method and separated
     * by a column ",".<br>
     * The <i>null</i> objects are represented as an underscore symbol "_". Every row of the board ends with a newline character "\n".</p>
     * @return a string representation of this object
     */
    @Override
    public String toString() {
        StringJoiner rowJoiner = new StringJoiner("\n");
        for (Piece[] pieces : board) {
            StringJoiner pieceJoiner = new StringJoiner(",");
            for (Piece piece : pieces) {
                pieceJoiner.add(piece == null ? "_" : piece.toString());
            }
            rowJoiner.add(pieceJoiner.toString());
        }
        return rowJoiner.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Board board1 = (Board) o;
        return Arrays.deepEquals(board, board1.board);
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(board);
    }
}
