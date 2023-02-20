package bg.reachup.edu.data.converters;

import bg.reachup.edu.buisness.Board;
import bg.reachup.edu.buisness.Piece;
import bg.reachup.edu.buisness.exceptions.IllegalPiecePlacementException;
import bg.reachup.edu.buisness.exceptions.InvalidPieceStringException;
import org.springframework.stereotype.Component;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Arrays;
import java.util.List;

@Component
@Converter(autoApply = true)
public class BoardConverter implements AttributeConverter<Board, String> {
    @Override
    public String convertToDatabaseColumn(Board attribute) {
        return attribute.toString();
    }


    /**
     * Parses the specified string representation of a board
     * into a new Board instance based on its contents
     *
     * @return Board instance based on the specified string board representation
     * @throws IllegalPiecePlacementException                                 if there is a piece at an illegal spot
     * @throws InvalidPieceStringException if an invalid symbol was found while parsing the board
     */
    @Override
    public Board convertToEntityAttribute(String dbData) {
        List<List<String>> strings = Arrays.stream(dbData.split("\n"))
                .map(line -> line.split(","))
                .map(Arrays::asList)
                .toList();
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
}
