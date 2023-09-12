import java.util.List;
import java.util.ArrayList;

import javax.swing.*;

/**
 * Represents the state where a player takes their turn.
 */
public class S_Turn implements States {
    GameData gameData;
    List<JButton> moveButtons = new ArrayList<JButton>();

    /**
     * Constructs an instance of the S_Turn class.
     *
     * @param gameData The game data.
     */
    S_Turn(GameData gameData) {
        this.gameData = gameData;
        moveButtons.clear();
    }

    /**
     * Draws the turn state, displaying player's turn information, the game board, and move buttons.
     */
    @Override
    public void draw() {
        // Title
        JLabel title = new JLabel("Turn State:    ");
        StateManager.display.add(title);

        // Turn information
        String infoStr = turnInfo();
        JLabel info = new JLabel(infoStr);
        StateManager.display.add(info);

        // Game board
        JPanel boardP = new BoardP(gameData, this);
        StateManager.display.add(boardP);

        // Move buttons
        createMoveButtons();
    }

    /**
     * Creates move buttons and attaches actions to them.
     */
    private void createMoveButtons() {
        for (String move : Player.moves) {
            JButton moveB = new JButton(move);
            moveB.addActionListener(l -> {
                Player player = gameData.currentPlayer();
                player.move(move);
                setCurrentState(player);
            });
            moveButtons.add(moveB);
            StateManager.display.add(moveB);
        }
    }

    /**
     * Sets the current state based on player's action.
     *
     * @param player The current player.
     */
    public void setCurrentState(Player player) {
        // Potentially make a guess
        if (player.enteredEstate()) {
            if (player.canSolve()) {
                StateManager.switchTo(new S_MakeGuessPrompt(gameData));
            } else {
                JOptionPane.showMessageDialog(StateManager.display,
                        player.name + " cannot make a guess", "CANNOT GUESS", JOptionPane.INFORMATION_MESSAGE);
            }
        }
        // Next player's turn
        else if (player.moveNum() == player.numOfMoves()) {
            if (player.canSolve()) {
                StateManager.switchTo(new S_SolveAttemptPrompt(gameData));
                return;
            }
            gameData.nextPlayersTurn();
            StateManager.switchTo(new S_Roll(gameData));
        }
        // Continue player's turn
        else {
            StateManager.switchTo(new S_Turn(gameData));
        }
    }

    /**
     * Generates the string containing player's turn information.
     *
     * @return The turn information string.
     */
    private String turnInfo() {
        Player player = gameData.currentPlayer();
        String str = player + "'s Turn:    Move: " + player.moveNum() + "/" + player.numOfMoves() + "    ";
        // Add position
        str += "Position: " + player.position() + "    ";
        // Add hand
        str += "Hand: " + player.hand() + "    ";
        // Add estate
        str += player.estateInfo();
        return str;
    }
}
