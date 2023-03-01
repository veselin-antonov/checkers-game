package bg.reachup.edu.data.entities;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "players")
public class Player {

    public Player() {
    }

    public Player(String username, String email) {
        this.username = username;
        this.email = email;
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
    @ColumnDefault("0")
    @Generated(GenerationTime.INSERT)
    private Integer wins;
    @ColumnDefault("0")
    @Generated(GenerationTime.INSERT)
    private Integer losses;
    @ColumnDefault("0")
    @Generated(GenerationTime.INSERT)
    private Integer ties;

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

    public Integer getWins() {
        return wins;
    }

    public void giveWin() {
        wins++;
    }

    public void setWins(Integer gamesWon) {
        this.wins = gamesWon;
    }

    public Integer getLosses() {
        return losses;
    }

    public void giveLoss() {
        losses++;
    }

    public void setLosses(Integer gamesLost) {
        this.losses = gamesLost;
    }

    public Integer getTies() {
        return ties;
    }

    public void giveTie() {
        ties++;
    }

    public void setTies(Integer gamesTied) {
        this.ties = gamesTied;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return Objects.equals(id, player.id) && Objects.equals(username, player.username) && Objects.equals(email, player.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, email);
    }

    @Override
    public String toString() {
        return "Player{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", gamesWon=" + wins +
                ", gamesLost=" + losses +
                ", gamesTied=" + ties +
                '}';
    }
}

