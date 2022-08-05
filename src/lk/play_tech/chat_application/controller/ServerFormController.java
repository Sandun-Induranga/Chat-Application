package lk.play_tech.chat_application.controller;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;

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
                    message = "Client 1 : " + dataInputStream1.readUTF();
                    System.out.println(message);
                    BufferedImage image = ImageIO.read(new File("/home/sandu/Pictures/Screenshots/Screenshot from 2022-07-19 20-43-10.png"));

                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    ImageIO.write(image, "png", byteArrayOutputStream);

                    byte[] size = ByteBuffer.allocate(4).putInt(byteArrayOutputStream.size()).array();
                    dataOutputStream1.write(size);
                    dataOutputStream1.write(byteArrayOutputStream.toByteArray());
                    dataOutputStream1.flush();

//                    dataOutputStream1.writeUTF(message.trim());
//                    dataOutputStream2.writeUTF(message.trim());
//                    dataOutputStream1.flush();
//                    dataOutputStream2.flush();
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
                    message = "Client 2 : " + dataInputStream2.readUTF();
                    System.out.println(message);

                    dataOutputStream1.writeUTF(message.trim());
                    dataOutputStream2.writeUTF(message.trim());
                    dataOutputStream1.flush();
                    dataOutputStream2.flush();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
