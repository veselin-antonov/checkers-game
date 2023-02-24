package bg.reachup.edu.presentation.mappers;

import bg.reachup.edu.data.entities.Board;
import bg.reachup.edu.presentation.dtos.StateDTO;
import bg.reachup.edu.data.entities.State;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface StateMapper {
    @Mapping(target = "board", qualifiedByName = "parseBoard")
    StateDTO stateToStateDTO(State entity);

    @Named("parseBoard")
    default String parseBoard(Board board) {
        return board.toString();
    }
}
