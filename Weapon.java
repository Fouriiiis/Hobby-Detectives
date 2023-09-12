/**
 * Represents a weapon in the game.
 */
public class Weapon {
    /** The name of the weapon. */
    public final String name;

    /** The estate where the weapon is located. */
    private Estate estate;

    /**
     * Constructs a Weapon object with the provided name and associated estate.
     *
     * @param n The name of the weapon.
     * @param e The estate where the weapon is located.
     */
    public Weapon(String n, Estate e) {
        name = n;
        estate = e;
    }

    /**
     * Sets the estate where the weapon is located.
     *
     * @param e The new estate where the weapon is located.
     */
    public void setEstate(Estate e) {
        estate = e;
    }

    /**
     * Gets the estate where the weapon is located.
     *
     * @return The estate where the weapon is located.
     */
    public Estate estate() {
        return estate;
    }

    /**
     * Returns the name of the weapon.
     *
     * @return The name of the weapon.
     */
    @Override
    public String toString() {
        return name;
    }
}
