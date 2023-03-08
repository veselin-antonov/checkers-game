package bg.reachup.edu.data.entities;

public enum Difficulty {
    EASY(2, "Easy_Bot"),
    NORMAL(4, "Normal_Bot"),
    HARD(7, "Hard_Bot");

    private final int value;
    private String botName;

    Difficulty(int value, String botName) {
        this.value = value;
        this.botName = botName;
    }

    public int value() {
        return value;
    }

    public String botName() {
        return botName;
    }
}
