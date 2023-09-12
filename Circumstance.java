import java.util.*;

/**
 * Represents a combination of cards in the game.
 */
public class Circumstance {
    /** The list of cards in this circumstance. */
    public final List<Card> cards;

    /**
     * Constructs a circumstance from a list of cards.
     *
     * @param cards The list of cards to be included in this circumstance.
     */
    public Circumstance(List<Card> cards) {
        assert cards.size() == 3;
        for (int i = 0; i < 3; i++) {
            assert cards.get(i).type.equals(Cards.cardTypes[i]);
        }
        this.cards = cards;
    }

    /**
     * Checks if this circumstance is equal to another.
     *
     * @param obj The object to compare with.
     * @return True if the objects are equal, false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Circumstance other) {
            for (Card c : this.cards) {
                if (!other.cards.contains(c)) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Returns a string representation of this circumstance.
     *
     * @return A string containing the list of cards in this circumstance.
     */
    @Override
    public String toString() {
        return cards.toString();
    }
}
