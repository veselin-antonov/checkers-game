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

        State actualState =  stateService.executeAction(actions[0], state);

        Assertions.assertEquals(expectedState, actualState);


        for (int i = 1; i < actions.length; i++) {
            Action currentAction = actions[i];
            Assertions.assertThrows(IllegalActionException.class, () -> stateService.executeAction(currentAction, state));
        }
    }
}