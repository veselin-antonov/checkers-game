package bg.reachup.edu.presentation.mappers;

import bg.reachup.edu.presentation.dtos.GameGetDTO;
import bg.reachup.edu.data.entities.Game;
import bg.reachup.edu.data.entities.Player;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring", uses = StateMapper.class)
public interface GameMapper {

    @Mapping(source = "player1", target = "player1Username", qualifiedByName = "playerParser")
    @Mapping(source = "player2", target = "player2username", qualifiedByName = "playerParser")
    GameGetDTO toDTO(Game entity);

    @Named("playerParser")
    default String parsePlayer(Player player) {
        return player.getUsername();
    }
}
