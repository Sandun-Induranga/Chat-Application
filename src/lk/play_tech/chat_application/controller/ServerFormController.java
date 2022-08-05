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
    String message = "";
    String message2 = "";

    public void initialize() {
        new Thread(() -> {
            try {
                serverSocket = new ServerSocket(PORT);
                serverSocket2 = new ServerSocket(PORT2);
                System.out.println("Server Started..");
                accept = serverSocket.accept();
                System.out.println("Client 1 Connected");
                // TODO: 2022-08-05
                accept2 = serverSocket2.accept();
                System.out.println("Client 2 Connected");
            } catch (IOException e) {
                e.printStackTrace();
            }

        }).start();
        new Thread(() -> {
            try {
                dataOutputStream1 = accept != null ? new DataOutputStream(accept.getOutputStream()) : null;
                dataInputStream1 = accept != null ? new DataInputStream(accept.getInputStream()) : null;

                dataOutputStream2 = accept2 != null ? new DataOutputStream(accept2.getOutputStream()) : null;
                dataInputStream2 = accept2 != null ? new DataInputStream(accept2.getInputStream()) : null;

                dataInputStream = dataInputStream1 != null ? dataInputStream1 : dataInputStream2;
                dataOutputStream = dataOutputStream1 != null ? dataOutputStream1 : dataOutputStream2;

                while (true) {
                    message = dataInputStream.readUTF();
                    System.out.println(message);
                    dataOutputStream.writeUTF(message.trim());
                    dataOutputStream.flush();
                    dataOutputStream1.flush();
                    dataOutputStream2.flush();
                    dataInputStream1 = null;
                    dataOutputStream2 = null;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
