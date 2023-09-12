import java.awt.Rectangle;

import javax.swing.*;

/**
 * Represents the main menu state of the game.
 */
public class S_Menu implements States {

    /**
     * Draws the main menu interface with title and buttons.
     */
    @Override
    public void draw() {
        // Get the size of the window
        Rectangle bounds = StateManager.display.getBounds();
        // Clear the display
        StateManager.display.removeAll();
        // Set panel layout
        StateManager.display.setLayout(null);

        ////////////////////////////// 
        /* Create label for the title */
        ////////////////////////////// 
        JLabel title = new JLabel("Hobby Detectives");
        // Set the alignment of the title
        title.setHorizontalAlignment(JLabel.CENTER);
        // Make the title bigger
        title.setFont(title.getFont().deriveFont(90.0f));
        // Set the bounds of the title
        title.setBounds(bounds.width/2 - 400, bounds.height/2 - 200, 800, 150);

        ///////////////////////////////////
        /* Create button to start the game */
        ///////////////////////////////////
        JButton startB = StateManager.createTransitionButton("Start Game", new S_Setup());
        // Set the bounds of the button
        startB.setBounds(bounds.width/2 - 150, bounds.height/2 - 25, 300, 75);

        //////////////////////////////////
        /* Create button to exit the game */
        //////////////////////////////////
        JButton exitB = StateManager.createExitButton();
        // Set the bounds of the button
        exitB.setBounds(bounds.width/2 - 100, bounds.height/2 + 75, 200, 50);

        // Add the title and buttons to the display
        StateManager.display.add(title);
        StateManager.display.add(startB);
        StateManager.display.add(exitB);
    }
}
