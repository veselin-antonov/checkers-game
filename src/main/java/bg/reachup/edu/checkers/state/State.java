package bg.reachup.edu.checkers.state;

import bg.reachup.edu.checkers.state.actions.Action;
import bg.reachup.edu.checkers.state.actions.ActionType;
import bg.reachup.edu.checkers.state.actions.Direction;
import bg.reachup.edu.utils.Pair;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class State {
    private final Action originAction;
    private final List<Piece> whitePieces;
    private final List<Piece> blackPieces;
    private boolean isWhitesTurn;
    private final int captureStreak;
    private final Board board;
    private List<State> children;
    private final Piece attacker;

    public final int maxStateScore;
    public final int minStateScore;


    public State(Board board, Action originAction, boolean isWhitesTurn, Piece attacker, int captureStreak) {
        this.board = board;
        this.originAction = originAction;
        this.isWhitesTurn = isWhitesTurn;
        whitePieces = new LinkedList<>();
        blackPieces = new LinkedList<>();
        board.getPieces(whitePieces, blackPieces);
        this.captureStreak = captureStreak;
        this.attacker = attacker;
        this.maxStateScore = whitePieces.size() * Piece.QUEEN_PIECE_COST + 1;
        this.minStateScore = (blackPieces.size() * Piece.QUEEN_PIECE_COST + 1) * -1;
    }

    public State(Board board, Action originAction, boolean isWhitesTurn) {
        this(board, originAction, isWhitesTurn, null, 0);
    }


    public State(Board board, boolean isWhitesTurn) {
        this(board, null, isWhitesTurn, null, 0);
    }

    public Piece getAttacker() {
        return attacker;
    }

    public int getCaptureStreak() {
        return captureStreak;
    }

    public boolean isWhitesTurn() {
        return isWhitesTurn;
    }

    public Board getBoard() {
        return board;
    }

    public void setWhitesTurn(boolean whitesTurn) {
        isWhitesTurn = whitesTurn;
    }

    public List<State> getChildren() {
        if (children != null) {
            return children;
        }
        children = new LinkedList<>();
        List<Piece> pieces = isWhitesTurn ? whitePieces : blackPieces;
        pieces.forEach(piece -> children.addAll(captureWithPiece(piece)));
        if (children.isEmpty()) {
            pieces.forEach(this::movePiece);
        }
        return children;
    }

    public Action getOriginAction() {
        return this.originAction;
    }

    @FunctionalInterface
    interface MoveAction {
        void tryMove(Piece piece);
    }

    MoveAction[] possibleMoveActions = {
            piece -> tryMoveInDirection(
                    piece,
                    Direction.DOWN_RIGHT,
                    new Coordinates(1, 1),
                    Piece::isBlack
            ),
            piece -> tryMoveInDirection(
                    piece,
                    Direction.DOWN_LEFT,
                    new Coordinates(1, -1),
                    Piece::isBlack),
            piece -> tryMoveInDirection(
                    piece,
                    Direction.UP_RIGHT,
                    new Coordinates(-1, 1),
                    Piece::isWhite),
            piece -> tryMoveInDirection(
                    piece,
                    Direction.UP_LEFT,
                    new Coordinates(-1, -1),
                    Piece::isWhite)
    };

    private void movePiece(Piece piece) {
        for (MoveAction moveAction : possibleMoveActions) {
            moveAction.tryMove(piece);
        }
    }

    private void tryMoveInDirection(
            Piece piece,
            Direction moveDirection,
            Coordinates changeValues,
            Predicate<Piece> predicate
    ) {
        Coordinates newCoordinates = piece.getCoordinates().modified(changeValues.row(), changeValues.column());
        if ((piece.isQueen() || predicate.test(piece))
                && board.isWithin(newCoordinates)
                && board.isFree(newCoordinates)
        ) {
            Board newBoard = board.movePiece(piece.getCoordinates(), newCoordinates);
            Action currentAction = new Action(ActionType.MOVE, moveDirection, newBoard.getAt(newCoordinates), piece.getCoordinates());
            children.add(new State(newBoard, currentAction, !isWhitesTurn));
        }
    }

    @FunctionalInterface
    interface CaptureAction {
        Pair<Integer, List<State>> tryCaptureWith(Piece piece);
    }

    CaptureAction[] possibleCaptureActions = {
            piece -> tryCaptureInDirection(
                    piece,
                    Direction.DOWN_RIGHT,
                    new Coordinates(1, 1),
                    new Coordinates(2, 2),
                    Piece::isBlack
            ),
            piece -> tryCaptureInDirection(
                    piece,
                    Direction.DOWN_LEFT,
                    new Coordinates(1, -1),
                    new Coordinates(2, -2),
                    Piece::isBlack
            ),
            piece -> tryCaptureInDirection(
                    piece,
                    Direction.UP_RIGHT,
                    new Coordinates(-1, 1),
                    new Coordinates(-2, 2),
                    Piece::isWhite
            ),
            piece -> tryCaptureInDirection(
                    piece,
                    Direction.UP_LEFT,
                    new Coordinates(-1, -1),
                    new Coordinates(-2, -2),
                    Piece::isWhite
            )
    };

    private List<State> captureWithPiece(Piece piece) {
        List<Pair<Integer, List<State>>> bestStatesForEachDirection = new LinkedList<>();
        for (CaptureAction captureAction : possibleCaptureActions) {
            Pair<Integer, List<State>> captureStates = captureAction.tryCaptureWith(piece);
            if (captureStates != null) {
                bestStatesForEachDirection.add(captureStates);
            }
        }
        if (bestStatesForEachDirection.isEmpty()) {
            return List.of();
        }
        int maxCaptureStreak = bestStatesForEachDirection.stream()
                .mapToInt(Pair::value1)
                .max()
                .orElse(0);
        return bestStatesForEachDirection.stream()
                .filter(pair -> pair.value1() == maxCaptureStreak)
                .map(Pair::value2)
                .flatMap(List::stream)
                .toList();
    }

    private Pair<Integer, List<State>> tryCaptureInDirection(
            Piece piece,
            Direction captureDirection,
            Coordinates changeValuesForOpponent,
            Coordinates changeValuesForNewPos,
            Predicate<Piece> predicate
    ) {
        Coordinates oldPos = piece.getCoordinates();
        Coordinates opponentPos = oldPos.modified(changeValuesForOpponent.row(), changeValuesForOpponent.column());
        Coordinates newPos = oldPos.modified(changeValuesForNewPos.row(), changeValuesForNewPos.column());
        if ((!predicate.test(piece) && !piece.isQueen())
                || !board.isWithin(newPos)
                || !board.isFree(newPos)
        ) {
            return null;
        }
        Piece toCapture = board.getAt(opponentPos);
        if (toCapture == null || !toCapture.isOpponent(piece)) {
            return null;
        }
        Board newBoard = board.movePiece(oldPos, newPos);
        newBoard.removeAt(toCapture.getCoordinates());
        Action currentAction = new Action(ActionType.CAPTURE, captureDirection, newBoard.getAt(newPos), oldPos);
        State newState = new State(newBoard, currentAction, isWhitesTurn, newBoard.getAt(newPos), captureStreak + 1);

        Map.Entry<Integer, List<State>> bestCaptureStreakStates = newState.captureWithPiece(newState.attacker).stream()
                .collect(Collectors.groupingBy(State::getCaptureStreak, TreeMap::new, Collectors.toList()))
                .lastEntry();
        if (bestCaptureStreakStates != null) {
            return new Pair<>(bestCaptureStreakStates.getKey(), bestCaptureStreakStates.getValue());
        } else {
            newState.setWhitesTurn(!isWhitesTurn);
            return new Pair<>(captureStreak, List.of(newState));
        }
    }

    private int getPiecesScore(List<Piece> pieces) {
        return pieces.stream().mapToInt(Piece::getValue).sum();
    }

    public int evaluate() {
        return getPiecesScore(whitePieces) - getPiecesScore(blackPieces);
    }

    public boolean isFinal() {
        return whitePieces.isEmpty() || blackPieces.isEmpty() || getChildren().isEmpty();
    }

    @Override
    public String toString() {
        return board.toString();
    }
}