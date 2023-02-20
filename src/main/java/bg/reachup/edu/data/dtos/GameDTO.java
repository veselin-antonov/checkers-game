package bg.reachup.edu.data.dtos;

public record GameDTO (
        Long id,
        String player1Username,
        String player2username,
        String board,
        boolean isPlayer1Turn,
        boolean isFinished
) {}