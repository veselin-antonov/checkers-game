package bg.reachup.edu.presentation.dtos;

import java.util.List;

public record StateDTO(
        List<String> board,
        boolean player1Turn,
        boolean isFinished
) {
}
