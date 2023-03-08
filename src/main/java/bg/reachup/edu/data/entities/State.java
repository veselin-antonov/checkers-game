package bg.reachup.edu.data.entities;

import bg.reachup.edu.data.converters.BoardConverter;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "states")
public class State {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "board")
    @Convert(converter = BoardConverter.class)
    private Board board;
    @Column(name = "player1turn")
    private boolean player1Turn;
    @Column(name = "finished")
    private boolean finished;
    @Transient
    private final List<Piece> whitePieces;
    @Transient
    private final List<Piece> blackPieces;
    @Transient
    private final Piece attacker;
    @Transient
    private final int captureStreak;
    @Transient
    private final Action originAction;
    @Transient
    private List<State> children;
    @Transient
    private int maxStateScore;
    @Transient
    private int minStateScore;

    public State(Board board, boolean player1Turn, Piece attacker, int captureStreak, Action originAction) {
        this.board = board;
        this.whitePieces = new LinkedList<>();
        this.blackPieces = new LinkedList<>();
        this.player1Turn = player1Turn;
        this.attacker = attacker;
        this.captureStreak = captureStreak;
        this.originAction = originAction;
        this.maxStateScore = 0;
        this.minStateScore = 0;
        if (board != null) {
            init();
        }
    }

    public State(Board board, boolean player1Turn, Action action) {
        this(board, player1Turn, null, 0, action);
    }

    public State(Board board, boolean player1Turn) {
        this(board, player1Turn, null, 0, null);
    }

    protected State() {
        this(null, false, null, 0, null);
    }

    private void init() {
        board.getPieces(whitePieces, blackPieces);
        this.maxStateScore = whitePieces.size() * Piece.QUEEN_PIECE_VALUE + 1;
        this.minStateScore = (blackPieces.size() * Piece.QUEEN_PIECE_VALUE + 1) * -1;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
        if (board != null) {
            init();
        }
    }

    public boolean isPlayer1Turn() {
        return player1Turn;
    }

    public void setPlayer1Turn(boolean player1Turn) {
        this.player1Turn = player1Turn;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public List<Piece> getWhitePieces() {
        if(whitePieces.isEmpty() && board != null) {
            init();
        }
        return whitePieces;
    }

    public List<Piece> getBlackPieces() {
        if(blackPieces.isEmpty() && board != null) {
            init();
        }
        return blackPieces;
    }

    public Piece getAttacker() {
        return attacker;
    }

    public int getCaptureStreak() {
        return captureStreak;
    }

    public Action getOriginAction() {
        return originAction;
    }

    public List<State> getChildren() {
        return children;
    }

    public void setChildren(List<State> children) {
        this.children = children;
    }

    public int getMaxStateScore() {
        return maxStateScore;
    }

    public int getMinStateScore() {
        return minStateScore;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        State state = (State) o;
        return player1Turn == state.player1Turn && finished == state.finished && Objects.equals(board, state.board);
    }

    @Override
    public int hashCode() {
        return Objects.hash(board, player1Turn, finished);
    }

    @Override
    public String toString() {
        return board.toString();
    }
}
