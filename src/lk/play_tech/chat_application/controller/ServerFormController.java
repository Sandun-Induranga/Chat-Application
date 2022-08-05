package lk.play_tech.chat_application.controller;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerFormController {
    final int PORT = 50000;
    final int PORT2 = 60000;
    ServerSocket serverSocket;
    ServerSocket serverSocket2;
    Socket accept;
    Socket accept2;
    DataInputStream dataInputStream;
    DataOutputStream dataOutputStream;
    DataInputStream dataInputStream1;
    DataOutputStream dataOutputStream1;
    DataInputStream dataInputStream2;
    DataOutputStream dataOutputStream2;
    String message = "none";
    String message2 = "";

    public void initialize() {
        new Thread(() -> {
            try {
                serverSocket = new ServerSocket(PORT);
                System.out.println("Server Started..");
                accept = serverSocket.accept();
                System.out.println("Client 1 Connected");
                dataOutputStream1 = new DataOutputStream(accept.getOutputStream());
                dataInputStream1 = new DataInputStream(accept.getInputStream());

                while (!message.equals("exit")) {
                    message = dataInputStream1.readUTF();
                    System.out.println(message);

                    dataOutputStream1.writeUTF(message.trim());
                    dataOutputStream1.flush();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }).start();
        new Thread(() -> {
            try {
                serverSocket2 = new ServerSocket(PORT2);
                accept2 = serverSocket2.accept();
                System.out.println("Client 2 Connected");

                dataOutputStream2 = new DataOutputStream(accept2.getOutputStream());
                dataInputStream2 = new DataInputStream(accept2.getInputStream());

                while (!message.equals("exit")) {
                    message = dataInputStream2.readUTF();
                    System.out.println(message);

                    dataOutputStream2.writeUTF(message.trim());
                    dataOutputStream2.flush();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
