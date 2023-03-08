package bg.reachup.edu.buisness.services;

import bg.reachup.edu.buisness.exceptions.state.IllegalActionException;
import bg.reachup.edu.data.converters.BoardConverter;
import bg.reachup.edu.data.entities.*;
import bg.reachup.edu.data.repositories.MockStateRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class StateServiceTest {

    private final StateService stateService = new StateService(new MockStateRepository());
    private final BoardConverter boardConverter = new BoardConverter();

    @Test
    @DisplayName("Normal piece movement")
    void test1() {
        Board board = boardConverter.convertToEntityAttribute(
                """
                        _,_,_,_
                        _,_,_,_
                        _,X,_,_
                        _,_,_,_
                        """
        );
        State state = new State(
                board,
                true
        );

        Action[] actions = new Action[]{
                new Action(ActionType.MOVE, Direction.UP_LEFT, new Coordinates(2, 1), "TestUser"),
                new Action(ActionType.MOVE, Direction.UP_RIGHT, new Coordinates(2, 1), "TestUser"),
                new Action(ActionType.MOVE, Direction.DOWN_LEFT, new Coordinates(2, 1), "TestUser"),
                new Action(ActionType.MOVE, Direction.DOWN_RIGHT, new Coordinates(2, 1), "TestUser"),
                new Action(ActionType.CAPTURE, Direction.UP_LEFT, new Coordinates(2, 1), "TestUser"),
                new Action(ActionType.CAPTURE, Direction.UP_RIGHT, new Coordinates(2, 1), "TestUser"),
                new Action(ActionType.CAPTURE, Direction.DOWN_LEFT, new Coordinates(2, 1), "TestUser"),
                new Action(ActionType.CAPTURE, Direction.DOWN_RIGHT, new Coordinates(2, 1), "TestUser")
        };


        State[] expectedStates = new State[]{
                new State(
                        boardConverter.convertToEntityAttribute(
                                """
                                        _,_,_,_
                                        X,_,_,_
                                        _,_,_,_
                                        _,_,_,_
                                        """
                        ),
                        false,
                        actions[0]
                ),
                new State(
                        boardConverter.convertToEntityAttribute(
                                """
                                        _,_,_,_
                                        _,_,X,_
                                        _,_,_,_
                                        _,_,_,_
                                        """
                        ),
                        false,
                        actions[1]
                )
        };

        State[] actualStates = new State[]{
                stateService.executeAction(actions[0], state),
                stateService.executeAction(actions[1], state)
        };

        Assertions.assertEquals(expectedStates[0], actualStates[0]);
        Assertions.assertEquals(expectedStates[1], actualStates[1]);

        for (int i = 2; i < actions.length; i++) {
            Action currentAction = actions[i];
            Assertions.assertThrows(IllegalActionException.class, () -> stateService.executeAction(currentAction, state));
        }
    }

    @Test
    @DisplayName("Queen piece movement")
    void test2() {
        Board board = boardConverter.convertToEntityAttribute(
                """
                        _,_,_,_
                        _,_,_,_
                        _,Xx,_,_
                        _,_,_,_
                        """
        );
        State state = new State(
                board,
                true
        );

        Action[] actions = new Action[]{
                new Action(ActionType.MOVE, Direction.UP_LEFT, new Coordinates(2, 1), "TestUser"),
                new Action(ActionType.MOVE, Direction.UP_RIGHT, new Coordinates(2, 1), "TestUser"),
                new Action(ActionType.MOVE, Direction.DOWN_LEFT, new Coordinates(2, 1), "TestUser"),
                new Action(ActionType.MOVE, Direction.DOWN_RIGHT, new Coordinates(2, 1), "TestUser"),
                new Action(ActionType.CAPTURE, Direction.UP_LEFT, new Coordinates(2, 1), "TestUser"),
                new Action(ActionType.CAPTURE, Direction.UP_RIGHT, new Coordinates(2, 1), "TestUser"),
                new Action(ActionType.CAPTURE, Direction.DOWN_LEFT, new Coordinates(2, 1), "TestUser"),
                new Action(ActionType.CAPTURE, Direction.DOWN_RIGHT, new Coordinates(2, 1), "TestUser")
        };


        State[] expectedStates = new State[]{
                new State(
                        boardConverter.convertToEntityAttribute(
                                """
                                        _,_,_,_
                                        Xx,_,_,_
                                        _,_,_,_
                                        _,_,_,_
                                        """
                        ),
                        false,
                        actions[0]
                ),
                new State(
                        boardConverter.convertToEntityAttribute(
                                """
                                        _,_,_,_
                                        _,_,Xx,_
                                        _,_,_,_
                                        _,_,_,_
                                        """
                        ),
                        false,
                        actions[1]
                ),
                new State(
                        boardConverter.convertToEntityAttribute(
                                """
                                        _,_,_,_
                                        _,_,_,_
                                        _,_,_,_
                                        Xx,_,_,_
                                        """
                        ),
                        false,
                        actions[2]
                ),
                new State(
                        boardConverter.convertToEntityAttribute(
                                """
                                        _,_,_,_
                                        _,_,_,_
                                        _,_,_,_
                                        _,_,Xx,_
                                        """
                        ),
                        false,
                        actions[3]
                )
        };

        State[] actualStates = new State[]{
                stateService.executeAction(actions[0], state),
                stateService.executeAction(actions[1], state),
                stateService.executeAction(actions[2], state),
                stateService.executeAction(actions[3], state)
        };

        for (int i = 0; i < 3; i++) {
            Assertions.assertEquals(expectedStates[i], actualStates[i]);
        }

        for (int i = 4; i < actions.length; i++) {
            Action currentAction = actions[i];
            Assertions.assertThrows(IllegalActionException.class, () -> stateService.executeAction(currentAction, state));
        }
    }

    @Test
    @DisplayName("Normal piece capture priority")
    void test3() {
        Board board = boardConverter.convertToEntityAttribute(
                """
                        _,_,_,_,_,_
                        _,_,_,_,_,_
                        _,O,_,_,_,_
                        _,_,X,_,_,_
                        _,_,_,_,_,_
                        _,_,_,_,_,_
                        """
        );
        State state = new State(
                board,
                true
        );

        Action[] actions = new Action[]{
                new Action(ActionType.CAPTURE, Direction.UP_LEFT, new Coordinates(3, 2), "TestUser"),
                new Action(ActionType.MOVE, Direction.UP_LEFT, new Coordinates(3, 2), "TestUser"),
                new Action(ActionType.MOVE, Direction.UP_RIGHT, new Coordinates(3, 2), "TestUser"),
                new Action(ActionType.MOVE, Direction.DOWN_LEFT, new Coordinates(3, 2), "TestUser"),
                new Action(ActionType.MOVE, Direction.DOWN_RIGHT, new Coordinates(3, 2), "TestUser"),
                new Action(ActionType.CAPTURE, Direction.UP_RIGHT, new Coordinates(3, 2), "TestUser"),
                new Action(ActionType.CAPTURE, Direction.DOWN_LEFT, new Coordinates(3, 2), "TestUser"),
                new Action(ActionType.CAPTURE, Direction.DOWN_RIGHT, new Coordinates(3, 2), "TestUser")
        };


        State expectedState = new State(
                boardConverter.convertToEntityAttribute(
                        """
                                _,_,_,_,_,_
                                X,_,_,_,_,_
                                _,_,_,_,_,_
                                _,_,_,_,_,_
                                _,_,_,_,_,_
                                _,_,_,_,_,_
                                """
                ),
                false,
                actions[0]
        );

        State actualState = stateService.executeAction(actions[0], state);

        Assertions.assertEquals(expectedState, actualState);


        for (int i = 1; i < actions.length; i++) {
            Action currentAction = actions[i];
            Assertions.assertThrows(IllegalActionException.class, () -> stateService.executeAction(currentAction, state));
        }
    }

    @Test
    @DisplayName("Normal piece capture priority 2")
    void test4() {
        Board board = boardConverter.convertToEntityAttribute(
                """
                        _,_,_,_,_,_,_,_
                        _,_,_,_,_,_,_,_
                        _,_,_,_,_,O,_,_
                        _,_,_,_,_,_,_,_
                        _,O,_,O,_,_,_,_
                        _,_,X,_,_,_,_,_
                        _,_,_,O,_,_,_,_
                        _,_,_,_,_,_,_,_
                        """
        );
        State state = new State(
                board,
                true
        );

        Coordinates piecePosition = new Coordinates(5, 2);
        Action[] actions = {
                new Action(ActionType.CAPTURE, Direction.UP_RIGHT, piecePosition, "TestUser"),
                new Action(ActionType.MOVE, Direction.UP_LEFT, piecePosition, "TestUser"),
                new Action(ActionType.MOVE, Direction.UP_RIGHT, piecePosition, "TestUser"),
                new Action(ActionType.MOVE, Direction.DOWN_LEFT, piecePosition, "TestUser"),
                new Action(ActionType.MOVE, Direction.DOWN_RIGHT, piecePosition, "TestUser"),
                new Action(ActionType.CAPTURE, Direction.UP_LEFT, piecePosition, "TestUser"),
                new Action(ActionType.CAPTURE, Direction.DOWN_LEFT, piecePosition, "TestUser"),
                new Action(ActionType.CAPTURE, Direction.DOWN_RIGHT, piecePosition, "TestUser")
        };


        State expectedState = new State(
                boardConverter.convertToEntityAttribute(
                        """
                                _,_,_,_,_,_,_,_
                                _,_,_,_,_,_,X,_
                                _,_,_,_,_,_,_,_
                                _,_,_,_,_,_,_,_
                                _,O,_,_,_,_,_,_
                                _,_,_,_,_,_,_,_
                                _,_,_,O,_,_,_,_
                                _,_,_,_,_,_,_,_
                                """
                ),
                false,
                actions[0]
        );

        State actualState = stateService.executeAction(actions[0], state);

        Assertions.assertEquals(expectedState, actualState);

        for (int i = 2; i < actions.length; i++) {
            Action currentAction = actions[i];
            Assertions.assertThrows(IllegalActionException.class, () -> stateService.executeAction(currentAction, state));
        }
    }

    @Test
    @DisplayName("Queen piece multiple captures")
    void test5() {
        Board board = boardConverter.convertToEntityAttribute(
                """
                        _,_,_,_,_,_
                        _,_,_,_,_,_
                        _,Oo,_,_,_,_
                        _,_,Xx,_,_,_
                        _,_,_,O,_,_
                        _,_,_,_,_,_
                        """
        );
        State state = new State(
                board,
                true
        );

        Coordinates piecePosition = new Coordinates(3, 2);
        Action[] actions = {
                new Action(ActionType.CAPTURE, Direction.UP_LEFT, piecePosition, "TestUser"),
                new Action(ActionType.CAPTURE, Direction.DOWN_RIGHT, piecePosition, "TestUser"),
                new Action(ActionType.MOVE, Direction.UP_LEFT, piecePosition, "TestUser"),
                new Action(ActionType.MOVE, Direction.UP_RIGHT, piecePosition, "TestUser"),
                new Action(ActionType.MOVE, Direction.DOWN_LEFT, piecePosition, "TestUser"),
                new Action(ActionType.MOVE, Direction.DOWN_RIGHT, piecePosition, "TestUser"),
                new Action(ActionType.CAPTURE, Direction.UP_RIGHT, piecePosition, "TestUser"),
                new Action(ActionType.CAPTURE, Direction.DOWN_LEFT, piecePosition, "TestUser")
        };


        State[] expectedStates = {
                new State(
                        boardConverter.convertToEntityAttribute(
                                """
                                        _,_,_,_,_,_
                                        Xx,_,_,_,_,_
                                        _,_,_,_,_,_
                                        _,_,_,_,_,_
                                        _,_,_,O,_,_
                                        _,_,_,_,_,_
                                        """
                        ),
                        false,
                        actions[0]
                ),
                new State(
                        boardConverter.convertToEntityAttribute(
                                """
                                        _,_,_,_,_,_
                                        _,_,_,_,_,_
                                        _,Oo,_,_,_,_
                                        _,_,_,_,_,_
                                        _,_,_,_,_,_
                                        _,_,_,_,Xx,_
                                        """
                        ),
                        false,
                        actions[1]
                )
        };

        State[] actualStates = {
                stateService.executeAction(actions[0], state),
                stateService.executeAction(actions[1], state),
        };

        Assertions.assertEquals(expectedStates[0], actualStates[0]);
        Assertions.assertEquals(expectedStates[1], actualStates[1]);

        for (int i = 2; i < actions.length; i++) {
            Action currentAction = actions[i];
            Assertions.assertThrows(IllegalActionException.class, () -> stateService.executeAction(currentAction, state));
        }
    }

    @Test
    @DisplayName("Normal piece multiple best captures")
    void test6() {
        Board board = boardConverter.convertToEntityAttribute(
                """
                        _,_,_,_,_,_,_,_,_,_
                        _,_,_,_,_,_,_,_,_,_
                        _,_,_,_,_,_,_,_,_,_
                        _,_,_,_,_,_,_,_,_,_
                        _,O,_,_,_,_,_,O,_,_
                        _,_,_,_,_,_,_,_,_,_
                        _,_,_,O,_,O,_,_,_,_
                        _,_,_,_,X,_,_,_,_,_
                        _,_,_,_,_,O,_,_,_,_
                        _,_,_,_,_,_,_,_,_,_
                        """
        );
        State state = new State(
                board,
                true
        );

        Coordinates piecePosition = new Coordinates(7, 4);
        Action[] actions = {
                new Action(ActionType.CAPTURE, Direction.UP_LEFT, piecePosition, "TestUser"),
                new Action(ActionType.CAPTURE, Direction.UP_RIGHT, piecePosition, "TestUser"),
                new Action(ActionType.MOVE, Direction.UP_LEFT, piecePosition, "TestUser"),
                new Action(ActionType.MOVE, Direction.UP_RIGHT, piecePosition, "TestUser"),
                new Action(ActionType.MOVE, Direction.DOWN_LEFT, piecePosition, "TestUser"),
                new Action(ActionType.MOVE, Direction.DOWN_RIGHT, piecePosition, "TestUser"),
                new Action(ActionType.CAPTURE, Direction.DOWN_LEFT, piecePosition, "TestUser"),
                new Action(ActionType.CAPTURE, Direction.DOWN_RIGHT, piecePosition, "TestUser")
        };


        State[] expectedStates = {
                new State(
                        boardConverter.convertToEntityAttribute(
                                """
                                        _,_,_,_,_,_,_,_,_,_
                                        _,_,_,_,_,_,_,_,_,_
                                        _,_,_,_,_,_,_,_,_,_
                                        X,_,_,_,_,_,_,_,_,_
                                        _,_,_,_,_,_,_,O,_,_
                                        _,_,_,_,_,_,_,_,_,_
                                        _,_,_,_,_,O,_,_,_,_
                                        _,_,_,_,_,_,_,_,_,_
                                        _,_,_,_,_,O,_,_,_,_
                                        _,_,_,_,_,_,_,_,_,_
                                        """
                        ),
                        false,
                        actions[0]
                ),
                new State(
                        boardConverter.convertToEntityAttribute(
                                """
                                        _,_,_,_,_,_,_,_,_,_
                                        _,_,_,_,_,_,_,_,_,_
                                        _,_,_,_,_,_,_,_,_,_
                                        _,_,_,_,_,_,_,_,X,_
                                        _,O,_,_,_,_,_,_,_,_
                                        _,_,_,_,_,_,_,_,_,_
                                        _,_,_,O,_,_,_,_,_,_
                                        _,_,_,_,_,_,_,_,_,_
                                        _,_,_,_,_,O,_,_,_,_
                                        _,_,_,_,_,_,_,_,_,_
                                        """
                        ),
                        false,
                        actions[1]
                )
        };

        State[] actualStates = {
                stateService.executeAction(actions[0], state),
                stateService.executeAction(actions[1], state),
        };

        Assertions.assertEquals(expectedStates[0], actualStates[0]);
        Assertions.assertEquals(expectedStates[1], actualStates[1]);

        for (int i = 2; i < actions.length; i++) {
            Action currentAction = actions[i];
            Assertions.assertThrows(IllegalActionException.class, () -> stateService.executeAction(currentAction, state));
        }
    }

    @Test
    @DisplayName("Executing multiple option capture")
    void test7() {
        Board board = boardConverter.convertToEntityAttribute(
                """
                        _,_,_,_,_,_,_,_,_,_
                        _,_,_,_,_,_,_,_,_,_
                        _,_,_,_,_,_,_,_,_,_
                        _,_,_,_,_,_,_,_,_,_
                        _,O,_,_,_,O,_,O,_,_
                        _,_,_,_,_,_,_,_,_,_
                        _,_,_,O,_,O,_,_,_,_
                        _,_,_,_,X,_,_,_,_,_
                        _,_,_,_,_,_,_,_,_,_
                        _,_,_,_,_,_,_,_,_,_
                        """
        );
        State state = new State(
                board,
                true
        );

        Coordinates piecePosition = new Coordinates(7, 4);
        Action[] actions = {
                new Action(ActionType.CAPTURE, Direction.UP_LEFT, piecePosition, "TestUser"),
                new Action(ActionType.CAPTURE, Direction.UP_RIGHT, piecePosition, "TestUser"),
                new Action(ActionType.MOVE, Direction.UP_LEFT, piecePosition, "TestUser"),
                new Action(ActionType.MOVE, Direction.UP_RIGHT, piecePosition, "TestUser"),
                new Action(ActionType.MOVE, Direction.DOWN_LEFT, piecePosition, "TestUser"),
                new Action(ActionType.MOVE, Direction.DOWN_RIGHT, piecePosition, "TestUser"),
                new Action(ActionType.CAPTURE, Direction.DOWN_LEFT, piecePosition, "TestUser"),
                new Action(ActionType.CAPTURE, Direction.DOWN_RIGHT, piecePosition, "TestUser")
        };


        State[] expectedStates = {
                new State(
                        boardConverter.convertToEntityAttribute(
                                """
                                        _,_,_,_,_,_,_,_,_,_
                                        _,_,_,_,_,_,_,_,_,_
                                        _,_,_,_,_,_,_,_,_,_
                                        X,_,_,_,_,_,_,_,_,_
                                        _,_,_,_,_,O,_,O,_,_
                                        _,_,_,_,_,_,_,_,_,_
                                        _,_,_,_,_,O,_,_,_,_
                                        _,_,_,_,_,_,_,_,_,_
                                        _,_,_,_,_,_,_,_,_,_
                                        _,_,_,_,_,_,_,_,_,_
                                        """
                        ),
                        false,
                        actions[0]
                ),
                new State(
                        boardConverter.convertToEntityAttribute(
                                """
                                        _,_,_,_,_,_,_,_,_,_
                                        _,_,_,_,_,_,_,_,_,_
                                        _,_,_,_,_,_,_,_,_,_
                                        _,_,_,_,_,_,_,_,_,_
                                        _,O,_,_,_,O,_,O,_,_
                                        _,_,_,_,_,_,X,_,_,_
                                        _,_,_,O,_,_,_,_,_,_
                                        _,_,_,_,_,_,_,_,_,_
                                        _,_,_,_,_,_,_,_,_,_
                                        _,_,_,_,_,_,_,_,_,_
                                        """
                        ),
                        true,
                        actions[1]
                )
        };

        State[] actualStates = {
                stateService.executeAction(actions[0], state),
                stateService.executeAction(actions[1], state),
        };

        Assertions.assertEquals(expectedStates[0].getBoard(), actualStates[0].getBoard());
        Assertions.assertEquals(expectedStates[0].getOriginAction(), actualStates[0].getOriginAction());
        Assertions.assertEquals(expectedStates[0].isPlayer1Turn(), actualStates[0].isPlayer1Turn());
        Assertions.assertEquals(expectedStates[1].getBoard(), actualStates[1].getBoard());
        Assertions.assertEquals(expectedStates[1].getOriginAction(), actualStates[1].getOriginAction());
        Assertions.assertEquals(expectedStates[1].isPlayer1Turn(), actualStates[1].isPlayer1Turn());

        for (int i = 2; i < actions.length; i++) {
            Action currentAction = actions[i];
            Assertions.assertThrows(IllegalActionException.class, () -> stateService.executeAction(currentAction, state));
        }
    }

    @Test
    @DisplayName("Finding best move")
    void test8() {
        Board board = boardConverter.convertToEntityAttribute(
                """
                        _,_,_,_,_,_,_,_,_,_
                        _,_,_,_,_,_,_,_,_,_
                        _,_,_,_,_,_,_,O,_,_
                        _,_,_,_,_,_,_,_,_,_
                        _,O,_,_,_,O,_,O,_,_
                        _,_,_,_,_,_,_,_,_,_
                        _,_,_,O,_,O,_,_,_,_
                        _,_,_,_,X,_,_,_,_,_
                        _,_,_,_,_,_,_,_,_,_
                        _,_,_,_,_,_,_,_,_,_
                        """
        );
        State state = new State(
                board,
                true
        );


        State expectedState = new State(
                boardConverter.convertToEntityAttribute(
                        """
                                _,_,_,_,_,_,_,_,_,_
                                _,_,_,_,_,_,X,_,_,_
                                _,_,_,_,_,_,_,_,_,_
                                _,_,_,_,_,_,_,_,_,_
                                _,O,_,_,_,O,_,_,_,_
                                _,_,_,_,_,_,_,_,_,_
                                _,_,_,O,_,_,_,_,_,_
                                _,_,_,_,_,_,_,_,_,_
                                _,_,_,_,_,_,_,_,_,_
                                _,_,_,_,_,_,_,_,_,_
                                """
                ),
                false
        );

        State actualState = stateService.findBestMove(state, 9);
        Assertions.assertEquals(expectedState, actualState);
    }

    @Test
    @DisplayName("Hard_Bot vs Easy_Bot")
    void test9() {
        State currentState = new State(
                boardConverter.convertToEntityAttribute(
                        """
                                _,O,_,O,_,O,_,O
                                O,_,O,_,O,_,O,_
                                _,O,_,O,_,O,_,O
                                _,_,_,_,_,_,_,_
                                _,_,_,_,_,_,_,_
                                X,_,X,_,X,_,X,_
                                _,X,_,X,_,X,_,X
                                X,_,X,_,X,_,X,_
                                """
                ),
                true
        );

        while(!stateService.isFinal(currentState)) {
            currentState = stateService.findBestMove(currentState, Difficulty.EASY.value());
            if (!stateService.isFinal(currentState)) {
                currentState = stateService.findBestMove(currentState, Difficulty.HARD.value());
            }
        }

        Assertions.assertTrue(stateService.evaluate(currentState) < 0);
    }
}