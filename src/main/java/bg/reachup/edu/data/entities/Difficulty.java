package bg.reachup.edu.data.entities;

public enum Difficulty {
    EASY(2), NORMAL(4), HARD(8);

    private final int value;

    Difficulty(int value) {
        this.value = value;
    }

    public int value() {
        return value;
    }
}
