import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Estates {
    public static final List<Estate> estatesList = List.of(
            new Estate("Haunted House", new Pos(2, 2), new Pos(5, 5),
                    Map.of("s", new Pos(5, 6), "d", new Pos(6, 3)),
                    occupantPositions(new Pos(3, 3))),

            new Estate("Calamity Castle", new Pos(2, 17), new Pos(5, 5),
                    Map.of("w", new Pos(3, 17), "d", new Pos(6, 18)),
                    occupantPositions(new Pos(3, 19))),

            new Estate("Maniac Manor", new Pos(17, 2), new Pos(5, 5),
                    Map.of("a", new Pos(17, 5), "s", new Pos(20, 6)),
                    occupantPositions(new Pos(19, 3))),

            new Estate("Peril Palace", new Pos(17, 17), new Pos(5, 5),
                    Map.of("w", new Pos(18, 17), "a", new Pos(17, 20)),
                    occupantPositions(new Pos(19, 19))),

            new Estate("Visitation Villa", new Pos(9, 10), new Pos(6, 4),
                    Map.of("w", new Pos(12, 10), "a", new Pos(9, 12), "s", new Pos(11, 13), "d", new Pos(14, 11)),
                    List.of(new Pos(10, 11), new Pos(11, 11), new Pos(12, 12), new Pos(13, 12)))
    );

    private static List<Pos> occupantPositions(Pos startPos) {
        List<Pos> poses = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                poses.add(new Pos(startPos.x + i, startPos.y + j));
            }
        }
        return poses;
    }

    /**
     * return the estate the door at position 'pos' is a part of
     */
    public static Estate getEstate(Pos pos) {
        if (pos.x < 8 && pos.y < 8) {
            return estatesList.get(0);
        }
        if (pos.x < 8 && pos.y >= 17) {
            return estatesList.get(1);
        }
        if (pos.x >= 17 && pos.y < 8) {
            return estatesList.get(2);
        }
        if (pos.x >= 17 && pos.y >= 17) {
            return estatesList.get(3);
        }
        return estatesList.get(4);
    }
}
