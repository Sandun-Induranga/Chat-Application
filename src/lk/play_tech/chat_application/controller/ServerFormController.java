package lk.play_tech.chat_application.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.sql.Array;
import java.util.Arrays;
import java.util.zip.DataFormatException;

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
    Socket socket;
    DataInputStream dataInputStream;
    DataOutputStream dataOutputStream;
    DataInputStream dataInputStream0;
    DataOutputStream dataOutputStream0;
    DataInputStream dataInputStream1;
    DataOutputStream dataOutputStream1;
    DataInputStream dataInputStream2;
    DataOutputStream dataOutputStream2;
    DataInputStream dataInputStream3;
    DataOutputStream dataOutputStream3;
    String message = "";
    int i = 0;
    public AnchorPane context = new AnchorPane();
//    https://www.codegrepper.com/code-examples/java/java+send+an+image+over+a+socket

    public void initialize() {
        Platform.setImplicitExit(false);
        msgContext.setContent(context);
        new Thread(() -> {
            try {
                serverSocket = new ServerSocket(PORT);
                accept = serverSocket.accept();
                System.out.println("Server Started");
                while (true) {
                    dataOutputStream = new DataOutputStream(accept.getOutputStream());
                    dataInputStream = new DataInputStream(accept.getInputStream());

                    message = "Server : " + dataInputStream.readUTF();
                    System.out.println(message);

                    if (message.equals("Server : exit")) {
                        accept = null;
                        return;
                    }
                    sendTextMessage(message);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            try {
                socket = new Socket("localhost", PORT);

                while (true) {
                    dataOutputStream0 = new DataOutputStream(socket.getOutputStream());
                    dataInputStream0 = new DataInputStream(socket.getInputStream());

                    message = dataInputStream0.readUTF();
                    System.out.println(message);
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            Label label = new Label(message);
                            label.setLayoutY(i);
                            context.getChildren().add(label);
                            i += 20;
                        }
                    });
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
        new Thread(() -> {
            try {
//                serverSocket1 = new ServerSocket(PORT1);
//                accept1 = serverSocket1.accept();
//                System.out.println("Client 1 Connected");

                accept1 = acceptConnection(serverSocket1, PORT1);

                dataOutputStream1 = new DataOutputStream(accept1.getOutputStream());
                dataInputStream1 = new DataInputStream(accept1.getInputStream());

                while (!message.equals("exit")) {
                    message = "Client 1 : " + dataInputStream1.readUTF();
                    System.out.println(message);
                    String typeName = dataInputStream1.getClass().getTypeName();
                    System.out.println(typeName);

                    if (message.equals("Client 1 : exit")) {
                        accept1 = null;
                        return;
                    }
                    sendTextMessage(message);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }).start();
        new Thread(() -> {
            try {
//                serverSocket2 = new ServerSocket(PORT2);
//                accept2 = serverSocket2.accept();
//                System.out.println("Client 2 Connected");

                accept2 = acceptConnection(serverSocket2,PORT2);

                dataOutputStream2 = new DataOutputStream(accept2.getOutputStream());
                dataInputStream2 = new DataInputStream(accept2.getInputStream());

                while (!message.equals("exit")) {
                    message = "Client 2 : " + dataInputStream2.readUTF();
                    System.out.println(message);

                    if (message.equals("Client 2 : exit")) {
                        accept2 = null;
                        return;
                    }
                    sendTextMessage(message);
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
                    sendTextMessage(message);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
        new Thread(() -> {
            try {
                while (true) {
                    ServerSocket imageSocket = new ServerSocket(13085);
                    Socket imgSocket = imageSocket.accept();
                    InputStream imgInputStream = imgSocket.getInputStream();
                    System.out.println("Reading: " + System.currentTimeMillis());

                    byte[] sizeAr = new byte[4];
                    imgInputStream.read(sizeAr);
                    int size = ByteBuffer.wrap(sizeAr).asIntBuffer().get();

                    byte[] imageAr = new byte[size];
                    imgInputStream.read(imageAr);

                    BufferedImage image = ImageIO.read(new ByteArrayInputStream(imageAr));

                    System.out.println("Received " + image.getHeight() + "x" + image.getWidth() + ": " + System.currentTimeMillis());
                    ImageIO.write(image, "jpg", new File("/media/sandu/0559F5C021740317/GDSE/Project_Zone/IdeaProjects/INP_Course_Work/src/lk/play_tech/chat_application/bo/test.jpg"));

                    imageSocket.close();
                    imgSocket.close();
                    imageSocket = null;
                    imageSocket = null;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void btnSendOnAction(ActionEvent actionEvent) throws IOException {
        dataOutputStream0.writeUTF(txtMessage.getText().trim());
        dataOutputStream0.flush();
    }

    public void btnImageChooserOnAction(ActionEvent actionEvent) {
    }

    public void btnExitOnAction(ActionEvent actionEvent) throws IOException {
        message = "Server Offline";
        sendTextMessage(message);
        System.exit(0);
    }

    private Socket acceptConnection(ServerSocket serverSocket, int port) throws IOException {
        serverSocket = new ServerSocket(port);
        System.out.println("Client 1 Connected");
        return serverSocket.accept();
    }

    private void sendTextMessage(String message) throws IOException {
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
}
