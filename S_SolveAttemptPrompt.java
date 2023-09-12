import javax.swing.*;

/**
 * Represents the state where a player is prompted to decide whether they want to make a solve attempt.
 */
public class S_SolveAttemptPrompt implements States {
    GameData gameData;

    /**
     * Constructs an instance of the S_SolveAttemptPrompt class.
     *
     * @param gameData The game data.
     */
    S_SolveAttemptPrompt(GameData gameData) {
        this.gameData = gameData;
    }

    /**
     * Draws the solve attempt prompt state, displaying the question and answer buttons.
     */
    @Override
    public void draw() {
        // Question
        String str = "Does " + gameData.currentPlayer().name + " want to make a solve attempt?";
        JLabel question = new JLabel(str);
        StateManager.display.add(question);

        // Transition/answer buttons
        JButton yesB = StateManager.createTransitionButton("Yes", new S_MakeSolveAttempt(gameData));
        JButton noB = new JButton("No");
        noB.addActionListener(l -> {
            gameData.nextPlayersTurn();
            StateManager.switchTo(new S_Roll(gameData));
        });

        // Add buttons to display
        StateManager.display.add(yesB);
        StateManager.display.add(noB);
    }
}
