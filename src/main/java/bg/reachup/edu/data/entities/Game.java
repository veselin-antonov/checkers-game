package bg.reachup.edu.data.entities;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "games")
public class Game {

    public Game() {
    }

    public Game(GameMode mode, Difficulty difficulty, Player player1, Player player2, State state) {
        this.mode = mode;
        this.difficulty = difficulty;
        this.player1 = player1;
        this.player2 = player2;
        this.state = state;
    }

    public Game(GameMode mode, Player player1, Player player2, State state) {
        this.mode = mode;
        this.player1 = player1;
        this.player2 = player2;
        this.state = state;
    }

    public Game(GameMode mode, Difficulty difficulty, Player player1, State state) {
        this.mode = mode;
        this.difficulty = difficulty;
        this.player1 = player1;
        this.state = state;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "player1_id", referencedColumnName = "id", nullable = false)
    private Player player1;
    @ManyToOne
    @JoinColumn(name = "player2_id", referencedColumnName = "id")
    private Player player2;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "state_id", referencedColumnName = "id")
    private State state;
    @Column(nullable = false)
    private GameMode mode;
    private Difficulty difficulty;

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

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public GameMode getMode() {
        return mode;
    }

    public void setMode(GameMode gameMode) {
        this.mode = gameMode;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Game game = (Game) o;
        return Objects.equals(id, game.id) && Objects.equals(player1, game.player1) && Objects.equals(player2, game.player2) && Objects.equals(state, game.state);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, player1, player2, state);
    }

    @Override
    public String toString() {
        return "%s%nGameID: %d%nPlayer 1: %s%nPlayer 2: %s%nIn turn: %s".formatted(
                state.toString(),
                id,
                player1.getUsername(),
                player2 == null ? "Bot" : player2.getUsername(),
                state.isPlayer1Turn() ? player1.getUsername() : player2 == null ? "Bot" : player2.getUsername()
        );
    }
}
