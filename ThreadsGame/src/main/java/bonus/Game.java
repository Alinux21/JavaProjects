package bonus;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private final Bag bag = new Bag(5); // change the n from Bag(n) to have tokens until n
    private final List<Player> players = new ArrayList<>();

    List<Thread> playerThreads = new ArrayList<>(); // store created threads
    int currentTurn = 0;
    boolean gameRunning = true;

    public void addPlayer(Player player) {
        players.add(player);
        player.setGame(this);
    }

    public void play() {

        players.get(0).takeTurn();

        for (Player player : players) {
            System.out.println("Started thread for " + player.getName());
            player.setRunning(true);

            Thread thread = new Thread(player);
            playerThreads.add(thread); // add created thread to the list

            thread.start();
        }

        joinPlayers();

    }

    public synchronized void stopGame() {
        gameRunning = false;
        // interrupt all player threads to stop their execution
        for (Thread thread : playerThreads) {
            thread.interrupt();
        }
    }

    public synchronized void nextTurn() {
        currentTurn++;
        if (currentTurn >= players.size()) {
            currentTurn = 0; // reset to the first player
        }
        players.get(currentTurn).takeTurn();
        notifyAll(); // notify all players after a turn is taken
    }

    public void joinPlayers() {
        // wait for all player threads to finish
        for (Thread thread : playerThreads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public Bag getBag() {
        return this.bag;
    }

    public List<Player> getPlayers() {
        return players;
    }

    @Override
    public String toString() {
        return "==Game==\n" + bag.toString();
    }

    public static void main(String args[]) {

        // each player takes its turn and waits 0.9 seconds

        Game game = new Game();
        System.out.println(game);
        // game.addPlayer(new RegularPlayer("Player 1"));
        game.addPlayer(new SmartPlayer("Player 3"));
        game.addPlayer(new RegularPlayer("Player 2"));

        game.play();

        // wait for the game to finish
        game.joinPlayers();

        // determine the winner(s)
        List<Player> winners = new ArrayList<>();
        int maxSequenceLength = 0;
        for (Player player : game.getPlayers()) {
            if (player.getSequenceLength() > maxSequenceLength) {
                winners.clear();
                winners.add(player);
                maxSequenceLength = player.getSequenceLength();
            } else if (player.getSequenceLength() == maxSequenceLength) {
                winners.add(player);
            }
        }

        // print out the winner(s) or draw message
        if (!winners.isEmpty()) {
            if (winners.size() == 1) {
                Player winner = winners.get(0);
                System.out.println("\nWinner: " + winner.getName() + " with score: " + maxSequenceLength);
            } else if (winners.size() == game.players.size()) {
                System.out.println("\nThe game ended in a draw!");
            } else {
                System.out.println("\nIt's a tie between the following players:");
                for (Player tiePlayer : winners) {
                    System.out.println(tiePlayer.getName() + " with score: " + maxSequenceLength);
                }
            }
        }

    }
}