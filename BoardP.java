import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Map;
import java.util.List;

public class BoardP extends JPanel implements MouseListener {
    private GameData gameData;
    S_Turn sTurn = null;
    List<String> playerNames;
    private int sqrSize = 10;
    private int xOffSet = 20;
    Map<String, Color> playerColors = Map.of("Malina", Color.PINK, "Bert", Color.GREEN.darker(), "Lucilla", Color.CYAN, "Percy", Color.BLUE.brighter());
    Map<String, Color> estateColors = Map.of("Haunted House", Color.GREEN,
                                            "Calamity Castle", Color.RED,
                                            "Maniac Manor", Color.BLUE,
                                            "Peril Palace", Color.YELLOW,
                                            "Visitation Villa", Color.ORANGE);
    /**
     * Constructs a BoardP instance.
     *
     * @param gameData The game data used to display the board.
     */
    BoardP(GameData gameData, S_Turn sTurn){
        this.sTurn = sTurn;
        this.gameData = gameData;
        playerNames = gameData.players().stream().map(p -> p.name).toList();
        setPreferredSize(new Dimension(600, 600));
        setVisible(true);
        setLayout(null);
        addMouseListener(this);
    }

    BoardP(GameData gameData){
        this.gameData = gameData;
        playerNames = gameData.players().stream().map(p -> p.name).toList();
        setPreferredSize(new Dimension(600, 600));
        setVisible(true);
        setLayout(null);
        addMouseListener(this);
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        for (int i = 0; i < 24; i++) {
            for (int j = 0; j < 24; j++) {
                drawCell(i, j, g);
            }
        }
        drawLegend(g, 20, 300);
    }

    private void drawLegend(Graphics g, int x, int y){

        // parameters
        int xSpacing = 15;
        int ySpacing = 10;
        int rowSpacing = 30;
        int colSpacing = 100;
        // legend title
        g.drawString("LEGEND", x+colSpacing/2, y-rowSpacing);
        // player keys
        int i = 0;
        for(String player : playerColors.keySet()){
            if(playerNames.contains(player)) {
                g.setColor(playerColors.get(player));
                g.fillRect(x, y + rowSpacing * i, 10, 10);
                g.setColor(Color.BLACK);
                g.drawString(player, x + xSpacing, y + ySpacing + rowSpacing * i);
                i++;
            }
        }
        i = 0;
        x += colSpacing;
        // estate keys
        for(String estate : estateColors.keySet()){
            g.setColor(estateColors.get(estate));
            g.fillRect(x, y+rowSpacing*i, 10, 10);
            g.setColor(Color.BLACK);
            g.drawString(estate, x+xSpacing, y+ySpacing+rowSpacing*i);
            i++;
        }
    }
    private void drawCell(int row, int col, Graphics g){
        char c = gameData.board.grid()[row][col];
        Color cellCol = getColor(c);

        //draw cell
        g.setColor(cellCol);
        g.fillRect(xOffSet+col*sqrSize, row*sqrSize, sqrSize, sqrSize);

        //draw grid outline
        g.setColor(Color.BLACK);
        g.drawRect(xOffSet+col*sqrSize, row*sqrSize, sqrSize, sqrSize);
    }
    private Color getColor(char c) {
        return switch (c) {
            // Estates
            case 'H' -> estateColors.get("Haunted House");
            case 'M' -> estateColors.get("Maniac Manor");
            case 'V' -> estateColors.get("Visitation Villa");
            case 'C' -> estateColors.get("Calamity Castle");
            case 'P' -> estateColors.get("Peril Palace");
            // Players
            case 'm' -> playerColors.get("Malina");
            case 'b' -> playerColors.get("Bert");
            case 'l' -> playerColors.get("Lucilla");
            case 'p' -> playerColors.get("Percy");
            // Other
            case 'W' -> Color.DARK_GRAY;
            case 'D' -> Color.WHITE;
            // Empty Tile
            default -> Color.LIGHT_GRAY;
        };
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(sTurn != null) {
            //check which cell was clicked
            //if the cell is next to the player, call move(string direction)
            int clickedRow = e.getY() / sqrSize;
            int clickedCol = (e.getX() - xOffSet) / sqrSize;

            int currentPlayerRow = gameData.currentPlayer().position().y;
            int currentPlayerCol = gameData.currentPlayer().position().x;

            //if the cell is next to the player, call move(string direction)
            String direction = null;
            if (clickedRow == currentPlayerRow && clickedCol == currentPlayerCol - 1) {
                direction = "Left";
            } else if (clickedRow == currentPlayerRow && clickedCol == currentPlayerCol + 1) {
                direction = "Right";
            } else if (clickedRow == currentPlayerRow - 1 && clickedCol == currentPlayerCol) {
                direction = "Up";
            } else if (clickedRow == currentPlayerRow + 1 && clickedCol == currentPlayerCol) {
                direction = "Down";
            }
            if (direction != null) {
                Player player = gameData.currentPlayer();
                player.move(direction);
                sTurn.setCurrentState(player);
                //print tried to move
                System.out.println("Tried to move " + direction);
                repaint();
            }
            //print clicked on cell
            System.out.println("Clicked on cell " + clickedRow + ", " + clickedCol);
            //print current player position
            System.out.println("Current player position: " + currentPlayerRow + ", " + currentPlayerCol);
        }
    }




    @Override
    public void mousePressed(MouseEvent e) {
    }




    @Override
    public void mouseReleased(MouseEvent e) {
    }




    @Override
    public void mouseEntered(MouseEvent e) {
    }




    @Override
    public void mouseExited(MouseEvent e) {
    }
}
