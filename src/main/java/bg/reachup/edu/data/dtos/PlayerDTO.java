package bg.reachup.edu.data.dtos;

public record PlayerDTO(
        String username,
        String email,
        int gamesPlayed
) {
}
