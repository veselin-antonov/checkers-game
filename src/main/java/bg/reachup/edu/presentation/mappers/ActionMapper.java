package bg.reachup.edu.presentation.mappers;

import bg.reachup.edu.data.entities.Action;
import bg.reachup.edu.presentation.dtos.ActionDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ActionMapper {
    Action toEntity(ActionDTO dto);

}
