package bg.reachup.edu.presentation.mappers;

import bg.reachup.edu.data.entities.Game;
import bg.reachup.edu.presentation.dtos.GameGetDTO;
import bg.reachup.edu.presentation.dtos.StateDTO;
import org.mapstruct.Mapper;

import java.util.Arrays;

@Mapper(componentModel = "spring")
public interface GameMapper {

    default GameGetDTO toDTO(Game entity) {
        return new GameGetDTO(
                entity.getPlayer1().getUsername(),
                entity.getPlayer2().getUsername(),
                new StateDTO(
                        Arrays.asList(entity.getState().getBoard().toString().split("\n")),
                        (entity.getState().isPlayer1Turn() ? entity.getPlayer1() : entity.getPlayer2()).getUsername(),
                        entity.getState().isFinished()
                )
        );
    }
}
