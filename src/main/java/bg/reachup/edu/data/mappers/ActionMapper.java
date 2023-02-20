package bg.reachup.edu.data.mappers;

import bg.reachup.edu.buisness.Action;
import bg.reachup.edu.data.dtos.ActionDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ActionMapper {
    Action toEntity(ActionDTO dto);

}
