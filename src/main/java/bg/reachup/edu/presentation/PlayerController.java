package bg.reachup.edu.presentation;

import bg.reachup.edu.buisness.exceptions.PlayerIDNotFoundException;
import bg.reachup.edu.buisness.services.PlayerService;
import bg.reachup.edu.data.entities.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.annotation.HttpConstraint;
import java.util.List;

@Controller
@RequestMapping("/players")
public class PlayerController {
    @Autowired
    PlayerService service;
    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody List<Player> all() {
        return service.getAllPlayers();
    }

    @GetMapping("/search/{id}")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody Player searchPlayerByID(@PathVariable Long id) {
        return service.searchByID(id);
    }

    @GetMapping("/placeholder")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody String loadPlaceholderData() {
        return service.loadPlaceholderData() ? "Loaded placeholder data" : "Database already contains some data";
    }

    @PostMapping("/register")
    public @ResponseBody Player registerPlayer(@RequestBody Player player) {
        return service.registerPlayer(player);
    }
}
