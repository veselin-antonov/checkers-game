package bg.reachup.edu.data.entities;

import java.util.Comparator;

public enum SortingCategory {
    UNSORTED(Comparator.comparingLong(Player::getId)),
    WINS(Comparator.comparingInt(Player::getWins)),
    LOSSES(Comparator.comparingInt(Player::getLosses)),
    TIES(Comparator.comparingInt(Player::getTies)),
    TOTAL(Comparator.comparingInt(Player::getTotalGames));
    private Comparator<Player> comparator;

    SortingCategory(Comparator<Player> comparator) {
        this.comparator = comparator;
    }

    public Comparator<Player> comparator() {
        return comparator;
    }
}