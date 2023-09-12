import javax.swing.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.List;

public class S_MakeSolveAttempt implements States, ItemListener {
    JComboBox<Circumstance> CircumstanceBox;
    GameData gameData;
    S_MakeSolveAttempt(GameData gameData){
        this.gameData = gameData;
    }
    @Override
    public void draw() {
        // question
        String str = "choose a potential solution";
        JLabel question = new JLabel(str);
        StateManager.display.add(question);
        // solution selector
        Circumstance[] solutionArr = circumstanceListToArray(gameData.potentialSolutions());
        CircumstanceBox = new JComboBox<>(solutionArr);
        StateManager.display.add(CircumstanceBox);
        // solution choice printout
        JLabel solution = new JLabel("Choice: "+CircumstanceBox.getSelectedItem().toString());
        StateManager.display.add(solution);
        // confirm button
        JButton confirmB = createConfirmButton();
        StateManager.display.add(confirmB);
    }

    private JButton createConfirmButton(){
        JButton confirmB = new JButton("Confirm");
        confirmB.addActionListener(l -> {
            Circumstance choice = (Circumstance) CircumstanceBox.getSelectedItem();
            gameData.removePotentialSolution(choice);
            if(choice.equals(gameData.solution())){
                // guessed the solution (win)
                JOptionPane.showMessageDialog(StateManager.display, ("You Win"),
                        "YOU WIN", JOptionPane.INFORMATION_MESSAGE);
                StateManager.switchTo(new S_EndGame(gameData, true));
            }
            else{
                // player failed their solve attempt
                Player p = gameData.currentPlayer();
                p.failedSolve();
                JOptionPane.showMessageDialog(StateManager.display, (p.name+" failed their solve attempt."),
                        "SOLVE FAILED", JOptionPane.INFORMATION_MESSAGE);
                // check if any other player can still make a guess (else game is lost)
                if(gameData.canStillWin()){
                    // a player can still win the game
                    gameData.nextPlayersTurn();
                    StateManager.switchTo(new S_Roll(gameData));
                }
                else{
                    // (lose)
                    JOptionPane.showMessageDialog(StateManager.display, ("You Lose"),
                            "YOU LOSE", JOptionPane.INFORMATION_MESSAGE);
                    StateManager.switchTo(new S_EndGame(gameData, false));
                }
            }
        });
        return confirmB;
    }

    /** converts a list of Circumstance objects into an array
     *  (used only in the construction of the JComboBox)
     */
    private Circumstance[] circumstanceListToArray(List<Circumstance> list){
        int len = list.size();
        Circumstance[] arr = new Circumstance[len];
        for(int i=0; i<len; i++){
            arr[i] = list.get(i);
        }
        return arr;
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        StateManager.display.revalidate();
    }
}
