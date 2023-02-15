package bg.reachup.edu.data.entities;

import bg.reachup.edu.buisness.Board;
import bg.reachup.edu.data.converters.BoardConverter;

import javax.persistence.*;

@Entity
@Table(name = "games")
public class Game {

    public Game() {
    }

    public Game(
            Player player1,
            Player player2,
            Board board,
            boolean isPlayer1Turn,
            boolean isFinished
    ) {
        this.player1 = player1;
        this.player2 = player2;
        this.board = board;
        this.isPlayer1Turn = isPlayer1Turn;
        this.isFinished = isFinished;
    }

    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn
    private Player player1;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn
    private Player player2;
    @Convert(converter = BoardConverter.class)
    private Board board;
    private boolean isPlayer1Turn;
    private boolean isFinished;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Player getPlayer1() {
        return player1;
    }

    public void setPlayer1(Player player1) {
        this.player1 = player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public void setPlayer2(Player player2) {
        this.player2 = player2;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public boolean isPlayer1Turn() {
        return isPlayer1Turn;
    }

    public void setPlayer1Turn(boolean player1) {
        isPlayer1Turn = player1;
    }

    public boolean isFinished() {
        return isFinished;
    }

    public void setFinished(boolean finished) {
        isFinished = finished;
    }

    @Override
    public String toString() {
        return "Game{" +
                "id=" + id +
                ", player1=" + player1.getUsername() +
                ", player2=" + player2.getUsername() +
                ", board='" + board + '\'' +
                ", isWhitesTurn=" + isPlayer1Turn +
                '}';
    }
}
