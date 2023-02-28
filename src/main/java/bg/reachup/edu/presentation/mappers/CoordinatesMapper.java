package bg.reachup.edu.presentation.mappers;

import bg.reachup.edu.data.entities.Coordinates;
import bg.reachup.edu.presentation.dtos.CoordinatesDTO;
import org.mapstruct.Mapper;

@Mapper
public interface CoordinatesMapper {
    Coordinates toEntity(CoordinatesDTO dto);
    CoordinatesDTO toDTO(Coordinates entity);
}
