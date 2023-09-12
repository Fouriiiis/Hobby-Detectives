import java.util.ArrayList;
import java.util.List;

/**
 * Represents a collection of card types used in the game.
 */
public class Cards {
    /** The types of cards that can be present. */
    public static final String[] cardTypes = new String[]{"estate", "character", "weapon"};

    /** A list containing all estate cards. */
    public static final List<Card> allEstateCards = List.of(
            new Card("estate", "Haunted House"),
            new Card("estate", "Calamity Castle"),
            new Card("estate", "Maniac Manor"),
            new Card("estate", "Peril Palace"),
            new Card("estate", "Visitation Villa"));

    /** A list containing all weapon cards. */
    public static final List<Card> allWeaponCards = List.of(
            new Card("weapon", "Broom"),
            new Card("weapon", "Scissors"),
            new Card("weapon", "Knife"),
            new Card("weapon", "Shovel"),
            new Card("weapon", "iPad"));

    /** A list containing all player cards. */
    public static final List<Card> allPlayerCards = new ArrayList<>(List.of(
            new Card("character", "Lucilla"),
            new Card("character", "Bert"),
            new Card("character", "Percy"),
            new Card("character", "Malina")));
}
