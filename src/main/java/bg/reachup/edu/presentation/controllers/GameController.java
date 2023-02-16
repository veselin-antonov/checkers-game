package bg.reachup.edu.presentation.controllers;

import bg.reachup.edu.buisness.services.GameService;
import bg.reachup.edu.data.dtos.GameDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/games")
public class GameController {

    GameService service;

    @Autowired
    public GameController(GameService service) {
        this.service = service;
    }

    @GetMapping("")
    public @ResponseBody List<GameDTO> getAllGames() {
        return service.getAll();
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody GameDTO getByID(@PathVariable Long id) {
        return service.getByID(id);
    }

    @PostMapping("")
    @ResponseStatus(value = HttpStatus.CREATED, reason = "New game was created successfully")
    public @ResponseBody GameDTO createNewGame(@RequestBody Map<String, String> playerUsernames) {
        return service.createNewGame(playerUsernames.get("player1"), playerUsernames.get("player2"));
    }

    @RequestMapping("/test-data")
    @ResponseStatus(value = HttpStatus.CREATED, reason = "Test game was inserted into the database")
    public void testData() {
        service.insertTestData();
    }
}
