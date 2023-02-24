package bg.reachup.edu.presentation.dtos;

public record PlayerDTO(
        String username,
        String email,
        int gamesPlayed
) {
}
