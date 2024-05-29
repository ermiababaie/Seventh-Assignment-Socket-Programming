package Server;
import Client.ClientHandler;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;
import java.lang.System;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// Server Class
public class Server {
    private static final int port = 5000;
    private static ArrayList<Socket> clients = new ArrayList<>();
    private ServerSocket server = null;
    private static ExecutorService pool = Executors.newFixedThreadPool(10);

    public static void main(String args[]) {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("server started.");
            try {
                File file = new File("chat.txt");
                if (file.createNewFile()) {
                    System.out.println("file created.");
                }
            }catch (IOException e) {
                System.out.println(e.getMessage());
            }
            while (true) {
                Socket client = serverSocket.accept();
                clients.add(client);
                ClientHandler clientHandler = new ClientHandler(client, clients);
                System.out.println(clients.size());
                pool.execute(clientHandler);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static ArrayList<Socket> getClients() {
        return clients;
    }
}