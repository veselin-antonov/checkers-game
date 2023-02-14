package bg.reachup.edu.data.entities;

import org.hibernate.annotations.Cascade;

import javax.persistence.*;

@Entity
@Table(name = "games")
public class Game {

    public Game() {
    }

    public Game(
            Player player1,
            Player player2,
            String board,
            boolean isWhitesTurn,
            boolean isFinished
    ) {
        this.player1 = player1;
        this.player2 = player2;
        this.board = board;
        this.isWhitesTurn = isWhitesTurn;
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
    private String board;
    private boolean isWhitesTurn;
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

    public String getBoard() {
        return board;
    }

    public void setBoard(String board) {
        this.board = board;
    }

    public boolean isWhitesTurn() {
        return isWhitesTurn;
    }

    public void setWhitesTurn(boolean whitesTurn) {
        isWhitesTurn = whitesTurn;
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
                ", isWhitesTurn=" + isWhitesTurn +
                '}';
    }
}
