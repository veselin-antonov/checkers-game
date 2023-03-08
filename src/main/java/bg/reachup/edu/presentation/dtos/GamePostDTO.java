package bg.reachup.edu.presentation.dtos;

import bg.reachup.edu.data.entities.Difficulty;
import bg.reachup.edu.data.entities.GameMode;
import bg.reachup.edu.presentation.validation.EnumValue;
import bg.reachup.edu.presentation.validation.Username;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;

public record GamePostDTO(
        @JsonIgnore
        Long id,
        @NotNull(message = "Missing player username!")
        @Username(message = "Invalid player1 username!")
        String player1,

        @Username(message = "Invalid player2 username!")
        String player2,
        @NotNull(message = "Missing game mode!")
        @EnumValue(enumClass = GameMode.class, message = "Invalid game mode!")
        String mode,
        @EnumValue(enumClass = Difficulty.class, message = "Invalid game difficulty!")
        String difficulty
) {

    public GamePostDTO {
        if (mode != null) {
            mode = mode.toUpperCase();
        }
        if (difficulty != null) {
            difficulty = difficulty.toUpperCase();
        }
    }

    @AssertTrue(message = "Cannot create singleplayer game with 2 players!")
    public boolean isSecondPlayerMissingInSingleplayer() {
        return mode == null ||  player2 == null || GameMode.valueOf(mode) == GameMode.MULTIPLAYER;
    }

    @AssertTrue(message = "Missing game difficulty!")
    public boolean isDifficultyPresentInSingleplayer() {
        return mode == null ||  difficulty != null || GameMode.valueOf(mode) == GameMode.MULTIPLAYER;
    }

    @AssertTrue(message = "Cannot select difficulty in a multiplayer game!")
    public boolean isDifficultyMissingInMultiplayer() {
        return mode == null ||  difficulty == null || GameMode.valueOf(mode) == GameMode.SINGLEPLAYER;
    }
}