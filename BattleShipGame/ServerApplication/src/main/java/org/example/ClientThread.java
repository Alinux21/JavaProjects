package org.example;

import java.io.*;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;

class ClientThread extends Thread {
    private final Socket socket ;
    private boolean running = true;
    private final GameServer server;
    private Game game;
    private final Board board = new Board();
    private final PrintWriter out;
    private final BufferedReader in;
    private Timer timer;



    public ClientThread (Socket socket,GameServer server) {
            this.socket = socket ;
            this.server = server;
        try {
            this.out = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Board getBoard(){
        return board;
    }

    public void setRunning(boolean running){
        this.running = running;
    }

    public void startTimer() {
        timer = new Timer();
        int delay = 30000; // 30 sec.
        timer.schedule(new TimerTask() {
            public void run() {
                out.println("Your time is up. You lose!");
                out.flush();

                ClientThread opponent = game.getPlayers().get(0) == ClientThread.this ?
                        game.getPlayers().get(1) : game.getPlayers().get(0);
                opponent.getOut().println("Your opponent's time is up. You win!");

                game.endGame();
            }
        }, delay);
    }

    public void stopTimer() {
        if (timer != null) {
            timer.cancel();
        }
    }


    public void run () {
        try{
            out.println("\nWelcome to Battleship! Type 'create game' to start a new game or 'list games' to see the available games.");
            out.println("Type 'join game <game_id>' to join an existing game.");
            out.println("Type 'submit move <coordinates>' to make a move.");
            out.println("Type 'exit' to disconnect from the server.");
            out.flush();
            String request = in.readLine();

            while(running) {

                if(out==null || in==null){
                    break;
                }

                if (request.equals("exit")) {
                    System.out.println("Client disconnected...");
                    if (game != null) {
                        ClientThread opponent = game.getPlayers().get(0) == this ? game.getPlayers().get(1) : game.getPlayers().get(0);
                        opponent.getOut().println("Your opponent has disconnected. You win!");
                        opponent.getOut().flush();
                        opponent.closeConnection();
                    }
                    running = false;
                    break;

                } else if (request.equals("stop")) {
                    out.println("Server stopped");
                    out.flush();

                    server.stopServer();

                    socket.close();
                    in.close();
                    out.close();
                    running = false;
                    break;
                } else if (request.equals("create game")) {
                    this.game = server.createGame();
                    game.addPlayer(this);
                    out.println(board);
                    out.println("Game created with id:"+game.getGameId()+". Waiting for other player (1/2)");
                    out.flush();

                }else if(request.matches("^list games$")){
                    out.println(server.listGames());
                    out.flush();

                } else if (request.matches("^join game [0-9]{3}$")) {
                    if (game == null) {
                        Integer gameId = Integer.parseInt(request.split(" ")[2]);
                        Game game = server.getGame(gameId);
                        if (game == null) {
                            out.println("N" + getString());
                            out.flush();
                        } else {
                            game.addPlayer(this);
                            this.game = game;
                            game.startGame();
                        }
                    } else {
                        out.println("You are already in a game");
                        out.flush();
                    }
                }else if(request.matches("^submit move [A-Ja-j]([1-9]|10)$")){
                    if(game!=null){
                        String square = request.split(" ")[2].toUpperCase();
                        game.submitMove(this,square);
                    }else{
                        out.println("You are not in a game");
                        out.flush();
                    }
                }else {
                    String response = "Unknown command";
                    out.println(response);
                    out.flush();
                }

                try{
                    if (socket.isClosed()) {
                        System.out.println("Opponent disconnected...");
                        running = false;
                        break;
                    }
                    request = in.readLine();
                }catch (IOException e) {
                    if(e.equals("java.net.SocketException: Connection reset")){
                        System.out.println("Client disconnected...");
                    }
                    break;
                }
            }
        }catch (IOException e) {
            System.err.println ("Error io: " + e);
        }finally {
            try{
                socket.close();
                in.close();
                out.close();
            }catch (IOException e) {
                System.err.println ("Error io din finally : " + e);
            }
        }
    }

    private static String getString() {
        return "o game with that id";
    }


    public PrintWriter getOut() {
        return this.out;
    }

    public void closeConnection() {
        System.out.println("Closing connection...");
        if(game == null){
            System.out.println("Null game");
            return;
        }
        this.setRunning(false);
    }
}
