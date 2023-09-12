import java.awt.Rectangle;
import javax.swing.*;

/**
 * Represents the end game state, displayed when the game is won or lost.
 */
public class S_EndGame implements States {
    GameData gameData;
    boolean win;

    /**
     * Constructs an instance of the S_EndGame class.
     *
     * @param gameData The game data.
     * @param win      A boolean indicating whether the game was won.
     */
    S_EndGame(GameData gameData, boolean win) {
        this.gameData = gameData;
        this.win = win;
    }

    /**
     * Draws the end game state, displaying appropriate messages and buttons.
     */
    @Override
    public void draw() {
        // Get the size of the window
        Rectangle bounds = StateManager.display.getBounds();
        // Clear the display
        StateManager.display.removeAll();
        // Set panel layout
        StateManager.display.setLayout(null);

        // Title
        String str;
        if (win) {
            // Display when the game is won
            Player winner = gameData.currentPlayer();
            str = "You Win, " + winner.name + " solved the murder";
        } else {
            // Display when the game is lost
            str = "You Lose, no player is able to make a solve";
        }
        JLabel title = new JLabel(str);
        // Set location of the title
        title.setBounds(bounds.width / 2 - 400, bounds.height / 2 - 200, 800, 150);
        // Set size of the title
        title.setFont(title.getFont().deriveFont(40.0f));

        // Create a new game button
        JButton newGameB = StateManager.createTransitionButton("New Game", new S_Setup());
        // Set the bounds of the button
        newGameB.setBounds(bounds.width / 2 - 150, bounds.height / 2 - 25, 300, 75);

        // Exit button
        JButton exitB = StateManager.createExitButton();
        // Set the bounds of the button
        exitB.setBounds(bounds.width / 2 - 100, bounds.height / 2 + 75, 200, 50);

        StateManager.display.add(title);
        StateManager.display.add(newGameB);
        StateManager.display.add(exitB);
    }
}
