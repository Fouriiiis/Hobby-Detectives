import java.util.List;

public class Board {
    //public final Game game;
    private char[][] grid = new char[24][24];

    /**
     * update the board and reprint it
     */
    public void update(Pos oldPos, Pos newPos) {
        char c = grid[oldPos.y][oldPos.x];
        grid[oldPos.y][oldPos.x] = '_';
        grid[newPos.y][newPos.x] = c;
    }
    public char[][] grid(){return grid;}

    ////////////////////////////
    /* SETUP() HELPER METHODS */
    ////////////////////////////
    public void setup(List<Player> players) {
        initializeBoard();
        addEstatesToBoard();
        addWallsToBoard();
        addPlayersToBoard(players);
    }

    private void initializeBoard() {
        for (int i = 0; i < 24; i++) {
            for (int j = 0; j < 24; j++) {
                grid[i][j] = '_';
            }
        }
    }

    private void addEstatesToBoard() {
        for (Estate e : Estates.estatesList) {
            char letter = e.name.charAt(0);
            letter = Character.toUpperCase(letter);
            // input top and bottom walls
            for (int i = 0; i < e.size.y; i++) {
                grid[e.pos.y + i][e.pos.x] = letter;
                grid[e.pos.y + i][e.pos.x + e.size.x - 1] = letter;
            }
            // input left and right walls
            for (int i = 0; i < e.size.x; i++) {
                grid[e.pos.y][e.pos.x + i] = letter;
                grid[e.pos.y + e.size.y - 1][e.pos.x + i] = letter;
            }
            // input doors
            for (Pos p : e.keyToDoor.values()) {
                grid[p.y][p.x] = 'D';
            }
        }
    }

    private void addWallsToBoard() {
        for (Pos p : Wall.locations) {
            for (int i = 0; i < Wall.size; i++) {
                for (int j = 0; j < Wall.size; j++) {
                    grid[p.y + i][p.x + j] = 'W';
                }
            }
        }
    }

    private void addPlayersToBoard(List<Player> players) {
        for (Player p : players) {
            char c = p.name.charAt(0);
            c = Character.toLowerCase(c);
            grid[p.position().y][p.position().x] = c;
        }
    }
}
