package bg.reachup.edu.presentation.dtos;

import java.util.List;

public record StateDTO(
        List<String> board,
        String currentPlayer,
        boolean finished 
) {
}
