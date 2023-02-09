package bg.reachup.edu.checkers.state.actions;

public enum Direction {
    UP_LEFT, UP_RIGHT, DOWN_LEFT, DOWN_RIGHT;

    @Override
    public String toString() {
        return switch (this) {
            case UP_LEFT -> "Upper left";
            case UP_RIGHT -> "Upper right";
            case DOWN_LEFT -> "Lower left";
            case DOWN_RIGHT -> "Lower right";
        };
    }
}
