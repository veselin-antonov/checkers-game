package bg.reachup.edu.checkers;

import bg.reachup.edu.checkers.state.Board;
import bg.reachup.edu.checkers.state.Coordinates;
import bg.reachup.edu.checkers.state.State;
import bg.reachup.edu.utils.Pair;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class GameTest {
    @Test
    @DisplayName("Prioritize score on even outcome evaluation")
    void whenManyCapturingOptionsPickBestScore() {
        Board board = Board.parseFromString(
                """
                        _,_,_,_,_,_,_
                        _,_,O,_,_,_,_
                        _,O,_,X,_,_,_
                        _,_,X,_,_,_,_
                        _,_,_,_,_,_,_
                        _,_,_,_,_,_,_
                        """
        );
        State state = new State(board, true);
        Game game = new Game(state);
        Pair<State, Integer> bestMove = game.findBestMove(state, 5);

        State bestMoveState = bestMove.value1();

        int expectedBestMoveScore = 2;
        int actualBestMoveScore = bestMove.value2();
        Assertions.assertEquals(expectedBestMoveScore, actualBestMoveScore);

        int expectedBestMoveStateEvaluation = 1;
        int actualBestMoveStateEvaluation = bestMoveState.evaluate();
        Assertions.assertEquals(expectedBestMoveStateEvaluation, actualBestMoveStateEvaluation);

        Coordinates expectedPiecePosition = new Coordinates(1, 0);
        Coordinates actualPiecePosition = bestMoveState.getOriginAction().piece().getCoordinates();
        Assertions.assertEquals(expectedPiecePosition, actualPiecePosition);
    }
}