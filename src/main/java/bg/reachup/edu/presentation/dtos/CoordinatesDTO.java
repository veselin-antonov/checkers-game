package bg.reachup.edu.presentation.dtos;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public record CoordinatesDTO (
        @NotNull(message = "Row coordinate is missing!")
        @Min(value = 0, message = "Row coordinate should not be less than 0!")
        Integer row,
        @NotNull(message = "Column coordinate is missing!")
        @Min(value = 0, message = "Column coordinate should not be less than 0!")
        Integer column
) {}
