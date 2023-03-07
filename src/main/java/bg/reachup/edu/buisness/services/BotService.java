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

    @Autowired
    public BotService(StateService stateService) {
        this.stateService = stateService;
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
            if (stateService.isFinal(newGameState)) {
                Player player1 = game.getPlayer1();
                Player player2 = game.getPlayer2();
                int gameEvaluation = stateService.evaluate(newGameState);

                if (gameEvaluation > 0) {
                    player1.giveWin();
                    player2.giveLoss();
                } else if (gameEvaluation < 0) {
                    player1.giveLoss();
                    player2.giveWin();
                } else {
                    player1.giveTie();
                    player2.giveTie();
                }

                newGameState.setFinished(true);
            }
            stateService.updateState(game.getState().getId(), newGameState);
            LOGGER.info("Bot made a move! -> " + newGameState.getOriginAction());
        }
        LOGGER.info("Bot fell asleep.");
    }

}
