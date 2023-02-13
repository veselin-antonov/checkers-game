package bg.reachup.edu.data.entities;

import javax.persistence.*;

@Entity
@Table(name="players")
public class Player {

    public Player() {
    }

    public Player(String email, int gamesPlayed) {
        this.email = email;
        this.gamesPlayed = gamesPlayed;
    }

    @Id
    @GeneratedValue
    @Column(
            name = "id",
            updatable = false
    )
    private Long id;
    @Column(
            name="email",
            nullable = false,
            unique = true
    )
    private String email;
    private int gamesPlayed;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getGamesPlayed() {
        return gamesPlayed;
    }

    public void setGamesPlayed(int gamesPlayed) {
        this.gamesPlayed = gamesPlayed;
    }

    @Override
    public String toString() {
        return "Player{" +
                "id=" + id +
                ", username='" + email + '\'' +
                ", gamesPlayed=" + gamesPlayed +
                '}';
    }
}

