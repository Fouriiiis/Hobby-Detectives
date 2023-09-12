import java.awt.Rectangle;

import javax.swing.*;

public class S_NumSelect implements States{
    @Override
    public void draw() {
        // get the size of the window
        Rectangle bounds = StateManager.display.getBounds();
        // clear the display
        StateManager.display.removeAll();
        // set panel layout
        StateManager.display.setLayout(null);
        
        // Create a label for the question
        JLabel question = new JLabel("How many Players?");
        // Set size of the question
        question.setFont(question.getFont().deriveFont(70.0f));
        // Set location of the question
        question.setBounds(bounds.width/2 - 325, bounds.height/2 - 350, 800, 150);

        // send to player selection state to pick 3 players (in turn order)
        JButton b3 = StateManager.createTransitionButton("3 Players", new S_PlayerSelect(3));
        // set location of button
        b3.setBounds(bounds.width/2 - 350, bounds.height/2 - 150, 300, 75);

        // send to player selection state to pick 4 players (in turn order)
        JButton b4 = StateManager.createTransitionButton("4 Players", new S_PlayerSelect(4));
        // set location of button
        b4.setBounds(bounds.width/2 + 50, bounds.height/2 - 150, 300, 75);

        // Create a back button
        JButton back = StateManager.createTransitionButton("Back", new S_Setup());
        // set location of button
        back.setBounds(bounds.width/2 - 100, bounds.height/2 + 200, 200, 50);

        StateManager.display.add(question);
        StateManager.display.add(b3);
        StateManager.display.add(b4);
        StateManager.display.add(back);
    }
}
