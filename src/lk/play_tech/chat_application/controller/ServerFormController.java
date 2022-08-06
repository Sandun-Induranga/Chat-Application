package lk.play_tech.chat_application.controller;

import javafx.event.ActionEvent;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.sql.Array;

public class ServerFormController {
    final int PORT = 64000;
    final int PORT1 = 50000;
    final int PORT2 = 60000;
    final int PORT3 = 65000;
    public ScrollPane msgContext;
    public TextField txtMessage;
    ServerSocket serverSocket;
    ServerSocket serverSocket1;
    ServerSocket serverSocket2;
    ServerSocket serverSocket3;
    Socket accept;
    Socket accept1;
    Socket accept2;
    Socket accept3;
    DataInputStream dataInputStream;
    DataOutputStream dataOutputStream;
    DataInputStream dataInputStream1;
    DataOutputStream dataOutputStream1;
    DataInputStream dataInputStream2;
    DataOutputStream dataOutputStream2;
    DataInputStream dataInputStream3;
    DataOutputStream dataOutputStream3;
    String message = "";

    public void initialize() {
        new Thread(() -> {
            try {
                serverSocket = new ServerSocket(PORT);
                accept = serverSocket.accept();
                System.out.println("Server Started");

                dataOutputStream = new DataOutputStream(accept.getOutputStream());
                dataInputStream = new DataInputStream(accept.getInputStream());

                while (true) {
                    message = "Client 3 : " + dataInputStream3.readUTF();
                    System.out.println(message);

                    if (message.equals("Client 3 : exit")) {
                        accept = null;
                        return;
                    }

                    if (accept != null) {
                        dataOutputStream.writeUTF(message.trim());
                        dataOutputStream.flush();
                    }
                    if (accept1 != null) {
                        dataOutputStream1.writeUTF(message.trim());
                        dataOutputStream1.flush();
                    }
                    if (accept2 != null) {
                        dataOutputStream2.writeUTF(message.trim());
                        dataOutputStream2.flush();
                    }
                    if (accept3 != null) {
                        dataOutputStream3.writeUTF(message.trim());
                        dataOutputStream3.flush();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
        new Thread(() -> {
            try {
                serverSocket1 = new ServerSocket(PORT1);
                accept1 = serverSocket1.accept();
                System.out.println("Client 1 Connected");

                dataOutputStream1 = new DataOutputStream(accept1.getOutputStream());
                dataInputStream1 = new DataInputStream(accept1.getInputStream());
//                double d = Double.parseDouble(dataInputStream1.readUTF());
//                BufferedImage image = ImageIO.read(new File(dataInputStream1.readUTF()));
//
//                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//                ImageIO.write(image, "png", byteArrayOutputStream);
//
//                byte[] size = ByteBuffer.allocate(4).putInt(byteArrayOutputStream.size()).array();
//                dataOutputStream1.write(size);
//                dataOutputStream1.write(byteArrayOutputStream.toByteArray());
////                        dataOutputStream1.flush();
//                dataOutputStream2.write(size);
//                dataOutputStream2.write(byteArrayOutputStream.toByteArray());
////                        dataOutputStream2.flush();
//                dataOutputStream1.flush();

                while (!message.equals("exit")) {
                    message = "Client 1 : " + dataInputStream1.readUTF();
                    System.out.println(message);

                    if (message.equals("Client 1 : exit")) {
                        accept1 = null;
                        return;
                    }

                    if (accept != null) {
                        dataOutputStream.writeUTF(message.trim());
                        dataOutputStream.flush();
                    }
                    if (accept1 != null) {
                        dataOutputStream1.writeUTF(message.trim());
                        dataOutputStream1.flush();
                    }
                    if (accept2 != null) {
                        dataOutputStream2.writeUTF(message.trim());
                        dataOutputStream2.flush();
                    }
                    if (accept3 != null) {
                        dataOutputStream3.writeUTF(message.trim());
                        dataOutputStream3.flush();
                    }
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

                    if (message.equals("Client 2 : exit")) {
                        accept2 = null;
                        return;
                    }

                    if (accept != null) {
                        dataOutputStream.writeUTF(message.trim());
                        dataOutputStream.flush();
                    }
                    if (accept1 != null) {
                        dataOutputStream1.writeUTF(message.trim());
                        dataOutputStream1.flush();
                    }
                    if (accept2 != null) {
                        dataOutputStream2.writeUTF(message.trim());
                        dataOutputStream2.flush();
                    }
                    if (accept3 != null) {
                        dataOutputStream3.writeUTF(message.trim());
                        dataOutputStream3.flush();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
        new Thread(() -> {
            try {
                serverSocket3 = new ServerSocket(PORT3);
                accept3 = serverSocket3.accept();
                System.out.println("Client 3 Connected");

                dataOutputStream3 = new DataOutputStream(accept3.getOutputStream());
                dataInputStream3 = new DataInputStream(accept3.getInputStream());

                while (true) {
                    message = "Client 3 : " + dataInputStream3.readUTF();
                    System.out.println(message);

                    if (message.equals("Client 3 : exit")) {
                        accept3 = null;
                        return;
                    }

                    if (accept != null) {
                        dataOutputStream.writeUTF(message.trim());
                        dataOutputStream.flush();
                    }
                    if (accept1 != null) {
                        dataOutputStream1.writeUTF(message.trim());
                        dataOutputStream1.flush();
                    }
                    if (accept2 != null) {
                        dataOutputStream2.writeUTF(message.trim());
                        dataOutputStream2.flush();
                    }
                    if (accept3 != null) {
                        dataOutputStream3.writeUTF(message.trim());
                        dataOutputStream3.flush();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void btnSendOnAction(ActionEvent actionEvent) throws IOException {
        dataOutputStream.writeUTF(txtMessage.getText().trim());
        dataOutputStream.flush();
    }

    public void btnImageChooserOnAction(ActionEvent actionEvent) {
    }

    public void btnExitOnAction(ActionEvent actionEvent) throws IOException {
        if (accept1 != null) {
            dataOutputStream1.writeUTF("Server Offline".trim());
            dataOutputStream1.flush();
        }
        if (accept2 != null) {
            dataOutputStream2.writeUTF("Server Offline".trim());
            dataOutputStream2.flush();
        }
        if (accept3 != null) {
            dataOutputStream3.writeUTF("Server Offline".trim());
            dataOutputStream3.flush();
        }
        System.exit(0);
    }
}
