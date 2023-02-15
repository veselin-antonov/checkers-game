package bg.reachup.edu.data.converters;

import bg.reachup.edu.buisness.Board;
import bg.reachup.edu.buisness.Piece;
import bg.reachup.edu.buisness.exceptions.IllegalPiecePlacementException;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Arrays;
import java.util.List;

@Converter(autoApply = true)
public class BoardConverter implements AttributeConverter<Board, String> {
    @Override
    public String convertToDatabaseColumn(Board attribute) {
        return attribute.toString();
    }

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
