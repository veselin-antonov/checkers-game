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
            State state,
            boolean isFinished
    ) {
        this.player1 = player1;
        this.player2 = player2;
        this.state = state;
        this.isFinished = isFinished;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "player1_id", referencedColumnName = "id")
    private Player player1;
    @ManyToOne
    @JoinColumn(name = "player2_id", referencedColumnName = "id")
    private Player player2;
    @OneToOne
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    @JoinColumn(name = "state_id", referencedColumnName = "id")
    private State state;
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

    public Player getPlayer2() {
        return player2;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public boolean isFinished() {
        return isFinished;
    }

    @Override
    public String toString() {
        return "%s%nGameID: %d%nPlayer 1: %s%nPlayer 2: %s%nIn turn: %s".formatted(
                state.toString(),
                id,
                player1.getUsername(),
                player2.getUsername(),
                state.isPlayer1Turn() ? player1.getUsername() : player2.getUsername()
        );
    }
}
