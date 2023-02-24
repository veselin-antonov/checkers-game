package bg.reachup.edu.data.entities;

import bg.reachup.edu.data.converters.BoardConverter;

import javax.annotation.PostConstruct;
import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

@Entity
public class State {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Transient
    private final List<Piece> whitePieces;
    @Transient
    private final List<Piece> blackPieces;
    private boolean player1Turn;
    @Transient
    private final int captureStreak;
    @Convert(converter = BoardConverter.class)
    private final Board board;
    @Transient
    private List<State> children;
    @Transient
    private final Piece attacker;

    @Transient
    private int maxStateScore;
    @Transient
    private int minStateScore;

    protected State() {
        this(null, false, null, 0);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public State(Board board, boolean player1Turn, Piece attacker, int captureStreak) {
        this.board = board;
        this.player1Turn = player1Turn;
        this.whitePieces = new LinkedList<>();
        this.blackPieces = new LinkedList<>();
        this.captureStreak = captureStreak;
        this.attacker = attacker;
        this.maxStateScore = 0;
        this.minStateScore = 0;
    }

    public State(Board board, boolean player1Turn) {
        this(board, player1Turn, null, 0);
    }

    @PostConstruct
    private void init() {
        board.getPieces(whitePieces, blackPieces);
        this.maxStateScore = whitePieces.size() * Piece.QUEEN_PIECE_VALUE + 1;
        this.minStateScore = (blackPieces.size() * Piece.QUEEN_PIECE_VALUE + 1) * -1;
    }

    public List<Piece> getWhitePieces() {
        return whitePieces;
    }

    public List<Piece> getBlackPieces() {
        return blackPieces;
    }

    public boolean isPlayer1Turn() {
        return player1Turn;
    }

    public void setPlayer1Turn(boolean player1Turn) {
        this.player1Turn = player1Turn;
    }

    public int getCaptureStreak() {
        return captureStreak;
    }

    public Board getBoard() {
        return board;
    }

    public List<State> getChildren() {
        return children;
    }

    public void setChildren(List<State> children) {
        this.children = children;
    }

    public Piece getAttacker() {
        return attacker;
    }

    public int getMaxStateScore() {
        return maxStateScore;
    }

    public int getMinStateScore() {
        return minStateScore;
    }

    @Override
    public String toString() {
        return board.toString();
    }
}
