package bg.reachup.edu.presentation.controllers;

import bg.reachup.edu.buisness.services.GameService;
import bg.reachup.edu.presentation.dtos.ActionDTO;
import bg.reachup.edu.presentation.dtos.GameGetDTO;
import bg.reachup.edu.presentation.dtos.GamePostDTO;
import bg.reachup.edu.presentation.mappers.ActionMapper;
import bg.reachup.edu.presentation.mappers.GameMapper;
import bg.reachup.edu.presentation.validation.GameID;
import bg.reachup.edu.presentation.validation.Username;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Validated
@RestController
@RequestMapping("/games")
public class GameController {

    private final GameService service;
    private final GameMapper gameMapper;
    private final ActionMapper actionMapper;

    @Autowired
    public GameController(GameService service, GameMapper gameMapper, ActionMapper actionMapper) {
        this.service = service;
        this.gameMapper = gameMapper;
        this.actionMapper = actionMapper;
    }

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<GameGetDTO> getAll() {
        return gameMapper.toGetDTOs(service.getAll());
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public GameGetDTO getByID(

            @PathVariable
            @GameID
            String id
    ) {
        return gameMapper.toGetDTO(service.getByID(Long.parseLong(id)));
    }

    @PostMapping("")
    @ResponseStatus(value = HttpStatus.CREATED, reason = "New game successfully created")
    @ResponseBody
    public GamePostDTO createNewGame(@RequestBody @Valid GamePostDTO gamePostDTO) {
        return gameMapper.toPostDTO(service.createNewGame(gameMapper.toEntity(gamePostDTO)));
    }

    @PostMapping("/{id}/players")
    @ResponseStatus(value = HttpStatus.CREATED, reason = "Game joined successfully")
    @ResponseBody
    public GamePostDTO joinGame(
            @PathVariable
            @GameID
            String id,

            @RequestBody
            @NotNull
            @Username
            String username
    ) {
        return gameMapper.toPostDTO(service.joinGame(Long.parseLong(id), username));
    }

    @PostMapping("/{id}/moves")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public GameGetDTO makeMove(
            @PathVariable("id")
            @GameID
            String gameID,

            @NotNull
            @Valid
            @RequestBody
            ActionDTO actionDTO
    ) {
        return gameMapper.toGetDTO(
                service.makeMove(
                        service.getByID(Long.parseLong(gameID)),
                        actionMapper.toEntity(actionDTO)
                )
        );
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT, reason = "Game successfully deleted")
    public void deleteByID(
            @PathVariable("id")
            @GameID
            String gameID
    ) {
        service.deleteByID(Long.parseLong(gameID));
    }
}
