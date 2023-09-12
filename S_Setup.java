import javax.swing.*;

import java.awt.Rectangle;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyListener;
import java.util.List;

public class S_Setup implements States {
    int numOfPlayers = 0;
    List<String> playerOrder = List.of();
    //"Lucilla", "Bert", "Malina", "Percy"

    int count = 0;
    S_Setup(List<String> playerOrder, int numOfPlayers){
        this.playerOrder = playerOrder;
        this.numOfPlayers = numOfPlayers;
    }
    S_Setup(){}
    @Override
    public void draw() {
        // get the size of the window
        Rectangle bounds = StateManager.display.getBounds();
        // clear the display
        StateManager.display.removeAll();
        // set panel layout
        StateManager.display.setLayout(null);

        // title
        JLabel title = new JLabel("Game Setup");
        // set size of title
        title.setFont(title.getFont().deriveFont(90.0f));
        // set location of title
        title.setBounds(bounds.width/2 - 250, bounds.height/2 - 350, 800, 150);

        // create game button
        JButton createGameB = new JButton("Create Game");
        // set location of button
        createGameB.setBounds(bounds.width/2 - 150, bounds.height/2 - 200, 300, 75);

        createGameB.addActionListener(l -> {
            //if number of players is 0, print label saying "please select number of players"
            if(numOfPlayers == 0){
                //only add the label if it doesn't already exist
                JLabel error = new JLabel("Please select number of players");
                // set location of error label
                error.setBounds(bounds.width/2 - 100, bounds.height/2 - 50, 300, 75);
                if(count == 0){
                    StateManager.display.add(error);
                    count++;
                }
                StateManager.display.revalidate();
                StateManager.display.repaint();
                StateManager.display.setVisible(true);
                return;
            }
            else{
                GameData gameData = new GameData(playerOrder);
                StateManager.switchTo(new S_Roll(gameData));
            }
        });

        // Select number of players button
        JButton numOfPlayersB = StateManager.createTransitionButton("Number of Players", new S_NumSelect());
        // set location of button
        numOfPlayersB.setBounds(bounds.width/2 - 150, bounds.height/2 - 100, 300, 75);

        // number of players label
        JLabel num = new JLabel("number of players: "+numOfPlayers);
        // set location of label
        num.setBounds(bounds.width/2 - 60, bounds.height/2, 300, 75);

        // Player order label
        JLabel orderTxt = new JLabel("Order:");
        JLabel order = new JLabel(playerOrder.subList(0, numOfPlayers).toString()); 
        // set location of labels
        orderTxt.setBounds(bounds.width/2 - 20, bounds.height/2 + 50, 300, 75);
        order.setBounds(bounds.width/2 - 75, bounds.height/2 + 100, 300, 75);

        //Create back button
        JButton backB = StateManager.createTransitionButton("Back", new S_Menu());
        // set location of button
        backB.setBounds(bounds.width/2 - 100, bounds.height/2 + 200, 200, 50);

        // Create a key listener
        KeyListener kl = new KeyAdapter() {
            @Override
            public void keyPressed(java.awt.event.KeyEvent e) {
                // if the key 3 is pressed, set the number of players to 3
                if(e.getKeyCode() == java.awt.event.KeyEvent.VK_3){
                    numOfPlayers = 3;
                    num.setText("number of players: "+numOfPlayers);
                    playerOrder = List.of("Lucilla", "Bert", "Malina");
                    order.setText(playerOrder.subList(0, numOfPlayers).toString());
                    StateManager.display.revalidate();
                    StateManager.display.repaint();
                    StateManager.display.setVisible(true);
                }
                // if the key 4 is pressed, set the number of players to 4
                else if(e.getKeyCode() == java.awt.event.KeyEvent.VK_4){
                    numOfPlayers = 4;
                    num.setText("number of players: "+numOfPlayers);
                    playerOrder = List.of("Lucilla", "Bert", "Malina", "Percy");
                    order.setText(playerOrder.subList(0, numOfPlayers).toString());
                    StateManager.display.revalidate();
                    StateManager.display.repaint();
                    StateManager.display.setVisible(true);
                }
            }
        };
        // set focusable to true so that the key listener works
        StateManager.display.setFocusable(true);
        // request focus so that the key listener works
        StateManager.display.requestFocusInWindow();

        StateManager.display.add(title);
        StateManager.display.addKeyListener(kl);
        StateManager.display.add(createGameB);
        StateManager.display.add(numOfPlayersB);
        StateManager.display.add(num);
        StateManager.display.add(orderTxt);
        StateManager.display.add(order);
        StateManager.display.add(backB);

        /*// information labels/printouts
        JLabel orderTxt = new JLabel("Order:");
        StateManager.display.add(orderTxt);
        JLabel order = new JLabel(playerOrder.subList(0, numOfPlayers).toString());
        StateManager.display.add(order);
        JLabel num = new JLabel("number of players: "+numOfPlayers);
        StateManager.display.add(num);
        // create game button
        JButton createGameB = new JButton("Create Game");
        createGameB.addActionListener(l -> {
            GameData gameData = new GameData(playerOrder);
            StateManager.switchTo(new S_Roll(gameData));
        });
        StateManager.display.add(createGameB);
        // select number of players button (which leads to the player selection state)
        JButton numOfPlayers = StateManager.createTransitionButton("Change", new S_NumSelect());
        StateManager.display.add(numOfPlayers);*/
    }
}