package bg.reachup.edu.buisness.services;

import bg.reachup.edu.data.entities.Game;
import bg.reachup.edu.data.entities.GameMode;
import bg.reachup.edu.data.entities.State;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class BotService {
    StateService stateService;

    @Autowired
    public BotService(StateService stateService) {
        this.stateService = stateService;
    }

    @Async
    public void botMakeMove(Game game) throws InterruptedException {
        if (
                game.getMode() == GameMode.SINGLEPLAYER
                        && !game.getState().isPlayer1Turn()
                        && !game.getState().isFinished()
        ) {
            State newGameState = stateService.findBestMove(game.getState(), game.getDifficulty().value());
            stateService.updateState(game.getState(), newGameState);
        }
    }

}
