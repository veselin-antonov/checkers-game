package bg.reachup.edu.presentation.dtos;

import bg.reachup.edu.data.entities.Coordinates;

public record ActionDTO (
        String actionType,
        String direction,
        Coordinates piecePosition,
        String executor
) {}
