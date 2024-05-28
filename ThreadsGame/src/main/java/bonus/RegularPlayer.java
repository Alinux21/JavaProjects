package bonus;

import java.util.*;

public class RegularPlayer implements Player {
    private final String name;
    private Game game;
    private boolean running;
    private final List<Tile> tiles = new ArrayList<>();
    private final List<Tile> seq = new ArrayList<>();
    int sequenceLength;
    private boolean myTurn = false;

    public RegularPlayer(String name) {
        this.name = name;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public String getName() {
        return name;
    }

    public int getSequenceLength() {
        return this.sequenceLength;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public void checkSequence() {
        for (Tile tile : tiles) {
            if (tile.first() == seq.get(seq.size() - 1).second() && !seq.contains(tile)) {
                seq.add(tile);
                break;
            }
        }
    }

    @Override
    public synchronized void run() {
        while (running && game.gameRunning) {
            while (!myTurn) {
                try {
                    while (!myTurn && game.gameRunning) {
                        System.out.println(this.name + " waiting ...");
                        wait(); // wait until it's my turn or game is stopped
                    }

                    if (!game.gameRunning) {
                        break; // exit the loop if the game is stopped
                    }
                } catch (InterruptedException e) {
                    // thread interrupted, exit gracefully
                    return;
                }
            }

            System.out.println(this.name + "'s turn");

            List<Tile> extractedTiles;
            synchronized (game.getBag()) { // Ensure thread-safe access to the bag
                extractedTiles = game.getBag().extractTiles(1);
            }

            if (seq.size() == 0) {
                seq.add(extractedTiles.get(0));
            } else {
                checkSequence();
            }

            sequenceLength = seq.size();
            if (extractedTiles.isEmpty() || sequenceLength == game.getBag().n) {
                running = false;
            }
            extractedTiles.forEach(tile -> this.tiles.add(tile));
            game.nextTurn(); // inform that this turn has ended
            myTurn = false; // reset player's myTurn flag
            notifyAll(); // notify other players waiting for their turn

        }
        if (game.gameRunning) {
            System.out.println(this.name + " seq:" + seq);
        }
    }

    public synchronized void takeTurn() {
        myTurn = true;
        notify(); // notify this player that it's their turn
    }
}
