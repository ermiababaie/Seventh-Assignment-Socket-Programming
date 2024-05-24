package Client;
import Server.Server;
import Server.ServerHandler;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.lang.System;
import java.net.ServerSocket;
import java.net.Socket;


public class Client {
    public static ArrayList<String> chat = new ArrayList<>();
    public static void main(String[] args) throws IOException {
        String userName;
        Scanner in = new Scanner(System.in);
        System.out.println("enter userName :");
        userName = in.next();
        System.out.println("welcome to chat.");

        Socket socket = new Socket("127.0.0.1", 5000);
        DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        ServerHandler serverHandler = new ServerHandler(socket);
        new Thread(serverHandler).start();

        String input;
        input = userName + " join the group.";
        outputStream.writeUTF(input);

        BufferedReader BR = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        while (true) {
            input = reader.readLine();
            chat.add(userName + ": " + input);
            outputStream.writeUTF(userName + ": " + input);
            if (input.equals("left")) {
                break;
            }
        }
    }
}