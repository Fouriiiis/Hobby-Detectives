/**
 * Represents a 2D position with x and y coordinates.
 */
public class Pos {
    /**
     * The x-coordinate of the position.
     */
    int x;

    /**
     * The y-coordinate of the position.
     */
    int y;

    /**
     * Constructs a new Pos object with the given x and y coordinates.
     *
     * @param x The x-coordinate of the position.
     * @param y The y-coordinate of the position.
     */
    public Pos(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Checks if this position is equal to another object.
     *
     * @param obj The object to compare with.
     * @return true if the objects are equal (have the same coordinates), false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Pos other) {
            return this.x == other.x && this.y == other.y;
        }
        return false;
    }

    /**
     * Returns a string representation of the position in the format (x,y).
     *
     * @return A string representing the position.
     */
    @Override
    public String toString() {
        return "(" + x + "," + y + ")";
    }
}
