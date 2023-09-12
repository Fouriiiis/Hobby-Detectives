import java.util.*;
import java.util.stream.Collectors;

public class GameData {
    private Set<Card> handCards = new HashSet<>(List.of(
            new Card("estate", "Haunted House"),
            new Card("estate", "Calamity Castle"),
            new Card("estate", "Maniac Manor"),
            new Card("estate", "Peril Palace"),
            new Card("estate", "Visitation Villa"),
            new Card("weapon", "Broom"),
            new Card("weapon", "Scissors"),
            new Card("weapon", "Knife"),
            new Card("weapon", "Shovel"),
            new Card("weapon", "iPad"),
            new Card("character", "Lucilla"),
            new Card("character", "Bert"),
            new Card("character", "Percy"),
            new Card("character", "Malina")));

    ////////////
    /* FIELDS */
    ////////////
    private Set<Weapon> weapons = new HashSet<>(); // weapon objects to distribute
    private List<Card> gamePlayerCards = new ArrayList<>();
    public final Board board = new Board();
    private List<Player> players = new ArrayList<>(List.of(
            new Player("Lucilla", new Pos(8, 3), board),
            new Player("Bert", new Pos(5, 8), board),
            new Player("Malina", new Pos(8, 18), board),
            new Player("Percy", new Pos(18, 15), board)));
    private Circumstance solution = null; // the solution of the game
    private List<Circumstance> potentialSolutions = new ArrayList<>(); // the list of potential solutions made
    private Player currentPlayer;

    /////////////
    /* GETTERS */
    /////////////
    public Player currentPlayer(){return currentPlayer;}
    public List<Card> gamePlayerCards(){return Collections.unmodifiableList(gamePlayerCards);}
    public List<Player> players() {return Collections.unmodifiableList(players);}
    public Circumstance solution(){return solution;}
    public Set<Weapon> weapons(){return Collections.unmodifiableSet(weapons);}
    public List<Circumstance> potentialSolutions(){return Collections.unmodifiableList(potentialSolutions);}

    /////////////
    /* SETTERS */
    /////////////
    public void setRoll(int n){currentPlayer.setNumOfMoves(n);}
    public void addToPotentialSolutions(Circumstance guess){potentialSolutions.add(guess);}
    public void removePotentialSolution(Circumstance c){potentialSolutions.remove(c);}
    public void nextPlayersTurn(){
        // get index of current player
        int idx = players.indexOf(currentPlayer);
        // get the index of the next player in the order
        idx++;
        idx = idx % players.size();
        // set the next player as the current player
        currentPlayer = players.get(idx);
    }
    public Player getPlayerAfter(Player p){
        // get index of player p
        int idx = players.indexOf(p);
        // get the index of the next player in the order
        idx++;
        idx = idx % players.size();
        return players.get(idx);
    }

    GameData(List<String> playerNames){setup(playerNames);}

    /**
     * returns random integer between 'Min' and 'Max' inclusive
     */
    public static int generateNum(int Min, int Max) {
        return (Min + (int) (Math.random() * ((Max - Min) + 1)));
    }

    public boolean canStillWin(){
        for (Player p : players) {
            // a player can still solve the murder
            if (p.canSolve()) {
                return true;
            }
        }
        // no player can solve the murder
        return false;
    }

    public Pos getFreeGuessPos(Estate estate){
        for(Pos pos : estate.guessPoses){
            if(board.grid()[pos.y][pos.x] == '_'){
                return pos;
            }
        }
        return null;
    }


    //////////////////////////////
    /* SETUP() + HELPER METHODS */
    //////////////////////////////

    /**
     * sets up the game
     */
    public void setup(List<String> playerNames) {
        // setup players and current player
        initPlayers(playerNames);
        currentPlayer = players.get(0);
        System.out.println("Welcome to the setup");
        // generate the solution
        generateSolution();
        // generate each player's hand
        generateHands();
        // randomly distribute the weapons across the estates
        createAndDistributeWeapons();
        // set up the board
        board.setup(players);
        testSituation();
    }

    private void initPlayers(List<String> playerNames) {
        List<Player> list = new ArrayList<>();
        for(String name : playerNames){
            Player player = players.stream().filter(p -> p.name.equals(name)).toList().get(0);
            Card card = Cards.allPlayerCards.stream().filter(c -> c.name.equals(name)).toList().get(0);
            gamePlayerCards.add(card);
            list.add(player);
        }
        players = list;
    }

    private void generateSolution() {
        List<Card> cards = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            String t = Cards.cardTypes[i];
            Card card = handCards.stream()
                    .filter(c -> c.type.equals(t))
                    .findAny()
                    .orElseThrow(NoSuchElementException::new);
            cards.add(card);
            handCards.remove(card);
        }
        solution = new Circumstance(cards);
    }

    private void generateHands() {
        while (!handCards.isEmpty()) {
            for (Player p : players) {
                if (!handCards.isEmpty()) {
                    Card card = handCards.stream()
                            .findAny()
                            .orElseThrow(NoSuchElementException::new);
                    p.addCard(card);
                    handCards.remove(card);
                }
            }
        }
    }

    private void createAndDistributeWeapons() {
        List<String> weaponNames = Cards.allWeaponCards.stream().map(c -> c.name).collect(Collectors.toList());
        for (Estate e : Estates.estatesList) {
            int idx = generateNum(0, weaponNames.size() - 1);
            String name = weaponNames.remove(idx);
            Weapon w = new Weapon(name, e);
            weapons.add(w);
            e.addWeapon(w);
        }
        System.out.println(weaponNames + " : " + weapons);
    }

    ///////////////////////
    /* DEBUGGING METHODS */
    ///////////////////////

    /**
     * the setup to test game win/lose operations
     */
    private void testSituation() {
        potentialSolutions.add(new Circumstance(List.of(new Card("estate", "Calamity Castle"), new Card("character", "Lucilla"), new Card("weapon", "Scissors"))));
        solution = new Circumstance(List.of(new Card("estate", "Calamity Castle"), new Card("character", "Lucilla"), new Card("weapon", "Scissors")));
        for (int i = 0; i < 4; i++) {
            potentialSolutions.add(generateRandomGuess());
        }
        System.out.println(potentialSolutions);

    }

    /**
     * generates a randomised Circumstance to add to potSolutions
     */
    private Circumstance generateRandomGuess() {
        List<Card> cards = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            List<Card> cards2 = Cards.allEstateCards;
            if (i == 1) {
                cards2 = Cards.allPlayerCards;
            }
            if (i == 2) {
                cards2 = Cards.allWeaponCards;
            }
            Card card = cards2.get(generateNum(0, cards2.size() - 1));
            cards.add(card);
        }
        System.out.println(cards);
        return new Circumstance(cards);
    }
}
