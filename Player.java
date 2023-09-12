import javax.swing.*;
import java.util.*;

public class Player {
    public static final List<String> moves = List.of("Left", "Right", "Up", "Down");

    ////////////
    /* FIELDS */
    ////////////
    public final String name;
    private Pos position;
    private Board board;
    private Set<Card> hand = new HashSet<>();
    private Estate estate = null;
    // Guessing functionality
    private boolean canSolve = true;

    // to do with moving, move()
    private boolean enteredEstate = false;
    private boolean onDoor = false;
    private int numOfMoves = 0; // number of moves in the turn
    private int moveNum = 0; // the current move number
    private List<Pos> prevPoses = new ArrayList<>(); // The previous positions the player was during the turn

    public Player(String n, Pos p, Board b) {name = n; position = p;board = b;}
    public String toString() {return name;}
    /////////////
    /* GETTERS */
    /////////////
    public Pos position() {return position;}
    public Set<Card> hand() {return Collections.unmodifiableSet(hand);}
    public boolean canSolve() {return canSolve;}
    public int numOfMoves(){return numOfMoves;}
    public int moveNum(){return moveNum;}
    public Estate estate(){return estate;}
    public boolean enteredEstate(){return enteredEstate;}
    public String estateInfo(){
        if(estate==null){return "Not in an Estate";}
        String str = "In "+estate.name+"\n ";
        str += "Weapons in estate: "+estate.weapons();
        return str;
    }
    /////////////
    /* SETTERS */
    /////////////
    public void setNumOfMoves(int n){numOfMoves = n;}
    public void setEstate(Estate e){estate = e;}
    public void setPosition(Pos p){position = p;}
    public void addCard(Card c) {hand.add(c);}
    public void playCard(Card c){hand.remove(c);}
    public void failedSolve() {canSolve = false;}
    public void resetMoveData(){
        numOfMoves = 0;
        moveNum = 0;
        prevPoses.clear();}

    public void move(String move){
        onDoor = false;
        enteredEstate = false;
        // check that the player has no more free moves
        if(!freeTileCanMove()){
            JOptionPane.showMessageDialog(StateManager.display, (name+" has no more valid moves "),
                    "NO MORE MOVES", JOptionPane.INFORMATION_MESSAGE);
            resetMoveData();
            return;
        }
        // get new position based on inputted move
        Pos newPos = getPos(move, position);
        // if the new position is valid and the player has not been in that position
        // previously during the turn
        if (isValidPos(newPos, board, true)) {
            // if the new position is on a door tile
            if (onDoor) {
                if (estate == null) {walkThroughDoor(newPos, move, true);} 
                else {walkThroughDoor(newPos, move, false);}
            } else {
                // the new position is on a regular free tile
                // add the players previous position to 'prevPoses'
                prevPoses.add(position);
                // update the board and 'position' to the new position
                board.update(position, newPos);
                position = newPos;
                moveNum++;
            }
            // new position is invalid
        } else {
            System.out.println("Invalid position");
        }
        if(moveNum == numOfMoves){
            resetMoveData();
        }
    }

    ///////////////////////////////
    /* ENTER/EXIT ESTATE METHODS */
    ///////////////////////////////

    /**
     * moves the player through a door
     */
    private void walkThroughDoor(Pos doorPos, String move, boolean entering) {
        // move the player to the cell in front of the door (inside)
        // check the cell is free
        Pos nextPos = getPos(move, doorPos); // the tile on the other side of the door
        // if tile in front of door (inside) is occupied
        if (!isValidPos(nextPos, board, false)) {
            System.out.println("Invalid move. Player in front of door");
            return;
        }
        // set the estate that the player has entered
        if (entering) {
            enteredEstate = true;
            estate = Estates.getEstate(doorPos);
            JOptionPane.showMessageDialog(StateManager.display, (name+" has entered "+estate.name),
                    "ENTERED ESTATE", JOptionPane.INFORMATION_MESSAGE);
            System.out.println(name + " has entered " + estate.name);
        } else {
            JOptionPane.showMessageDialog(StateManager.display, (name+" has exited "+estate.name),
                    "EXITED ESTATE", JOptionPane.INFORMATION_MESSAGE);
            System.out.println(name + " has exited " + estate.name);
            estate = null;
        }
        // add the players previous position to 'prevPoses'
        prevPoses.add(position);
        prevPoses.add(doorPos);
        // update board and player position
        board.update(position, nextPos);
        position = nextPos;
        moveNum++;
    }

    //////////////////////
    /* CAN MOVE METHODS */
    //////////////////////

    /**
     * check if player can move (if on a free tile)
     */
    private boolean freeTileCanMove() {
        // for each potential tile to move to
        for (String move : moves) {
            Pos newPos = getPos(move, position);
            // check if the tile is valid/free
            if (isValidPos(newPos, board, false)) {
                // if the tile is a door
                if (board.grid()[newPos.y][newPos.x] == 'D') {
                    // check the tile on the other side of the door is free
                    if (isValidPos(getPos(move, newPos), board, false)) {
                        return true;
                    }
                } else {
                    return true;
                }
            }
        }
        return false;
    }
    
    //////////////////////
    /* POSITION METHODS */
    //////////////////////

    /**
     * return the new position the player would be based on the inputted move command from Pos 'p'
     */
    public Pos getPos(String move, Pos p) {
        return switch (move) {
            case "Up" -> new Pos(p.x, p.y - 1);
            case "Left" -> new Pos(p.x - 1, p.y);
            case "Down" -> new Pos(p.x, p.y + 1);
            case "Right" -> new Pos(p.x + 1, p.y);
            default -> null;
        };
    }

    /**
     * check that the new position is valid
     */
    private boolean isValidPos(Pos newPos, Board board, boolean fromMove) {
        // the player has already been on the newPos in the turn
        if (prevPoses.contains(newPos)) {return false;}
        // the newPos is out of bounds
        if (newPos.x > 23 || newPos.y > 23 || newPos.x < 0 || newPos.y < 0) {return false;}
        char c = board.grid()[newPos.y][newPos.x];
        // the newPos is a free tile
        if (c == '_') {return true;}
        // the newPos is a door tile
        if (c == 'D') {
            if (fromMove) {onDoor = true;}
            return true;
        }
        return false;
    }
}