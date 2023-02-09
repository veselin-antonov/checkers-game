package bg.reachup.edu.checkers;

import bg.reachup.edu.checkers.state.State;
import bg.reachup.edu.utils.Pair;

import java.util.List;

public class Game {
    private State startingState;
    private int gameChecks;

    public Game(State startingState) {
        this.startingState = startingState;
        gameChecks = 0;
    }

    private Pair<State, Integer> minMaxAB(State state, int depth, int alpha, int beta) {
        if (state.isFinal()) {
            return new Pair<>(state, state.evaluate());
        }
        if (depth == 0) {
            return new Pair<>(state, state.evaluate());
        }
        List<State> children = state.getChildren();
        Pair<State, Integer> bestChoice = null;
        for (State child : children) {
            Pair<State, Integer> possibleBestChoice = new Pair<>(
                    child,
                    minMaxAB(child, depth - 1, alpha, beta).value2()
            );
            bestChoice = betterChoice(bestChoice, possibleBestChoice, state.isWhitesTurn()) ?
                    bestChoice : possibleBestChoice;
            if (state.isWhitesTurn()) {
                alpha = Math.max(alpha, bestChoice.value2());
            } else {
                beta = Math.min(beta, bestChoice.value2());
            }
            if (alpha >= beta) {
                break;
            }
        }
        return bestChoice;
    }

    public Pair<State, Integer> findBestMove(State state, int depth) {
        return minMaxAB(state, depth, state.minStateScore, state.maxStateScore);
    }

    private boolean betterChoice(Pair<State, Integer> choice1, Pair<State, Integer> choice2, boolean isMaximisingPlayersTurn) {
        if (choice1 == null) {
            return false;
        } else if (isMaximisingPlayersTurn) {
            return choice1.value2() > choice2.value2();
        } else {
            return choice1.value2() < choice2.value2();
        }
    }

    public void playGame() {
        State currentState = startingState;
        Pair<State, Integer> bestMove;
        do {
            int depth = currentState.isWhitesTurn() ? 2 : 3;
            bestMove = findBestMove(currentState, depth);
            currentState = bestMove == null ? currentState : bestMove.value1();
            System.out.printf("%s. State score: %d%n", currentState.getOriginAction(), currentState.evaluate());
            System.out.println(currentState);
            System.out.println(gameChecks);
        } while (!currentState.isFinal());
        System.out.println(gameChecks);
    }
}
