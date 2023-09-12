import javax.swing.*;

import java.awt.FlowLayout;
import java.awt.Rectangle;
// import mouse listener
import java.awt.event.MouseAdapter;
import java.awt.event.MouseListener;
// import key listener
import java.awt.event.KeyAdapter;
import java.awt.event.KeyListener;

public class S_Roll implements States {
    private final String playerName;
    private int dice1 = 0;
    private int dice2 = 0;
    GameData gameData;
    private int count = 0;
    private int total = 0;
    JLabel hint, dice1Txt, dice2Txt;

    S_Roll(GameData gameData){
        this.gameData = gameData;
        this.playerName = gameData.currentPlayer().name;
    }

    @Override
    public void draw() {
        count = 0;
         // get the size of the window
         Rectangle bounds = StateManager.display.getBounds();
         // clear the display
         StateManager.display.removeAll();
         // set panel layout
        StateManager.display.setLayout(null);
         
        // Label who the current player's turn is
        JLabel playerTurn = new JLabel(playerName+"'s turn \n");
        // Makes label bigger
        playerTurn.setFont(playerTurn.getFont().deriveFont(90.0f));
        // Set location of the player turn label
        playerTurn.setBounds(bounds.width/2 - 300, bounds.height/2 - 300, 800, 150);

        // Label hint
        hint = new JLabel("Click anywhere to roll the dice 1");
        // Set location of the hint label
        hint.setBounds(bounds.width/2 - 100, bounds.height/2 - 225, 800, 150);

        // Label Dice1
        dice1Txt = new JLabel("Dice 1: "+dice1);
        // set size of the dice1 label
        dice1Txt.setFont(dice1Txt.getFont().deriveFont(50.0f));
        // Set location of the dice1 label
        dice1Txt.setBounds(bounds.width/2 - 300, bounds.height/2 - 150, 800, 150);

        // Label Dice2
        dice2Txt = new JLabel("Dice 2: "+dice2);
        // set size of the dice2 label
        dice2Txt.setFont(dice2Txt.getFont().deriveFont(50.0f));
        // Set location of the dice2 label
        dice2Txt.setBounds(bounds.width/2 + 50, bounds.height/2 - 150, 800, 150);

        MouseListener ml = new MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if(count == 0){
                    rollDice1();
                    count++;
                }
                else if(count == 1){
                    rollDice2();
                    count++;
                }
                else if(count == 2){
                    // set layout back to default
                    StateManager.display.setLayout(new FlowLayout());
                    // switch to play turn state
                    StateManager.switchTo(new S_Turn(gameData));
                    //set count back to 0
                    count = 0;
                }
                else{
                    count = 0;
                }
            }
        };

        // Create a key listener
        KeyListener kl = new KeyAdapter() {
            @Override
            public void keyPressed(java.awt.event.KeyEvent e) {
                // if the key pressed is the space bar or R, roll the dice
                if(e.getKeyCode() == java.awt.event.KeyEvent.VK_SPACE || e.getKeyCode() == java.awt.event.KeyEvent.VK_R){
                    // if count is 0, roll both dice
                    if(count == 0){
                        rollDice1();
                        rollDice2();
                        count = 2;
                    }
                    // if count is 1, roll dice2
                    else if(count == 1){
                        rollDice2();
                        count = 2;
                    }
                }
            }
        };
        // set focusable to true so that the key listener works
        StateManager.display.setFocusable(true);
        // request focus so that the key listener works
        StateManager.display.requestFocusInWindow();

        StateManager.display.addKeyListener(kl);
        StateManager.display.add(playerTurn);
        StateManager.display.add(hint);
        StateManager.display.addMouseListener(ml);
        StateManager.display.add(dice1Txt);
        StateManager.display.add(dice2Txt);
    }
    private void rollDice1(){
        // roll dice1
        dice1 = GameData.generateNum(1, 6);
        // update total
        total += dice1;
        // update hint
        hint.setText("Click anywhere to roll the dice 2    ");
        // update dice1
        dice1Txt.setText("Dice 1: "+dice1);
    }
    private void rollDice2(){
        // roll dice2
        dice2 = GameData.generateNum(1, 6);
        // update total
        total += dice2;
        // update game data
        gameData.setRoll(total);
        // update hint
        hint.setText("Click anywhere to continue    Total: "+total);
        // update dice2
        dice2Txt.setText("Dice 2: "+dice2);
    }
}
