package bg.reachup.edu.data.mappers;

import bg.reachup.edu.data.dtos.PlayerDTO;
import bg.reachup.edu.data.entities.Player;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PlayerMapper {
    PlayerDTO toDTO(Player entity);

    List<PlayerDTO> toDTOs(Iterable<Player> entities);

    @Mapping(target = "id", ignore = true)
    Player toEntity(PlayerDTO dto);
}
