package bg.reachup.edu.presentation.controllers;

import bg.reachup.edu.buisness.services.PlayerService;
import bg.reachup.edu.presentation.dtos.PlayerDTO;
import bg.reachup.edu.presentation.mappers.PlayerMapper;
import bg.reachup.edu.presentation.validation.GameID;
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
    @ResponseBody
    public List<PlayerDTO> getAll(

            @RequestParam(value = "sortedBy", required = false, defaultValue = "unsorted")
            String sortedBy,

            @RequestParam(value = "direction", required = false, defaultValue = "descending")
            String direction
    ) {
        return mapper.toDTOs(service.getAllPlayers(sortedBy, direction));
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody PlayerDTO getByID(
            @PathVariable
            @GameID
            String id
    ) {
        return mapper.toDTO(service.searchByID(Long.parseLong(id)));
    }

    @PostMapping("")
    @ResponseStatus(value = HttpStatus.CREATED, reason = "Successfully registered new player")
    public @ResponseBody PlayerDTO register(
            @Valid
            @RequestBody
            PlayerDTO playerDTO
    ) {
        return mapper.toDTO(service.registerPlayer(mapper.toEntity(playerDTO)));
    }
}
