
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class GameTest {

    private GameData gameData;

    @BeforeEach
    public void setup() {
        List<String> playerNames = List.of("Lucilla", "Bert", "Malina", "Percy");
        gameData = new GameData(playerNames);
    }

    @Test
    public void testGameDataSetup() {
        assertNotNull(gameData.currentPlayer());
        assertEquals(4, gameData.players().size());
        assertNotNull(gameData.solution());
        assertFalse(gameData.potentialSolutions().isEmpty());
    }

    @Test
    public void testPlayerMovement() {
        Player currentPlayer = gameData.currentPlayer();
        Pos initialPosition = currentPlayer.position();
        currentPlayer.move("Right");
        Pos newPosition = currentPlayer.position();

        assertNotEquals(initialPosition, newPosition);
    }

    @Test
    public void testPlayerPos() {
        Player currentPlayer = gameData.currentPlayer();
        Pos initialPosition = currentPlayer.position();
        currentPlayer.move("Right");
        currentPlayer.move("Up");
        Pos newPosition = currentPlayer.position();
        Pos newP =new Pos(9,2);
        assertEquals(newP, newPosition);
    }

    @Test
    public void testPlayerEnteringEstate() {
        Player currentPlayer = gameData.currentPlayer();
        currentPlayer.move("Left");
        currentPlayer.move("Left");
        assertTrue(currentPlayer.enteredEstate());
        assertNotNull(currentPlayer.estate());
    }

    @Test
    public void testPlayerExitingEstate() {
        Player currentPlayer = gameData.currentPlayer();
        currentPlayer.move("Right");
        currentPlayer.move("Right");
        currentPlayer.move("Left");
        assertFalse(currentPlayer.enteredEstate());
        assertNull(currentPlayer.estate());
    }

    @Test
    public void testPlayerHandAndCards() {
        Player currentPlayer = gameData.currentPlayer();
        assertFalse(currentPlayer.hand().isEmpty());
        Card cardToPlay = currentPlayer.hand().iterator().next();
        currentPlayer.playCard(cardToPlay);
        assertFalse(currentPlayer.hand().contains(cardToPlay));
    }

}

