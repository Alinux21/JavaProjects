package org.example;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class GameClient {
    private static volatile boolean server_stopped = false;
    private final Scanner scanner = new Scanner(System.in);
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private boolean alreadyInGame = false;

    public static void main(String[] args){
        String serverAddress = "127.0.0.1";
        int PORT = 8100;


        GameClient client = new GameClient();

        try {

            client.socket = new Socket(serverAddress, PORT);
            client.out = new PrintWriter(client.socket.getOutputStream(), true);
            client.in = new BufferedReader(
                    new InputStreamReader(client.socket.getInputStream()));

            //new thread that continuously reads from the server
            new Thread(() -> {
                try {
                    while (true) {
                        char[] buffer = new char[2048]; //buffer that holds the characters
                        int numRead = client.in.read(buffer); //read the characters from the server
                        if (numRead == -1) {
                            server_stopped = true;
                            System.out.println("Server closed connection");
                            break;
                        }
                        String response = new String(buffer, 0, numRead); //convert the buffer to a string
                        System.out.println(response);
                    }
                } catch (IOException e) {
                    System.out.println("Error reading from server: " + e.getMessage());
                }
            }).start();

            while (!server_stopped) {

                    String request = client.scanner.nextLine();

                    if (request.equals("exit")) {
                        System.out.println("Exiting client...");
                        client.out.println(request);
                        break;
                    } else if ((request.equals("create game") || request.startsWith("join game")) && !client.alreadyInGame) {
                        client.alreadyInGame = true;
                    } else if (request.equals("create game") || request.equals("join game")) {
                        System.out.println("You are already in a game!");
                        continue;
                    } else if (request.startsWith("submit move")) {
                        String move = request.substring("submit move".length()).trim(); //extract the move e.g("A5")
                        if (!move.matches("^[A-Ja-j]([1-9]|10)$")) {
                            System.out.println("Invalid move. Please try again.");
                            continue;
                        }
                    }else if (request.equals("stop") || request.equals("list games")) {
                        client.out.println(request);
                        client.out.flush();
                        continue;
                    }


                client.out.println(request);
                client.out.flush();
            }

            client.in.close();
            client.out.close();
            client.socket.close();


        }catch (UnknownHostException e) {
            System.err.println("No server listening... " + e);
        } catch (IOException e) {
            System.err.println("Error: " + e);
        }
    }
}