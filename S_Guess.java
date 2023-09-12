import javax.swing.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.List;

public class S_Guess implements States, ItemListener {
    Estate estate; // estate where the guess is taking place
    JComboBox<Card> estateB;
    JComboBox<Card> playerB;
    JComboBox<Card> weaponB;
    JLabel estateL, playerL, weaponL;
    Card estateC, playerC, weaponC;
    GameData gameData;
    S_Guess(GameData gameData){
        this.gameData = gameData;
    }

    @Override
    public void draw() {
        // Write the name of the game
        JLabel title = new JLabel(gameData.currentPlayer()+"'s Guess");
        StateManager.display.add(title);

        estate = gameData.currentPlayer().estate();
        estateC = Cards.allEstateCards.stream().filter(c -> c.name.equals(estate.name)).toList().get(0);
        Card[] playerArr = cardListToArray(gameData.gamePlayerCards());
        Card[] weaponArr = cardListToArray(Cards.allWeaponCards);
        //JComboBoxes
        estateB = new JComboBox<>(new Card[]{estateC});
        playerB = new JComboBox<>(playerArr);
        weaponB = new JComboBox<>(weaponArr);

        playerC = (Card) playerB.getSelectedItem();
        weaponC = (Card) weaponB.getSelectedItem();

        playerB.addItemListener(this);
        weaponB.addItemListener(this);

        StateManager.display.add(estateB);
        StateManager.display.add(playerB);
        StateManager.display.add(weaponB);

        //JLabels
        estateL = new JLabel("Estate: "+estateC.name);
        playerL = new JLabel("Player: "+playerC.name);
        weaponL = new JLabel("Weapon: "+weaponC.name);
        StateManager.display.add(estateL);
        StateManager.display.add(playerL);
        StateManager.display.add(weaponL);

        // confirm button
        JButton confirmB = new JButton("Confirm");
        StateManager.display.add(confirmB);
        confirmB.addActionListener(l -> {
            moveWeapon();
            movePlayer();
            Circumstance guess = new Circumstance(List.of(estateC, playerC, weaponC));
            if(!gameData.potentialSolutions().contains(guess)) {
                StateManager.switchTo(new S_Refutions(gameData, guess));
            }
            else{
                JOptionPane.showMessageDialog(StateManager.display, ("This guess is already a potential solution"),
                        "DUPLICATE", JOptionPane.WARNING_MESSAGE);
            }
        });
    }

    /**
     * move the selected weapon from its old estate to the guess' estate
     */
    private void moveWeapon(){
        Weapon weapon = gameData.weapons().stream().filter(w -> w.name.equals(weaponC.name)).toList().get(0);
        if(estate.weapons().contains(weapon)){return;}
        Estate oldEstate = weapon.estate();
        oldEstate.removeWeapon(weapon);
        estate.addWeapon(weapon);
    }
    /**
     * move the selected player from where they were to the guess' estate
     */
    private void movePlayer(){
        Player player = gameData.players().stream().filter(p -> p.name.equals(playerC.name)).toList().get(0);
        if(player.estate()!= null && player.estate().equals(estate)){return;}
        Pos newPos = gameData.getFreeGuessPos(estate);
        gameData.board.update(player.position(), newPos);
        player.setPosition(newPos);
        player.setEstate(estate);
        player.resetMoveData();
    }

    private Card[] cardListToArray(List<Card> list){
        int len = list.size();
        Card[] arr = new Card[len];
        for(int i=0; i<len; i++){
            arr[i] = list.get(i);
        }
        return arr;
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if(e.getSource() == playerB){
            playerC = (Card) playerB.getSelectedItem();
        }
        if(e.getSource() == weaponB){
            weaponC = (Card) weaponB.getSelectedItem();
        }
        StateManager.display.revalidate();
    }
}