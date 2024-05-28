package bonus;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

public class Bag {
    private final List<Tile> tiles = new ArrayList<>();
    int n;

    public Bag(int n) {
        this.n = n;
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                if (i != j) {
                    Tile tile1 = new Tile(i, j);
                    if (!tiles.contains(tile1)) {
                        tiles.add(new Tile(i, j));
                    }
                }
            }
        }
        Collections.shuffle(tiles);
    }

    public synchronized List<Tile> extractTiles(int howMany) {

        List<Tile> extracted = new ArrayList<>();
        for (int i = 0; i < howMany; i++) {
            if (this.tiles.size() == 0) {
                break;
            }
            extracted.add(this.tiles.get(i));
            this.tiles.remove(this.tiles.get(i)); // once extracted we remove the tile from the bag
        }
        return extracted;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Tiles:\n");
        tiles.stream().forEach(tile -> sb.append(tile.toString() + "\n"));
        return sb.toString();
    }

}