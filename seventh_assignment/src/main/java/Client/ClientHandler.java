package Client;
import Server.Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.util.ArrayList;
import java.util.Scanner;
import java.lang.System;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.zip.CheckedInputStream;


public class ClientHandler implements Runnable {
    private Socket client;
    private static ArrayList<Socket> clients;
    private static ArrayList<String> chat = new ArrayList<>();
    private DataInputStream in;
    private DataOutputStream out;
    public ClientHandler(Socket client, ArrayList<Socket> clients) throws IOException{
        this.client = client;
        this.clients = clients;
        this.in = new DataInputStream(client.getInputStream());
        this.out = new DataOutputStream(client.getOutputStream());
    }
    public void run() {
        try {
            String request;
            while (true) {
                request = this.in.readUTF();
                if (request != null) {
                    if (request.endsWith("left")) {
                        sendM(request + " the group");
                        break;
                    }
                    else if (request.endsWith("join the group.")) {
                        sendM(request);
                        sendMM();
                    }
                    else {
                        sendM(request);
                    }
                }
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                in.close();
                out.close();
                client.close();
            }catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private void sendM(String msg) throws IOException {
        chat.add(msg);
        for (Socket clientt: clients) {
            if (clientt != client) {
                DataOutputStream outputStream = new DataOutputStream(clientt.getOutputStream());
                outputStream.writeUTF(msg);
            }
        }
    }
    private void sendMM() throws IOException {
        Socket clientt = client;
        for (String msg: chat) {
            DataOutputStream outputStream = new DataOutputStream(clientt.getOutputStream());
            outputStream.writeUTF(msg);
        }
    }

    public static ArrayList<Socket> getClients() {
        return clients;
    }
}