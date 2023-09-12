import javax.swing.*;
import java.util.List;

public class S_RefuteGuess implements States {
    GameData gameData;
    Circumstance guess;
    Player refuter; // the player that is refuting the guess
    List<Card> refutingCards; // cards that refute the guess
    List<Card> nonRefutingCards; // cards that do not refute the guess
    S_RefuteGuess(GameData gameData, Circumstance guess, Player refuter){
        this.gameData = gameData;
        this.guess = guess;
        this.refuter = refuter;

    }
    @Override
    public void draw() {
        StateManager.display.removeAll();
        // title
        JLabel title = new JLabel(refuter.name+" refutes guess: "+guess);
        StateManager.display.add(title);
        // cards print out
        JLabel handL = new JLabel("Player's hand: "+refuter.hand());
        StateManager.display.add(handL);
        // determine which cards in the players hand can refute the guess (and which can not)
        refutingCards = refuter.hand().stream().filter(c -> guess.cards.contains(c)).toList();
        nonRefutingCards = refuter.hand().stream().filter(c -> !guess.cards.contains(c)).toList();
        System.out.println("refuting: "+refutingCards);
        System.out.println("non-refuting: "+nonRefutingCards);

        if(ifPlayerCanNotRefuteGuess()){return;}
        setRefutingCardButtons();
        setNonRefutingCardButtons();

    }

    /**
     * if the player can not refute the guess go to the next player
     * or add the guess to the list of potential solutions
     */
    private boolean ifPlayerCanNotRefuteGuess(){
        // if the player can not refute the guess
        if(refutingCards.isEmpty()){
            JOptionPane.showMessageDialog(StateManager.display, refuter+ " has no refuting cards",
                    "NO REFUTING CARDS", JOptionPane.INFORMATION_MESSAGE);
            Player nextPlayer = gameData.getPlayerAfter(refuter);
            // the current player will always be the player that made the guess
            if(nextPlayer.equals(gameData.currentPlayer())){
                // add guess to potential solution
                JOptionPane.showMessageDialog(StateManager.display,"guess is a potential solution","POTENTIAL SOLUTION", JOptionPane.INFORMATION_MESSAGE);
                gameData.addToPotentialSolutions(guess);
                goToNextState();
            }
            else {
                // let the next player attempt to refute the guess
                StateManager.switchTo(new S_RefuteGuess(gameData, guess, nextPlayer));
            }
            return true;
        }
        return false;
    }

    /** create and set the buttons corresponding to non-refuting cards to the display */
    private void setNonRefutingCardButtons(){
        for(Card c : nonRefutingCards){
            JButton b = new JButton(c.toString());
            StateManager.display.add(b);
            b.addActionListener(l -> {
                JOptionPane.showMessageDialog(StateManager.display, "Can not play this card",
                        "WARNING", JOptionPane.ERROR_MESSAGE);
            });
        }
    }
    /** create and set the buttons corresponding to refuting cards to the display */
    private void setRefutingCardButtons(){
        for(Card c : refutingCards){
            JButton b = new JButton(c.toString());
            StateManager.display.add(b);
            b.addActionListener(l -> {
                refuter.playCard(c);
                JOptionPane.showMessageDialog(StateManager.display, (refuter.name+" refuted the guess with: "+c),
                        "GUESS REFUTED", JOptionPane.INFORMATION_MESSAGE);
                goToNextState();
            });
        }
    }

    /**
     * if there are potential solutions give the current player to option to make a solve
     * else start the next players turn
     */
    private void goToNextState(){
        if(gameData.currentPlayer().canSolve() && !gameData.potentialSolutions().isEmpty()){
            StateManager.switchTo(new S_SolveAttemptPrompt(gameData));
        }else{
            gameData.nextPlayersTurn();
            StateManager.switchTo(new S_Roll(gameData));
        }
    }
}
