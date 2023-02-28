package bg.reachup.edu.buisness.services;

import bg.reachup.edu.buisness.exceptions.state.IllegalActionException;
import bg.reachup.edu.data.converters.BoardConverter;
import bg.reachup.edu.data.entities.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class StateServiceTest {

    @Test
    void test1() {
        BoardConverter boardConverter = new BoardConverter();
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
        StateService stateService = new StateService();

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
    void test2() {
        BoardConverter boardConverter = new BoardConverter();
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
        StateService stateService = new StateService();

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
    void test3() {
        BoardConverter boardConverter = new BoardConverter();
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
        StateService stateService = new StateService();

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
    void test4() {
        BoardConverter boardConverter = new BoardConverter();
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
        StateService stateService = new StateService();

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
    void test5() {
        BoardConverter boardConverter = new BoardConverter();
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
        StateService stateService = new StateService();

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
    void test6() {
        BoardConverter boardConverter = new BoardConverter();
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
        StateService stateService = new StateService();

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
    void test7() {
        BoardConverter boardConverter = new BoardConverter();
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
        StateService stateService = new StateService();

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
}