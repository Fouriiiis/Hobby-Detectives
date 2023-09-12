/**
 * Represents a card in the game.
 */
public class Card {
    /** The type of the card. */
    public final String type;

    /** The name of the card. */
    public final String name;

    /**
     * Constructs a Card object with the provided type and name.
     *
     * @param t The type of the card.
     * @param n The name of the card.
     */
    public Card(String t, String n) {
        type = t;
        name = n;
    }

    /**
     * Compares whether this card is equal to another object.
     *
     * @param obj The object to compare with.
     * @return True if the object is a Card and has the same type and name, otherwise false.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Card other) {
            return this.type.equals(other.type) && this.name.equals(other.name);
        }
        return false;
    }

    /**
     * Returns a string representation of the card.
     *
     * @return A string in the format "(type : name)".
     */
    @Override
    public String toString() {
        return "(" + type + " : " + name + ")";
    }
}
