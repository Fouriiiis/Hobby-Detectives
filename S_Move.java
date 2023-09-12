import javax.swing.*;
import java.util.List;

public class S_Move implements States {
    private GameData gameData;

    S_Move(List<String> playerNames){
        gameData = new GameData(playerNames);
    }

    @Override
    public void draw() {
        // Add button that switches to game state
        JButton b = StateManager.createTransitionButton("Menu", new S_Menu());
        StateManager.display.add(b);
        JLabel order = new JLabel("Players: "+gameData.players().toString());
        StateManager.display.add(order);
    }

}
