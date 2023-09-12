import javax.swing.*;

/**
 * Manages the different states of the application and handles state transitions.
 */
public class StateManager {

    /** The current state of the application. */
    public static States currentState; // Use the States interface type

    /** The panel used to display the current state's content. */
    public static JPanel display = new JPanel();

    /**
     * Constructs a new StateManager and sets the initial state to S_Menu.
     */
    public StateManager() {
        currentState = new S_Menu();
    }

    /**
     * Switches the state of the application to the provided new state.
     * Clears the display, invokes the new state's draw method, and updates the display.
     *
     * @param newState The new state to switch to.
     */
    public static void switchTo(States newState) {
        currentState = newState;

        // Clear the display
        display.removeAll();

        // Call the draw method of the new state
        currentState.draw();

        display.revalidate();
        display.repaint();
        display.setVisible(true);
    }

    /**
     * Sets up and draws the main application frame with the current state's content.
     */
    public void draw() {
        // Create a frame
        JFrame frame = new JFrame();

        // Set frame title
        frame.setTitle("Hobby Detectives");
        frame.setSize(1000, 800);

        // Set up the menu bar
        JMenuBar menuBar = createMenuBar();
        frame.setJMenuBar(menuBar);

        // Configure frame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(display);
        frame.setVisible(true);

        // Call the draw method of the current state
        currentState.draw();
    }

    /**
     * Creates and returns a button that transitions to a new state when clicked.
     *
     * @param title The title of the button.
     * @param newState The state to transition to.
     * @return A JButton that triggers the state transition.
     */
    public static JButton createTransitionButton(String title, States newState) {
        JButton b = new JButton(title);
        b.addActionListener(l -> StateManager.switchTo(newState));
        return b;
    }

    /**
     * Creates and returns a button that exits the program when clicked.
     *
     * @return A JButton that exits the program.
     */
    public static JButton createExitButton() {
        JButton b = new JButton("Exit");
        b.addActionListener(l -> System.exit(0));
        return b;
    }

    /**
     * Creates and returns the menu bar for the main frame.
     *
     * @return The JMenuBar containing menu items.
     */
    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        // File menu
        JMenu fileMenu = new JMenu("Menu");
        menuBar.add(fileMenu);

        // New game menu item
        JMenuItem newGameMenuItem = new JMenuItem("New Game");
        newGameMenuItem.addActionListener(e -> switchTo(new S_Menu()));
        fileMenu.add(newGameMenuItem);

        // Instructions menu item
        JMenuItem instructionsMenuItem = new JMenuItem("Instructions");
        instructionsMenuItem.addActionListener(e -> Instructions.main(null));
        fileMenu.add(instructionsMenuItem);

        // Exit menu item
        JMenuItem exitMenuItem = new JMenuItem("Exit");
        exitMenuItem.addActionListener(e -> System.exit(0));
        fileMenu.add(exitMenuItem);

        return menuBar;
    }
}
