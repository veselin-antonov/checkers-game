package bg.reachup.edu.buisness;

import bg.reachup.edu.buisness.exceptions.IllegalActionException;
import bg.reachup.edu.buisness.exceptions.NoPieceAtCoordinatesException;
import bg.reachup.edu.buisness.exceptions.OpponentPieceException;
import bg.reachup.edu.buisness.utils.Pair;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class State {
    private final Action originAction;
    private final List<Piece> whitePieces;
    private final List<Piece> blackPieces;
    private boolean isPlayer1Turn;
    private final int captureStreak;
    private final Board board;
    private List<State> children;
    private final Piece attacker;

    public final int maxStateScore;
    public final int minStateScore;


    public State(Board board, Action originAction, boolean isPlayer1Turn, Piece attacker, int captureStreak) {
        this.board = board;
        this.originAction = originAction;
        this.isPlayer1Turn = isPlayer1Turn;
        whitePieces = new LinkedList<>();
        blackPieces = new LinkedList<>();
        board.getPieces(whitePieces, blackPieces);
        this.captureStreak = captureStreak;
        this.attacker = attacker;
        this.maxStateScore = whitePieces.size() * Piece.QUEEN_PIECE_COST + 1;
        this.minStateScore = (blackPieces.size() * Piece.QUEEN_PIECE_COST + 1) * -1;
    }

    public State(Board board, Action originAction, boolean isPlayer1Turn) {
        this(board, originAction, isPlayer1Turn, null, 0);
    }


    public State(Board board, boolean isPlayer1Turn) {
        this(board, null, isPlayer1Turn, null, 0);
    }

    public Piece getAttacker() {
        return attacker;
    }

    public int getCaptureStreak() {
        return captureStreak;
    }

    public boolean isPlayer1Turn() {
        return isPlayer1Turn;
    }

    public Board getBoard() {
        return board;
    }

    public void setPlayer1Turn(boolean player1Turn) {
        isPlayer1Turn = player1Turn;
    }

    public List<State> getChildren() {
        if (children != null) {
            return children;
        }
        children = new LinkedList<>();
        List<Piece> pieces = isPlayer1Turn ? whitePieces : blackPieces;
        pieces.forEach(piece -> children.addAll(captureWithPiece(piece)));
        children.removeIf(Objects::isNull);
        if (children.isEmpty()) {
            pieces.forEach(piece -> children.addAll(movePiece(piece)));
        }
        children.removeIf(Objects::isNull);
        return children;
    }

    public Action getOriginAction() {
        return this.originAction;
    }

    private record MoveAction(
            Direction moveDirection,
            Coordinates positionOffset,
            Predicate<Piece> predicate
    ) {
    }

    MoveAction[] possibleMoveActions = {
            new MoveAction(
                    Direction.UP_LEFT,
                    new Coordinates(-1, -1),
                    Piece::isWhite
            ),
            new MoveAction(
                    Direction.UP_RIGHT,
                    new Coordinates(-1, 1),
                    Piece::isWhite
            ),
            new MoveAction(
                    Direction.DOWN_LEFT,
                    new Coordinates(1, -1),
                    Piece::isBlack
            ),
            new MoveAction(
                    Direction.DOWN_RIGHT,
                    new Coordinates(1, 1),
                    Piece::isBlack
            )
    };

    private List<State> movePiece(Piece piece) {
        List<State> newStates = new LinkedList<>();
        for (MoveAction moveAction : possibleMoveActions) {
            newStates.add(tryMove(piece, moveAction));
        }
        return newStates;
    }

    private State tryMove(
            Piece piece,
            MoveAction action
    ) {
        Direction moveDirection = action.moveDirection;
        Coordinates positionOffset = action.positionOffset;
        Predicate<Piece> predicate = action.predicate;

        Coordinates newCoordinates = piece.getCoordinates().modified(positionOffset.row(), positionOffset.column());
        if ((piece.isQueen() || predicate.test(piece))
                && board.isWithin(newCoordinates)
                && board.isFree(newCoordinates)
        ) {
            Board newBoard = board.movePiece(piece.getCoordinates(), newCoordinates);
            Action currentAction = new Action(ActionType.MOVE, moveDirection, piece.getCoordinates(), "fix the name");
            return new State(newBoard, currentAction, !isPlayer1Turn);
        }
        return null;
    }

    private record CaptureAction(
            Direction captureDirection,
            Coordinates opponentOffset,
            Coordinates newPositionOffset,
            Predicate<Piece> predicate
    ) {
    }

    private final CaptureAction[] possibleCaptureActions = {
            new CaptureAction(
                    Direction.UP_LEFT,
                    new Coordinates(-1, -1),
                    new Coordinates(-2, -2),
                    Piece::isWhite
            ),
            new CaptureAction(
                    Direction.UP_RIGHT,
                    new Coordinates(-1, 1),
                    new Coordinates(-2, 2),
                    Piece::isWhite
            ),
            new CaptureAction(
                    Direction.DOWN_LEFT,
                    new Coordinates(1, -1),
                    new Coordinates(2, -2),
                    Piece::isBlack
            ),
            new CaptureAction(
                    Direction.DOWN_RIGHT,
                    new Coordinates(1, 1),
                    new Coordinates(2, 2),
                    Piece::isBlack
            )
    };

    private List<State> captureWithPiece(Piece piece) {
        List<Pair<Integer, List<State>>> bestStatesForEachDirection = new LinkedList<>();
        for (CaptureAction captureAction : possibleCaptureActions) {
            Pair<Integer, List<State>> captureStates = tryCaptureWith(piece, captureAction);
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

    private Pair<Integer, List<State>> tryCaptureWith(
            Piece piece,
            CaptureAction action
    ) {

        Direction captureDirection = action.captureDirection;
        Coordinates opponentOffset = action.opponentOffset;
        Coordinates newPositionOffset = action.newPositionOffset;
        Predicate<Piece> predicate = action.predicate;

        Coordinates oldPos = piece.getCoordinates();
        Coordinates opponentPos = oldPos.modified(opponentOffset.row(), opponentOffset.column());
        Coordinates newPos = oldPos.modified(newPositionOffset.row(), newPositionOffset.column());
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
        Action currentAction = new Action(ActionType.CAPTURE, captureDirection, oldPos, "fix the name");
        State newState = new State(newBoard, currentAction, isPlayer1Turn, newBoard.getAt(newPos), captureStreak + 1);

        Map.Entry<Integer, List<State>> bestCaptureStreakStates = newState.captureWithPiece(newState.attacker).stream()
                .collect(Collectors.groupingBy(State::getCaptureStreak, TreeMap::new, Collectors.toList()))
                .lastEntry();
        if (bestCaptureStreakStates != null) {
            return new Pair<>(bestCaptureStreakStates.getKey(), bestCaptureStreakStates.getValue());
        } else {
            newState.setPlayer1Turn(!isPlayer1Turn);
            return new Pair<>(captureStreak, List.of(newState));
        }
    }

    public State executeAction(Action action) {
        Piece piece = board.getAt(action.piecePosition());
        if (piece == null) {
            throw new NoPieceAtCoordinatesException();
        }
        if ((isPlayer1Turn && piece.isBlack()) || (!isPlayer1Turn && piece.isWhite())) {
            throw new OpponentPieceException();
        }
        List<State> possibleCaptures = captureWithPiece(piece);
        if (possibleCaptures.isEmpty() && action.actionType() == ActionType.CAPTURE) {
            throw new IllegalActionException();
        } else if (possibleCaptures.isEmpty() && action.actionType() == ActionType.MOVE) {
            MoveAction moveAction = possibleMoveActions[action.direction().ordinal()];
            State newState = tryMove(piece, moveAction);
            if (newState == null) {
                throw new IllegalActionException();
            } else {
                return newState;
            }
        } else if (!possibleCaptures.isEmpty() && action.actionType() == ActionType.CAPTURE) {
            throw new IllegalActionException();
        } else {
            // TODO: 20.2.2023 Capture execution.. one by one or all at once.. if all - how?
            return null;
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