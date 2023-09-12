import javax.swing.*;

public class S_Refutions implements  States{
    GameData gameData;
    Circumstance guess;
    S_Refutions(GameData gameData, Circumstance guess){
        this.gameData = gameData;
        this.guess = guess;
    }

    @Override
    public void draw() {
        // title
        JLabel title = new JLabel("Refution State");
        StateManager.display.add(title);
        // guessStr
        String guessStr = guess.cards.toString();
        JLabel guessL = new JLabel(guessStr);
        StateManager.display.add(guessL);

        Player guesser = gameData.currentPlayer();
        Player refuter = gameData.getPlayerAfter(guesser);
        JButton refuteGuess = StateManager.createTransitionButton("Refute Guess", new S_RefuteGuess(gameData, guess, refuter));
        StateManager.display.add(refuteGuess);
    }
}
