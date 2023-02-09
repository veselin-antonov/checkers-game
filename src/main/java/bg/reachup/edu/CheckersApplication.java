package bg.reachup.edu;

import bg.reachup.edu.checkers.Game;
import bg.reachup.edu.checkers.state.Board;
import bg.reachup.edu.checkers.state.State;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CheckersApplication {

    public static void main(String[] args) {
        SpringApplication.run(CheckersApplication.class, args);

        Logger logger = LoggerFactory.getLogger(CheckersApplication.class);

        Board board = Board.parseFromFile("./src/main/resources/test.board");
        State state = new State(board, false);
        Game game = new Game(state);
        game.playGame();
//        state.getChildren().stream().map(State::toString).forEach(logger::info);

    }
}
