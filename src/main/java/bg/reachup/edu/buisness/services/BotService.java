package bg.reachup.edu.buisness.services;

import bg.reachup.edu.data.entities.Game;
import bg.reachup.edu.data.entities.GameMode;
import bg.reachup.edu.data.entities.Player;
import bg.reachup.edu.data.entities.State;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class BotService {
    private final static Logger LOGGER = LoggerFactory.getLogger(BotService.class);
    private final StateService stateService;
    private final GameEndHandlerService gameEndHandlerService;

    @Autowired
    public BotService(StateService stateService, GameEndHandlerService gameEndHandlerService) {
        this.stateService = stateService;
        this.gameEndHandlerService = gameEndHandlerService;
    }

    @Async
    public void botMakeMove(Game game) {
        LOGGER.info("Bot woke up.");
        if (
                game.getMode() == GameMode.SINGLEPLAYER
                        && !game.getState().isPlayer1Turn()
                        && !game.getState().isFinished()
        ) {
            LOGGER.info("Bot started thinking...");

            State newGameState = stateService.findBestMove(game.getState(), game.getDifficulty().value());
            stateService.updateState(game.getState().getId(), newGameState);

            gameEndHandlerService.checkForGameEnd(game);

            LOGGER.info("Bot made a move! -> " + newGameState.getOriginAction());
        }
        LOGGER.info("Bot fell asleep.");
    }

}
