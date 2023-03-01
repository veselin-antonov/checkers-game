package bg.reachup.edu.presentation.controllers;

import bg.reachup.edu.buisness.services.GameService;
import bg.reachup.edu.presentation.dtos.ActionDTO;
import bg.reachup.edu.presentation.dtos.GameGetDTO;
import bg.reachup.edu.presentation.dtos.GamePostDTO;
import bg.reachup.edu.presentation.mappers.GameMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;

@Validated
@RestController
@RequestMapping("/games")
public class GameController {

    private final GameService service;
    private final GameMapper mapper;

    @Autowired
    public GameController(GameService service, GameMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody List<GameGetDTO> getAllGames() {
        return service.getAll().stream().map(mapper::toDTO).toList();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody GameGetDTO getByID(

            @Pattern(regexp = "\\d+", message = "Game id must be a positive integer!")
            @PathVariable("id")
            String id
    ) {
        return mapper.toDTO(service.getByID(Long.parseLong(id)));
    }

    @PostMapping("")
    @ResponseStatus(value = HttpStatus.CREATED, reason = "New game was created successfully")
    public @ResponseBody GameGetDTO createNewGame(@RequestBody GamePostDTO playerUsernames) {
        return mapper.toDTO(service.createNewGame(playerUsernames.player1(), playerUsernames.player2()));
    }

    @PostMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody GameGetDTO makeMove(

            @Pattern(regexp = "\\d+", message = "Invalid game ID!")
            @PathVariable("id")
            String gameID,

            @NotNull
            @Valid
            @RequestBody
            ActionDTO actionDTO
    ) {
        return mapper.toDTO(service.makeMove(Long.parseLong(gameID), actionDTO));
    }
}
