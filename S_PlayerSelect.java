import javax.swing.*;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

public class S_PlayerSelect implements States{
    int numOfPlayers;
    List<JRadioButton> options = new ArrayList<>();
    final List<String> players = List.of("Lucilla", "Bert", "Malina", "Percy");
    List<String> currentPlayerOrder = new ArrayList<>();

    int count = 0;

    S_PlayerSelect(int num, List<String> currentPlayerOrder) {
        this.numOfPlayers = num;
        this.currentPlayerOrder = currentPlayerOrder;
    }
    S_PlayerSelect(int num) {
        this.numOfPlayers = num;
    }
    @Override
    public void draw() {
        // get the size of the window
        Rectangle bounds = StateManager.display.getBounds();
        // clear the display
        StateManager.display.removeAll();
        // set panel layout
        StateManager.display.setLayout(null);
        
        // Create a label for which player is selecting
        JLabel playerSelect = new JLabel("Player "+(currentPlayerOrder.size()+1)+":");
        // Set size of the question
        playerSelect.setFont(playerSelect.getFont().deriveFont(70.0f));
        // Set location of the question
        playerSelect.setBounds(bounds.width/2 - 150, bounds.height/2 - 350, 800, 150);

        // Create a label for hint
        JLabel hint = new JLabel("Press 'Confirm' to confirm your choice.");
        // Set location of hint
        hint.setBounds(bounds.width/2 - 125, bounds.height/2 - 250, 800, 150);

        // option buttons
        createOptionRadioButtons();
        // confirm button
        JButton confirmB = createConfirmButton();
        // set location of button
        confirmB.setBounds(bounds.width/2 - 150, bounds.height/2 + 200, 300, 75);

        StateManager.display.add(playerSelect);
        StateManager.display.add(hint);
        StateManager.display.add(confirmB);
    }
    /** create the option buttons and add them to the display */
    private void createOptionRadioButtons(){
        // get the size of the window
        Rectangle bounds = StateManager.display.getBounds();

        // option radio buttons
        ButtonGroup bGroup = new ButtonGroup();
        // set location of the buttons
        int x = bounds.width/2 - 150;
        int y = bounds.height/2 - 150;
        for (JRadioButton b : options) {
            b.setBounds(x, y, 300, 75);
            y += 50;
        }
        // create the radio buttons and add them to 'buttonGroup' & display
        for (String name : players) {
            // the options to chose from are the players that have not been picked yet
            // (are not in the current order)
            if(!currentPlayerOrder.contains(name)) {
                JRadioButton b = new JRadioButton(name);
                // set location of the button
                b.setBounds(x, y, 300, 75);
                y += 50;
                // add the button to the display
                StateManager.display.add(b);
                bGroup.add(b);
                options.add(b);
            }
        }
    }

    /**
     * create and return the confirm button
     * which confirms the player choice and adds the player
     * to the current player order
     */
    private JButton createConfirmButton(){
        // confirm button
        JButton confirmB = new JButton("Confirm");
        confirmB.addActionListener(e -> {
            for (JRadioButton b : options) {
                if (b.isSelected()) {
                    // add the player to the current order
                    currentPlayerOrder.add(b.getText());
                    // if all the players for the game have been selected
                    if (currentPlayerOrder.size() == numOfPlayers) {
                        StateManager.switchTo(new S_Setup(currentPlayerOrder, numOfPlayers));
                    } else {
                        // players still need to be chosen
                        StateManager.switchTo(new S_PlayerSelect(numOfPlayers, currentPlayerOrder));
                    }
                }
            }
        });
        return confirmB;
    }

}
