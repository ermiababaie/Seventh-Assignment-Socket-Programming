package Server;
import Client.ClientHandler;

import java.io.DataInputStream;
import java.io.DataOutputStream;
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
public class ServerHandler implements  Runnable {
    public static ArrayList<String> chat = new ArrayList<>();
    private DataInputStream in;
    public ServerHandler(Socket client) throws IOException {
        this.in = new DataInputStream(client.getInputStream());
    }
    public void run() {
        try {
            while (true) {
                String save = this.in.readUTF();
                System.out.println(save);
            }
        }catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}