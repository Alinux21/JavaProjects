package org.example;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.*;
import java.util.List;

public class GameServer {
    public static final int PORT = 8100;
    private boolean running = true;
    private final List<ClientThread> clients = new ArrayList<>();
    private final List<Game> games = new ArrayList<>();
    private ServerSocket serverSocket = null;

    public GameServer() throws IOException {
        try {
            serverSocket = new ServerSocket(PORT);
            while (running) {
                System.out.println ("Waiting for a client ...");
                Socket socket = serverSocket.accept();

                //execute the client's request in a new thread
                System.out.println("Creating a new thread for the client");
                ClientThread clientThread = new ClientThread(socket,this);
                this.clients.add(clientThread);
                clientThread.start();
            }
        } catch (SocketException e) { //caused because accept() method is blocking and
                                      // when the server is stopped, the serverSocket is closed and the
                                      //accept() method throws a SocketException
            System.out.println("Server stopped");
        }
        catch (IOException e) {
            System.err. println ("Error : " + e);
        } finally {
            if (serverSocket != null) {
                serverSocket.close();
            }
        }
    }
    public synchronized Game createGame(){
        Random random = new Random();
        Integer id = 100 + random.nextInt(900); //generates a random 3-digit number
        System.out.println("Creating game with id: " + id);
        Game game = new Game(id,this);
        games.add(game);
        return game;
    }

    public synchronized void removeGame(Game game){
        games.remove(game);
    }

    public synchronized Game getGame(Integer id){
        for(Game game : games){
            if(game.getGameId().equals(id)){
                return game;
            }
        }
        return null;
    }

    public synchronized void stopServer(){
        for(ClientThread client : clients) {
            client.setRunning(false);
        }

        this.running = false;

        if (serverSocket != null) {
            try {
                serverSocket.close();
            } catch (IOException e) {
                System.err.println("Error : " + e);
            }
        }
    }

    public List<Integer> listGames() {
        List<Integer> gameIds = new ArrayList<>();
        for(Game game : games){
            gameIds.add(game.getGameId());
        }
        return gameIds;
    }


    public static void main ( String [] args ) throws IOException {
       // new GameServer();   //commented this since we simulate the players connected to the server

         //---BONUS PART---
        ScheduleGenerator scheduleGenerator = new ScheduleGenerator();

        int n=5;    //number of players
        int p=2;    //number of games per day
        int d=3;    //number of days

        System.out.println("The schedule for a game with "+n+" players, "+p+" games per day and "+d+" days is:");
        scheduleGenerator.generateSchedule(n,p,d);
        System.out.println();
        scheduleGenerator.findSequence(scheduleGenerator.simulateGames(5),5);

    }


}