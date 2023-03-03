package bg.reachup.edu.presentation.controllers;

import bg.reachup.edu.buisness.services.GameService;
import bg.reachup.edu.presentation.dtos.ActionDTO;
import bg.reachup.edu.presentation.dtos.GameGetDTO;
import bg.reachup.edu.presentation.dtos.GamePostDTO;
import bg.reachup.edu.presentation.mappers.ActionMapper;
import bg.reachup.edu.presentation.mappers.GameMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;

@Validated
@RestController
@RequestMapping("/games")
public class GameController {

    private final GameService service;
    private final GameMapper gameMapper;
    private final ActionMapper actionMapper;

    public GameController(GameService service, GameMapper gameMapper, ActionMapper actionMapper) {
        this.service = service;
        this.gameMapper = gameMapper;
        this.actionMapper = actionMapper;
    }

    @Autowired


    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody List<GameGetDTO> getAllGames() {
        return service.getAll().stream().map(gameMapper::toGetDTO).toList();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody GameGetDTO getByID(

            @Pattern(regexp = "\\d+", message = "Game id must be a positive integer!")
            @PathVariable("id")
            String id
    ) {
        return gameMapper.toGetDTO(service.getByID(Long.parseLong(id)));
    }

    @PostMapping("")
    @ResponseStatus(value = HttpStatus.CREATED, reason = "New game was created successfully")
    public @ResponseBody GamePostDTO createNewGame(@RequestBody @Valid GamePostDTO gamePostDTO) {
        return gameMapper.toPostDTO(service.createNewGame(gameMapper.toEntity(gamePostDTO)));
    }

    @PostMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody GameGetDTO makeMove(

            @PathVariable("id")
            @Pattern(regexp = "\\d+", message = "Invalid game ID!")
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
}
