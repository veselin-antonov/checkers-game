package bg.reachup.edu.data.dtos;

import bg.reachup.edu.buisness.Coordinates;

public record ActionDTO (
        String actionType,
        String direction,
        Coordinates piecePosition,
        String executor
) {}
