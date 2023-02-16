package bg.reachup.edu.data.dtos;

public record GameDTO (
        String player1Username,
        String player2username,
        String board,
        boolean isPlayer1Turn,
        boolean isFinished
) {}