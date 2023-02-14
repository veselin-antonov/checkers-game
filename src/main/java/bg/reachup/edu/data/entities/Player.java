package bg.reachup.edu.data.entities;

import javax.persistence.*;

@Entity
@Table(name="players")
public class Player {

    public Player() {
    }

    public Player(String username, String email, int gamesPlayed) {
        this.username = username;
        this.email = email;
        this.gamesPlayed = gamesPlayed;
    }

    @Id
    @GeneratedValue
    @Column(
            updatable = false
    )
    private Long id;
    @Column(
            nullable = false,
            unique = true
    )
    private String username;

    @Column(
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

