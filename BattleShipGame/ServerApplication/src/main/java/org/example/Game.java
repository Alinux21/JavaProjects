package org.example;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Game {
    private final GameServer server;
    private final List<ClientThread> players = new ArrayList<>();
    private final Integer gameId;
    private boolean isPlayer1Turn = true;
    private final int[] totalHits = {0, 0};

    public Game(Integer gameId,GameServer server) {
        this.gameId=gameId;
        this.server = server;
    }

    public void addPlayer(ClientThread player) {
        players.add(player);
    }

    public void startGame() throws IOException {
        if (players.size() == 2) {

            ClientThread player1 = players.get(0);
            ClientThread player2 = players.get(1);

            player1.getOut().println("Game started!");
            player1.getOut().println("Your board:\n" + player1.getBoard());
            player1.getOut().println("Opponent's board:\n" + player2.getBoard().getMaskedBoard());
            player1.getOut().println("You go first. Enter the coordinates of your attack (e.g., A5)");
            player1.getOut().flush();

            player2.getOut().println("Game started!");
            player2.getOut().println("Your board:\n" + player2.getBoard());
            player2.getOut().println("Opponent's board:\n" + player1.getBoard().getMaskedBoard());
            player2.getOut().println("Waiting for opponent's move...");
            player2.getOut().flush();

        }
    }

    public void submitMove(ClientThread player, String square) {
        if (players.size() == 2) {

            if ((isPlayer1Turn && player != players.get(0)) || (!isPlayer1Turn && player != players.get(1))) {
                player.getOut().println("It's not your turn yet!");
                player.getOut().flush();
                return;
            }

            ClientThread opponent = players.get(0) == player ? players.get(1) : players.get(0);

            Board opponentBoard = opponent.getBoard();
            int y = square.charAt(0) - 'A'; // y should correspond to the column (letter)
            int x = Integer.parseInt(square.substring(1)) - 1; // x should correspond to the row (number)

            System.out.println("Player " + player + " attacked square " + square + " on opponent's board");
            System.out.println(x + " " + y);

            if (opponentBoard.getGrid()[x][y].startsWith("Ship")) {
                String shipType = opponentBoard.getGrid()[x][y];
                opponentBoard.getGrid()[x][y] = "❌";
                player.getOut().println("Hit! " + shipType);


                //increment the total hits count for the player
                int playerIndex = player == players.get(0) ? 0 : 1;
                totalHits[playerIndex]++;

                //check if all ships have been destroyed
                if (totalHits[playerIndex] == 14) {
                    player.getOut().println("Opponent's board:\n" + opponentBoard.getMaskedBoard());
                    player.getOut().println("You have destroyed all opponent's ships. You win!");
                    player.getOut().flush();

                    opponent.getOut().println("Your board:\n" + opponent.getBoard());
                    opponent.getOut().println("All your ships have been destroyed. You lose!");
                    opponent.getOut().flush();
                    endGame();
                    return;
                }


            }else {
                opponentBoard.getGrid()[x][y] = "💨";
                player.getOut().println("Miss!");
            }

            player.getOut().println("Your move: " + square);
            player.getOut().println("Your board:\n" + player.getBoard());
            player.getOut().println("Opponent's board:\n" + opponentBoard.getMaskedBoard());
            player.getOut().println("Waiting for opponent's move...");
            player.getOut().flush();

            opponent.getOut().println("Opponent's move: " + square);
            opponent.getOut().println("Your board:\n" + opponent.getBoard());
            opponent.getOut().println("Opponent's board:\n" + player.getBoard().getMaskedBoard());
            opponent.getOut().println("Your turn. Enter the coordinates of your attack (e.g., submit move A5)");
            opponent.getOut().flush();

            //stop the current player's timer and start the opponent's timer
            player.stopTimer();
            opponent.startTimer();
            opponent.getOut().println("You have 30 seconds to make a move or you will be eliminated.");
            opponent.getOut().flush();

            isPlayer1Turn = !isPlayer1Turn;
        }
    }

    synchronized void endGame() {
        //stop the timers before closing the connections
        players.get(0).stopTimer();
        players.get(1).stopTimer();

        players.get(0).closeConnection();
        players.get(1).closeConnection();
        players.clear();
        server.removeGame(this);
    }

    public List<ClientThread> getPlayers() {
        return players;
    }


    @Override
    public String toString(){

        StringBuilder sb = new StringBuilder();
        sb.append("Game id:").append(gameId).append("\n");
        sb.append("Players:\n");
        for(ClientThread player : players){
            sb.append(player.toString());
            sb.append("\n");
        }
        return sb.toString();
    }


    public Integer getGameId() {
        return this.gameId;
    }
}
