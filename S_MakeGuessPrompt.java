import javax.swing.*;
public class S_MakeGuessPrompt implements States {
    GameData gameData;
    S_MakeGuessPrompt(GameData gameData){
        this.gameData = gameData;
    }
    @Override
    public void draw() {
        // Write the name of the game
        JLabel title = new JLabel("Will "+gameData.currentPlayer()+" make a guess?");
        StateManager.display.add(title);
        // weapons
        JLabel weapons = new JLabel("Weapons in estate: "+gameData.currentPlayer().estate().weapons());
        StateManager.display.add(weapons);
        // yes button
        //JButton yesB = StateManager.createTransitionButton("Yes", new S_Guess(gameData));
        JButton yesB = new JButton("Yes");
        yesB.addActionListener(l -> {
            Player player = gameData.currentPlayer();
            Pos newPos = gameData.getFreeGuessPos(player.estate());
            gameData.board.update(player.position(), newPos);
            player.setPosition(newPos);
            player.resetMoveData();
            StateManager.switchTo(new S_Guess(gameData));
        });
        StateManager.display.add(yesB);
        // no button
        JButton noB = StateManager.createTransitionButton("No", new S_Turn(gameData));
        StateManager.display.add(noB);
        // board
        JPanel boardP = new BoardP(gameData);
        StateManager.display.add(boardP);
    }
}