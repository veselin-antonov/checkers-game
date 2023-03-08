package bg.reachup.edu.buisness.services;

import bg.reachup.edu.data.entities.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class BotService {
    private final static Logger LOGGER = LoggerFactory.getLogger(BotService.class);
    private final StateService stateService;
    private final GameService gameService;

    @Autowired
    public BotService(StateService stateService, GameService gameService) {
        this.stateService = stateService;
        this.gameService = gameService;
    }

//    @Async
//    public void botMakeMove(Game game) {
//        LOGGER.info("Bot woke up.");
//        if (
//                game.getMode() == GameMode.SINGLEPLAYER
//                        && !game.getState().isPlayer1Turn()
//                        && !game.getState().isFinished()
//        ) {
//            LOGGER.info("Bot started thinking...");
//
//            State newGameState = stateService.findBestMove(game.getState(), game.getDifficulty().value());
//            stateService.updateState(game.getState().getId(), newGameState);
//
//            gameEndHandlerService.checkForGameEnd(game);
//
//            LOGGER.info("Bot made a move! -> " + newGameState.getOriginAction());
//        }
//        LOGGER.info("Bot fell asleep.");
//    }

    @Async
    public void botMakeMove(Game game) {
        if (
                game.getMode() == GameMode.SINGLEPLAYER
                        && !game.getState().isPlayer1Turn()
                        && !game.getState().isFinished()
        ) {
            LOGGER.info("Bot started thinking...");

            Action bestMove = stateService.findBestMove(game.getState(), game.getDifficulty().value()).getOriginAction();
            gameService.makeMove(game, bestMove);

            LOGGER.info("Bot found a move! -> " + bestMove);
        }
        LOGGER.info("Bot fell asleep.");
    }

}
