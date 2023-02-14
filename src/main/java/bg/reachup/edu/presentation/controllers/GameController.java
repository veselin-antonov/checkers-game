package bg.reachup.edu.presentation.controllers;

import bg.reachup.edu.buisness.services.GameService;
import bg.reachup.edu.data.entities.Game;
import bg.reachup.edu.data.entities.Player;
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

    @Autowired
    public GameController(GameService service) {
        this.service = service;
    }

    @GetMapping("/search/all")
    public @ResponseBody List<Game> getAllGames() {
        return service.getAll();
    }

    @PostMapping("/create")
    public @ResponseBody Game createNewGame(@RequestBody Map<String, String> playerUsernames) {
        return service.createNewGame(playerUsernames.get("player1"), playerUsernames.get("player2"));
    }

    @ResponseStatus(value = HttpStatus.CREATED, reason = "Test game was inserted into the database")
    @RequestMapping("/test-data")
    public void testData() {
        service.insertTestData();
    }
}
