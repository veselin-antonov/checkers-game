package bg.reachup.edu.buisness.services;

import bg.reachup.edu.data.entities.Action;
import bg.reachup.edu.data.entities.Game;

import java.util.List;

public interface GameService {

    List<Game> getAll();

    Game getByID(Long id);

    Game createNewGame(Game game);

    Game joinGame(Long id, String playerUsername);

    void deleteByID(Long gameID);

    Game makeMove(Game game, Action action);
}
