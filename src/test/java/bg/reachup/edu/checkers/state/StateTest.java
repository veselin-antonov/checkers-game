package bg.reachup.edu.checkers.state;

import bg.reachup.edu.buisness.checkers.state.Board;
import bg.reachup.edu.buisness.checkers.state.Coordinates;
import bg.reachup.edu.buisness.checkers.state.Piece;
import bg.reachup.edu.buisness.checkers.state.State;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

class StateTest {
    @Test
    @DisplayName("Moving when capturing is available")
    void whenCaptureIsPossibleMoveIsNotAttempted() {
        Board board = Board.parseFromString(
                """
                        _,_,_,_,_,_,_
                        _,_,_,_,_,_,_
                        _,_,_,_,_,_,_
                        _,_,_,_,_,_,_
                        _,O,_,_,_,_,_
                        _,_,X,_,_,_,_
                        """
        );
        State state = new State(board, true);
        List<State> children = state.getChildren();

        int expectedSize = 1;
        int actualSize = children.size();
        Assertions.assertEquals(expectedSize, actualSize);

        State childState = children.get(0);

        int expectedChildStateScore = 1;
        int actualChildStateScore = childState.evaluate();
        Assertions.assertEquals(expectedChildStateScore, actualChildStateScore);

        Piece expectedAttackPiece = childState.getBoard().getAt(new Coordinates(3, 0));
        Piece actualAttackPiece = childState.getAttacker();
        int expectedAttackStreak = 1;
        int actualAttackStreak = childState.getCaptureStreak();

        Assertions.assertNotNull(expectedAttackPiece);
        Assertions.assertNotNull(actualAttackPiece);
        Assertions.assertSame(expectedAttackPiece, actualAttackPiece);
        Assertions.assertEquals(expectedAttackStreak, actualAttackStreak);

        List<Piece> childStateWhitePieces = new LinkedList<>();
        List<Piece> childStateBlackPieces = new LinkedList<>();
        childState.getBoard().getPieces(childStateWhitePieces, childStateBlackPieces);

        int expectedChildState1BlackPieceCount = 0;
        int actualChildState1BlackPieceCount = childStateBlackPieces.size();
        Assertions.assertEquals(expectedChildState1BlackPieceCount, actualChildState1BlackPieceCount);
    }

    @Test
    @DisplayName("Capture prioritization 1")
    void whenManyBestTakingOptionsReturnAll() {
        Board board = Board.parseFromString(
                """
                        _,_,_,_,_,_,_,_,_,_
                        _,_,_,_,_,_,_,_,_,_
                        _,_,_,_,_,_,_,O,_,_
                        _,_,_,_,_,_,_,_,_,_
                        _,_,_,O,_,O,_,O,_,_
                        _,_,_,_,_,_,_,_,_,_
                        _,O,_,O,_,O,_,_,_,_
                        _,_,_,_,_,_,_,_,_,_
                        _,_,_,O,_,_,_,_,_,_
                        _,_,_,_,Xx,_,_,_,_,_
                        """
        );
        State state = new State(board, true);
        List<State> children = state.getChildren();

        int expectedSize = 2;
        int actualSize = children.size();
        Assertions.assertEquals(expectedSize, actualSize);

        for (State childState : children) {
            int expectedChildStateScore = -1;
            int actualChildStateScore = childState.evaluate();
            Assertions.assertEquals(expectedChildStateScore, actualChildStateScore);
        }

        State childState1 = children.get(0);
        State childState2 = children.get(1);

        Piece actualAttackPiece1 = childState1.getBoard().getAt(new Coordinates(5, 8));
        Piece actualAttackPiece2 = childState2.getBoard().getAt(new Coordinates(1, 8));

        Assertions.assertNotNull(actualAttackPiece1);
        Assertions.assertNotNull(actualAttackPiece2);
        Assertions.assertTrue(actualAttackPiece1.isWhite());
        Assertions.assertTrue(actualAttackPiece1.isWhite());

        List<Piece> childState1WhitePieces = new LinkedList<>();
        List<Piece> childState1BlackPieces = new LinkedList<>();
        childState1.getBoard().getPieces(childState1WhitePieces, childState1BlackPieces);

        int expectedChildState1BlackPieceCount = 4;
        int actualChildState1BlackPieceCount = childState1BlackPieces.size();
        Assertions.assertEquals(expectedChildState1BlackPieceCount, actualChildState1BlackPieceCount);
    }

    @Test
    @DisplayName("Capture prioritization 2")
    void whenManyTakingOptionsThenTakeOnlyBestOne() {
        Board board = Board.parseFromString(
                """
                        _,_,_,_,_,_,_,_,_,_
                        _,_,_,_,_,_,_,_,_,_
                        _,_,_,_,_,O,_,_,_,_
                        _,_,_,_,_,_,_,_,_,_
                        _,_,_,_,_,_,_,O,_,_
                        _,_,_,_,_,_,_,_,_,_
                        _,O,_,_,_,_,_,O,_,_
                        _,_,_,_,_,_,_,_,_,_
                        _,_,_,O,_,O,_,_,_,_
                        _,_,_,_,Xx,_,_,_,_,_
                        """
        );
        State state = new State(board, true);
        List<State> children = state.getChildren();

        int expectedSize = 1;
        int actualSize = children.size();
        Assertions.assertEquals(expectedSize, actualSize);

        State childState = children.get(0);

        int expectedChildStateScore = 1;
        int actualChildStateScore = childState.evaluate();
        Assertions.assertEquals(expectedChildStateScore, actualChildStateScore);

        Piece expectedAttackPiece = childState.getBoard().getAt(new Coordinates(1, 4));
        Piece actualAttackPiece = childState.getAttacker();
        int expectedAttackStreak = 4;
        int actualAttackStreak = childState.getCaptureStreak();

        Assertions.assertNotNull(expectedAttackPiece);
        Assertions.assertNotNull(actualAttackPiece);
        Assertions.assertSame(expectedAttackPiece, actualAttackPiece);
        Assertions.assertEquals(expectedAttackStreak, actualAttackStreak);

        List<Piece> childStateWhitePieces = new LinkedList<>();
        List<Piece> childStateBlackPieces = new LinkedList<>();
        childState.getBoard().getPieces(childStateWhitePieces, childStateBlackPieces);

        int expectedChildState1BlackPieceCount = 2;
        int actualChildState1BlackPieceCount = childStateBlackPieces.size();
        Assertions.assertEquals(expectedChildState1BlackPieceCount, actualChildState1BlackPieceCount);
    }
}