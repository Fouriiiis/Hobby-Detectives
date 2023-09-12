import java.util.*;

/**
 * Represents an estate on the board.
 */
public class Estate {
    /**
     * The name of the estate.
     */
    public final String name;

    /**
     * The position of the estate.
     */
    public final Pos pos;

    /**
     * The dimensions of the estate.
     */
    public final Pos size;

    /**
     * A mapping of keys to corresponding door positions within the estate.
     */
    public final Map<String, Pos> keyToDoor;

    /**
     * A list of guessed positions within the estate.
     */
    public final List<Pos> guessPoses;

    /**
     * The set of weapons present within the estate.
     */
    private Set<Weapon> weapons = new HashSet<>();

    /**
     * Constructs an Estate object with the provided parameters.
     *
     * @param n     The name of the estate.
     * @param p     The position of the estate.
     * @param s     The dimensions (size) of the estate.
     * @param ktd   A mapping of keys to corresponding door positions within the estate.
     * @param gPos  A list of guessed positions within the estate.
     */
    public Estate(String n, Pos p, Pos s, Map<String, Pos> ktd, List<Pos> gPos) {
        name = n;
        pos = p;
        size = s;
        keyToDoor = ktd;
        guessPoses = gPos;
    }

    /**
     * Returns a set of weapons present within the estate.
     *
     * @return An unmodifiable set of weapons.
     */
    public Set<Weapon> weapons() {
        return Collections.unmodifiableSet(weapons);
    }

    /**
     * Adds a weapon to the estate's collection of weapons.
     *
     * @param w The weapon to be added.
     */
    public void addWeapon(Weapon w) {
        weapons.add(w);
        w.setEstate(this);
    }

    /**
     * Removes a weapon from the estate's collection of weapons.
     *
     * @param w The weapon to be removed.
     */
    public void removeWeapon(Weapon w) {
        weapons.remove(w);
        w.setEstate(null);
    }

    /**
     * Returns a string representation of the estate including its name and the list of weapons.
     *
     * @return A string representation of the estate.
     */
    @Override
    public String toString() {
        return "Name: " + name + ". Weapons:" + weapons;
    }
}
