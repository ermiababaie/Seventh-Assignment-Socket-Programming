package Client;
import Server.Server;
import Server.ServerHandler;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
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
        while (true) {
            System.out.println("select one: 0)close 1)chat 2)download");
            int task = in.nextInt();
            while (task < 0 || task > 2) {
                System.out.println("select one: 0)close 1)chat 2)download");
                task = in.nextInt();
            }
            if (task == 0) {
                break;
            }
            else if (task == 1) {
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
            } else {
                File folder2 = new File("src/main/java/Client/" + userName + "Downloads");
                if (!folder2.exists())
                    folder2.mkdir();
                File[] fList;
                while (true) {
                    String fPath = "src/main/java/Server/data";
                    File folder = new File(fPath);
                    fList = folder.listFiles();
                    assert fList != null;
                    int cnt = 1;
                    for (File f : fList)
                        System.out.println((cnt++ + ": " + f.getName()));
                    System.out.println("select one file: ");
                    int num = in.nextInt();
                    while (num < 0 || num > fList.length) {
                        System.out.println("select one file: ");
                        num = in.nextInt();
                    }
                    if (num == 0) {
                        break;
                    }
                    File req = fList[num - 1];
                    File file = new File("src/main/java/Client/" + userName + "Downloads/" + req.getName());
                    if (file.createNewFile())
                        System.out.println("file downloaded in: " + file.getPath());
                    String textFile = "";
                    Scanner myReader = new Scanner(req);
                    while (myReader.hasNext()) {
                        textFile = textFile + myReader.nextLine() + "\n";
                    }
                    FileWriter fw = new FileWriter(file);
                    fw.append(textFile);
                    fw.close();
                }
            }
        }
    }
}