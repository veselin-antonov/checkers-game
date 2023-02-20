package bg.reachup.edu.presentation.controllers;

import bg.reachup.edu.buisness.services.GameService;
import bg.reachup.edu.data.dtos.ActionDTO;
import bg.reachup.edu.data.dtos.GameDTO;
import bg.reachup.edu.data.mappers.GameMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/games")
public class GameController {

    GameService service;
    GameMapper mapper;

    @Autowired
    public GameController(GameService service, GameMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody List<GameDTO> getAllGames() {
        return service.getAll().stream().map(mapper::toDTO).toList();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody GameDTO getByID(@PathVariable Long id) {
        return mapper.toDTO(service.getByID(id));
    }

    @PostMapping("")
    @ResponseStatus(value = HttpStatus.CREATED, reason = "New game was created successfully")
    public @ResponseBody GameDTO createNewGame(@RequestBody Map<String, String> playerUsernames) {
        return mapper.toDTO(service.createNewGame(playerUsernames.get("player1"), playerUsernames.get("player2")));
    }

    @PostMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody GameDTO makeMove(@PathVariable("id") Long gameID, @RequestBody() ActionDTO actionDTO) {
        return mapper.toDTO(service.makeMove(gameID, actionDTO));
    }
}
