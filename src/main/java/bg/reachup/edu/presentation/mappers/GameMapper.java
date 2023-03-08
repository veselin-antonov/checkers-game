package bg.reachup.edu.presentation.mappers;

import bg.reachup.edu.data.entities.Game;
import bg.reachup.edu.data.entities.Player;
import bg.reachup.edu.data.entities.State;
import bg.reachup.edu.presentation.dtos.GameGetDTO;
import bg.reachup.edu.presentation.dtos.GamePostDTO;
import bg.reachup.edu.presentation.dtos.StateDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Arrays;
import java.util.List;

@Mapper(componentModel = "spring")
public interface GameMapper {

    @Mapping(target = "player1", expression = "java(entity.getPlayer1().getUsername())")
    @Mapping(target = "player2", expression = "java(entity.getPlayer2() == null ? \"\" : entity.getPlayer2().getUsername())")
    @Mapping(target = "state", expression = "java(convertToStateDTO(entity.getState(), entity))")
    GameGetDTO toGetDTO(Game entity);

    List<GameGetDTO> toGetDTOs(List<Game> entities);

    @Mapping(target = "player1", expression = "java(entity.getPlayer1().getUsername())")
    @Mapping(target = "player2", expression = "java(entity.getPlayer2() == null ? \"\" : entity.getPlayer2().getUsername())")
    GamePostDTO toPostDTO(Game entity);

    @Mapping(target = "state", ignore = true)
    @Mapping(target = "player1", qualifiedByName = "createPlayer")
    @Mapping(target = "player2", qualifiedByName = "createPlayer")
    Game toEntity(GamePostDTO dto);

    default StateDTO convertToStateDTO(State entity, Game game) {
        return new StateDTO(
                Arrays.asList(entity.getBoard().toString().split("\n")),
                (entity.isPlayer1Turn() ? game.getPlayer1() : game.getPlayer2()).getUsername(),
                entity.isFinished()
        );
    }

    @Named("createPlayer")
    default Player createPlayer(String username) {
        return username == null ? null : new Player(username, null);
    }
}
