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
import lk.play_tech.chat_application.model.Client;

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
    final int PORT11 = 51000;
    final int PORT22 = 62000;
    final int PORT33 = 63000;
    public ScrollPane msgContext;
    public TextField txtMessage;
    ServerSocket serverSocket;
    ServerSocket serverSocket1;
    ServerSocket imageSocket;
    Socket accept;
    Socket accept1;
    Socket accept2;
    Socket accept3;
    Socket acceptImg;
    Socket accept1Img;
    Socket accept2Img;
    Socket accept3Img;
    Socket socket;
    DataInputStream dataInputStream;
    DataOutputStream dataOutputStream;
    InputStream imgInputStream;
    OutputStream imgOutputStream;
    DataInputStream dataInputStream0;
    DataOutputStream dataOutputStream0;
    InputStream imgInputStream0;
    OutputStream imgOutputStream0;
    DataInputStream dataInputStream1;
    DataOutputStream dataOutputStream1;
    InputStream imgInputStream1;
    OutputStream imgOutputStream1;
    DataInputStream dataInputStream2;
    DataOutputStream dataOutputStream2;
    InputStream imgInputStream2;
    OutputStream imgOutputStream2;
    DataInputStream dataInputStream3;
    DataOutputStream dataOutputStream3;
    InputStream imgInputStream3;
    OutputStream imgOutputStream3;
    String message = "";
    int i = 0;
    public AnchorPane context = new AnchorPane();
    Client client;
    Client client2;
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
            client = new Client(PORT1);
            try {
                client.acceptConnection();
                client.setInputAndOutput();
                client.processTextMessage();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
        new Thread(() -> {
            client2 = new Client(PORT2);
            try {
                client2.acceptConnection();
                client2.setInputAndOutput();
                client2.processTextMessage();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
//        new Thread(() -> {
//            try {
//                accept1 = acceptConnection(PORT1);
//                System.out.println("Client 1 Connected");
//
//                dataOutputStream1 = new DataOutputStream(accept1.getOutputStream());
//                dataInputStream1 = new DataInputStream(accept1.getInputStream());
//
//                while (!message.equals("exit")) {
//                    message = "Client 1 : " + dataInputStream1.readUTF();
//                    System.out.println(message);
//                    String typeName = dataInputStream1.getClass().getTypeName();
//                    System.out.println(typeName);
//
//                    if (message.equals("Client 1 : exit")) {
//                        accept1 = null;
//                        return;
//                    }
//                    sendTextMessage(message);
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//        }).start();
//
//        // image
//        new Thread(() -> {
//            try {
//                accept1Img = acceptImgConnection(PORT11);
//
//                imgInputStream1 = accept1Img.getInputStream();
//                imgOutputStream1 = accept1Img.getOutputStream();
//
//                while (true) {
////                    message = "Client 1 : " + dataInputStream1.readUTF();
////                    System.out.println(message);
////                    String typeName = dataInputStream1.getClass().getTypeName();
////                    System.out.println(typeName);
////
////                    if (message.equals("Client 1 : exit")) {
////                        accept1 = null;
////                        return;
////                    }
////                    sendTextMessage(message);
//                    processImage(imgInputStream1);
//                }
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }).start();
//        new Thread(() -> {
//            try {
//                accept2 = acceptConnection(PORT2);
//                accept2Img = acceptImgConnection(PORT22);
//                System.out.println("Client 1 Connected");
//
//                dataOutputStream2 = new DataOutputStream(accept2.getOutputStream());
//                dataInputStream2 = new DataInputStream(accept2.getInputStream());
//
//                imgOutputStream2 = accept2Img.getOutputStream();
//                imgInputStream2 = accept2Img.getInputStream();
//
//                while (!message.equals("exit")) {
//                    message = "Client 2 : " + dataInputStream2.readUTF();
//                    System.out.println(message);
//
//                    if (message.equals("Client 2 : exit")) {
//                        accept2 = null;
//                        return;
//                    }
//                    sendTextMessage(message);
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }).start();
//        new Thread(() -> {
//            try {
//                accept2Img = acceptImgConnection(PORT22);
//
//                imgInputStream2 = accept1Img.getInputStream();
//                imgOutputStream2 = accept1Img.getOutputStream();
//
//                while (true) {
//                    message = "Client 1 : " + dataInputStream2.readUTF();
//                    System.out.println(message);
//                    String typeName = dataInputStream2.getClass().getTypeName();
//                    System.out.println(typeName);
//
//                    if (message.equals("Client 1 : exit")) {
//                        accept1 = null;
//                        return;
//                    }
//                    sendTextMessage(message);
//                    processImage(imgInputStream2);
//                }
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }).start();
//        new Thread(() -> {
//            try {
//                // TODO: 2022-08-07
//                accept3 = acceptConnection(PORT3);
//                accept3Img = acceptImgConnection(PORT33);
//
//                dataOutputStream3 = new DataOutputStream(accept3.getOutputStream());
//                dataInputStream3 = new DataInputStream(accept3.getInputStream());
//
//                imgOutputStream3 = accept3Img.getOutputStream();
//                imgInputStream3 = accept3Img.getInputStream();
//
//                while (true) {
//                    message = "Client 3 : " + dataInputStream3.readUTF();
//                    System.out.println(message);
//
//                    if (message.equals("Client 3 : exit")) {
//                        accept3 = null;
//                        return;
//                    }
//                    sendTextMessage(message);
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }).start();
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

    private Socket acceptConnection(int port) throws IOException {
        serverSocket1 = new ServerSocket(port);
        return serverSocket1.accept();
    }

    private Socket acceptImgConnection(int port) throws IOException {
        imageSocket = new ServerSocket(port);
        return imageSocket.accept();
    }

    private void sendTextMessage(String message) throws IOException {
        if (accept != null) {
            dataOutputStream.writeUTF(message.trim());
            dataOutputStream.flush();
        }
        if (client.getAccept() != null) {
            client.getDataOutputStream().writeUTF(message.trim());
            client.getDataOutputStream().flush();
        }
        if (client2.getAccept() != null) {
            client2.getDataOutputStream().writeUTF(message.trim());
            client2.getDataOutputStream().flush();
        }
//        if (accept3 != null) {
//            dataOutputStream3.writeUTF(message.trim());
//            dataOutputStream3.flush();
//        }
    }

    private void processImage(InputStream inputStream) throws IOException {
        if (inputStream!=null){
            byte[] sizeAr = new byte[4];
            inputStream.read(sizeAr);
            int size = ByteBuffer.wrap(sizeAr).asIntBuffer().get();

            byte[] imageAr = new byte[size];
            inputStream.read(imageAr);
            BufferedImage image = ImageIO.read(new ByteArrayInputStream(imageAr));

            System.out.println("Received " + image.getHeight() + "x" + image.getWidth() + ": " + System.currentTimeMillis());
            ImageIO.write(image, "jpg", new File("/media/sandu/0559F5C021740317/GDSE/Project_Zone/IdeaProjects/INP_Course_Work/src/lk/play_tech/chat_application/bo/test1.jpg"));

            BufferedImage sendImage = ImageIO.read(new File("/home/sandu/Downloads/296351115_1695464754171592_2138034279597586981_n.jpg"));

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ImageIO.write(sendImage, "jpg", byteArrayOutputStream);

            byte[] sendSize = ByteBuffer.allocate(4).putInt(byteArrayOutputStream.size()).array();
            sendImgMessage(sendSize, byteArrayOutputStream);
            System.out.println("Flushed: " + System.currentTimeMillis());
            System.out.println("Closing: " + System.currentTimeMillis());
            sendImgMessage(sendSize,byteArrayOutputStream);
        }
    }

    private void sendImgMessage(byte[] sendSize, ByteArrayOutputStream byteArrayOutputStream) throws IOException {
        if (acceptImg != null) {
            imgOutputStream.write(sendSize);
            imgOutputStream.write(byteArrayOutputStream.toByteArray());
            imgOutputStream.flush();
        }
        if (accept1Img != null) {
            imgOutputStream1.write(sendSize);
            imgOutputStream1.write(byteArrayOutputStream.toByteArray());
            imgOutputStream1.flush();
            System.out.println("done");
        }
        if (accept2Img != null) {
            imgOutputStream2.write(sendSize);
            imgOutputStream2.write(byteArrayOutputStream.toByteArray());
            imgOutputStream2.flush();
        }
        if (accept3Img != null) {
            imgOutputStream3.write(sendSize);
            imgOutputStream3.write(byteArrayOutputStream.toByteArray());
            imgOutputStream3.flush();
        }
    }
}
