package bonus;

public interface Player extends Runnable {
    String getName();
    void setGame(Game game);
    void setRunning(boolean running);
    void checkSequence();
    void takeTurn();
    int getSequenceLength();
}
