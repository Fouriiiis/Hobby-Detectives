import javax.swing.*;

/**
 * Represents the game instructions state.
 */
public class Instructions implements States {

    /**
     * Displays the instructions in a pop-up window.
     */
    @Override
    public void draw() {
        // Create a pop-up window with instructions
        JFrame frame = new JFrame();
        frame.setTitle("Instructions");
        frame.setSize(500, 400);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
        
        // Create a label with instructions
        JLabel instructions = new JLabel("<html>Instructions: <br> 1. 'Start Game' Button to start the game. <br> 2. Select the number of players <br> 3. Select Character for each player <br> 4. 'Create Game' button to create the game <br> 5. Use 'UP','DOWN','LEFT','RIGHT' button's or click on the adjacent tiles for movement. <br> <br> Shortcuts: <br> 1. Press '3' or '4' to select the number of players when on game setup <br> 2. Press 'Space bar' or 'R' to roll both dice immediately.</html>");
        frame.add(instructions);
    }

    /**
     * Main method to run the instructions display.
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        // Create a new instance of the Instructions class
        Instructions i = new Instructions();
        // Call the draw method to display instructions
        i.draw();
    }
}
