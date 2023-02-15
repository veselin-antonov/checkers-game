package bg.reachup.edu.presentation.controllers;

import bg.reachup.edu.buisness.services.PlayerService;
import bg.reachup.edu.data.entities.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/players")
public class PlayerController {
    @Autowired
    PlayerService service;
    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody List<Player> all() {
        return service.getAllPlayers();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody Player searchPlayerByID(@PathVariable Long id) {
        return service.searchByID(id);
    }

    @PostMapping("")
    @ResponseStatus(value = HttpStatus.CREATED, reason = "Successfully registered new player")
    public @ResponseBody Player registerPlayer(@RequestBody Player player) {
        return service.registerPlayer(player);
    }

    @RequestMapping("/test-data")
    @ResponseStatus(value = HttpStatus.CREATED, reason = "Loaded test data")
    public void loadTestData() {
        service.loadPlaceholderData();
    }
}
