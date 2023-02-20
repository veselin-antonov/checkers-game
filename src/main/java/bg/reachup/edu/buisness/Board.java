package bg.reachup.edu.buisness;

import bg.reachup.edu.buisness.exceptions.BoardFileAccessException;
import bg.reachup.edu.buisness.exceptions.CoordinatesOutOfBoundsException;
import bg.reachup.edu.buisness.exceptions.IllegalPiecePlacementException;
import bg.reachup.edu.buisness.exceptions.InvalidPieceStringException;
import bg.reachup.edu.buisness.utils.csv_reader.CSVReader;
import bg.reachup.edu.data.converters.BoardConverter;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.jsonschema.JsonSerializableSchema;

import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.StringJoiner;

/**
 * Class representing a Checkers game board.
 */
public class Board {
    private final Piece[][] board;

    public Board(Piece[][] board) {
        this.board = board;
    }

    /**
     * Parses the file at the specified path
     * into a new Board instance based on its contents
     *
     * @param filePath location of the file to be parsed
     * @return Board instance based on the contents of the file at the specified path
     * @throws IllegalPiecePlacementException                                 if there is a piece at an illegal spot
     * @throws BoardFileAccessException                                       if the file at the specified path could not be accessed
     * @throws InvalidPieceStringException if an invalid symbol was found while parsing the map
     */
    public static Board parseFromFile(String filePath) {
        try (CSVReader csvReader = new CSVReader(filePath);) {
            List<List<String>> strings = new LinkedList<>();
            for (List<String> line : csvReader) {
                strings.add(line);
            }
            return parseMap(strings);
        } catch (FileNotFoundException e) {
            throw new BoardFileAccessException();
        }
    }

    /**
     * Parses the specified string representation of a board
     * into a new Board instance based on its contents
     *
     * @param board string representation of the board to be parsed
     * @return Board instance based on the specified string board representation
     * @throws IllegalPiecePlacementException                                 if there is a piece at an illegal spot
     * @throws InvalidPieceStringException if an invalid symbol was found while parsing the board
     */
    public static Board parseFromString(String board) {
        List<List<String>> strings = Arrays.stream(board.split("\n"))
                .map(line -> line.split(","))
                .map(Arrays::asList)
                .toList();
        return parseMap(strings);
    }

    private static Board parseMap(List<List<String>> strings) {
        Piece[][] newBoard = new Piece[strings.size()][strings.get(0).size()];
        int row = 0;
        int column = 0;
        for (List<String> list : strings) {
            for (String string : list) {
                Piece newPiece = Piece.getOfType(string, row, column);
                if (newPiece != null && (row + column) % 2 == 0) {
                    throw new IllegalPiecePlacementException(newPiece.getCoordinates());
                }
                newBoard[row][column] = newPiece;
                column++;
            }
            column = 0;
            row++;
        }
        return new Board(newBoard);
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
    public Piece getAt(Coordinates coordinates) {
        if (!isWithin(coordinates)) {
            throw new CoordinatesOutOfBoundsException();
        }
        return board[coordinates.row()][coordinates.column()];
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
        if (!isWithin(piecePosition) || !isWithin(destination)) {
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
        return getAt(coordinates) == null;
    }

    // TODO: 15.2.2023 JavaDoc
    /**
     *
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
}
