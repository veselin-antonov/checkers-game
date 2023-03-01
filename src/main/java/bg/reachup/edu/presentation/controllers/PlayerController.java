package bg.reachup.edu.presentation.controllers;

import bg.reachup.edu.buisness.services.PlayerService;
import bg.reachup.edu.presentation.dtos.PlayerDTO;
import bg.reachup.edu.presentation.mappers.PlayerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Validated
@RestController
@RequestMapping("/players")
public class PlayerController {
    private final PlayerService service;
    private final PlayerMapper mapper;

    @Autowired
    public PlayerController(PlayerService service, PlayerMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody List<PlayerDTO> all() {
        return mapper.toDTOs(service.getAllPlayers());
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody PlayerDTO searchPlayerByID(@PathVariable Long id) {
        return mapper.toDTO(service.searchByID(id));
    }

    @PostMapping("")
    @ResponseStatus(value = HttpStatus.CREATED, reason = "Successfully registered new player")
    public @ResponseBody PlayerDTO registerPlayer(@Valid @RequestBody PlayerDTO playerDTO) {
        return mapper.toDTO(service.registerPlayer(mapper.toEntity(playerDTO)));
    }
}
