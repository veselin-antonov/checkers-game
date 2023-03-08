package bg.reachup.edu.buisness.services;

import bg.reachup.edu.data.entities.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class BotServiceImpl implements BotService{
    private final static Logger LOGGER = LoggerFactory.getLogger(BotServiceImpl.class);
    private final StateService stateService;
    private GameService gameService;

    @Autowired
    public BotServiceImpl(StateService stateService, @Lazy GameService gameService) {
        this.stateService = stateService;
        this.gameService = gameService;
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

            Action bestMove = stateService.findBestMove(game.getState(), game.getDifficulty().value()).getOriginAction();
            Action botMove = new Action(
                    bestMove.actionType(),
                    bestMove.direction(),
                    bestMove.piecePosition(),
                    game.getPlayer2().getUsername()
            );
            gameService.makeMove(game, botMove);

            LOGGER.info("Bot found a move! -> " + bestMove);
        }
        LOGGER.info("Bot fell asleep.");
    }

}
