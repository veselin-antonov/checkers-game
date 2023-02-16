package bg.reachup.edu.data.mappers;

import bg.reachup.edu.data.dtos.PlayerDTO;
import bg.reachup.edu.data.entities.Player;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface PlayerMapper {
    PlayerDTO toDTO(Player entity);

    void updateEntity(PlayerDTO dto, @MappingTarget Player entity);
}
