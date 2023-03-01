package bg.reachup.edu.presentation.mappers;

import bg.reachup.edu.data.entities.Board;
import bg.reachup.edu.presentation.dtos.StateDTO;
import bg.reachup.edu.data.entities.State;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Arrays;
import java.util.List;

@Mapper(componentModel = "spring", uses = {CoordinatesMapper.class})
public interface StateMapper {
    @Mapping(target = "board", qualifiedByName = "parseBoard")
    StateDTO stateToStateDTO(State entity);

    @Named("parseBoard")
    default List<String> parseBoard(Board board) {
        return Arrays.asList(board.toString().split("\n"));
    }
}
