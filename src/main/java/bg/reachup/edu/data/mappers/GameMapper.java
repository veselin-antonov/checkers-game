package bg.reachup.edu.data.mappers;

import bg.reachup.edu.buisness.Board;
import bg.reachup.edu.data.dtos.GameDTO;
import bg.reachup.edu.data.entities.Game;
import bg.reachup.edu.data.entities.Player;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface GameMapper {

    @Mapping(source = "player1", target = "player1Username", qualifiedByName = "playerParser")
    @Mapping(source = "player2", target = "player2username", qualifiedByName = "playerParser")
    @Mapping(target = "board", qualifiedByName = "boardParser")
    GameDTO toDTO(Game entity);

    @Named("playerParser")
    default String parsePlayer(Player player) {
        return player == null ? "null" : player.getUsername();
    }

    @Named("boardParser")
    default String parseBoard(Board board) {
        return board == null ? "null" : board.toString();
    }
}
