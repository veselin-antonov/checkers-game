package bg.reachup.edu.buisness.services;

import bg.reachup.edu.data.entities.Game;
import bg.reachup.edu.data.entities.Player;
import bg.reachup.edu.data.entities.State;
import bg.reachup.edu.data.repositories.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GameEndHandlerService {
    GameRepository gameRepository;
    StateService stateService;

    @Autowired
    public GameEndHandlerService(GameRepository gameRepository, StateService stateService) {
        this.gameRepository = gameRepository;
        this.stateService = stateService;
    }

    public void checkForGameEnd(Game game) {
        State gameState = game.getState();
        if (stateService.isFinal(gameState)) {

            Player player1 = game.getPlayer1();
            Player player2 = game.getPlayer2();
            int gameEvaluation = stateService.evaluate(gameState);

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

            gameState.setFinished(true);
            stateService.updateState(gameState.getId(), gameState);
        }
    }
}
