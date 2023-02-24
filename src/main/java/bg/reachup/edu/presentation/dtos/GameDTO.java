package bg.reachup.edu.presentation.dtos;

public record GameDTO (
        Long id,
        String player1Username,
        String player2username,
        StateDTO state
) {}