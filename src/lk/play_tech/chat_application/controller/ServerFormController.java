package lk.play_tech.chat_application.controller;

import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.play_tech.chat_application.model.Client;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.Socket;
import java.nio.ByteBuffer;

public class ServerFormController {
    public ScrollPane msgContext;
    public TextField txtMessage;

    final int PORT = 64000;
    final static int PORT1 = 50000;
    final int PORT2 = 60000;
    final int PORT3 = 65000;
    Socket socket;
    DataInputStream dataInputStream0;
    DataOutputStream dataOutputStream0;
    InputStream imgInputStream;
    OutputStream imgOutputStream;
    String message = "";
    int i = 0;
    public AnchorPane context = new AnchorPane();
    Client serverClient;
    Client client;
    Client client2;
    Client client3;
//    https://www.codegrepper.com/code-examples/java/java+send+an+image+over+a+socket

    public void initialize() {
        Platform.setImplicitExit(false);
        msgContext.setContent(context);
        msgContext.vvalueProperty().bind(context.heightProperty());
//        new Thread(() -> {
//            try {
//                Socket imgSocket = new Socket("localhost", PORT + 1);
//                while (true) {
//                    imgOutputStream = imgSocket.getOutputStream();
//                    imgInputStream = imgSocket.getInputStream();
//
//                    byte[] sizeAr = new byte[4];
//                    imgInputStream.read(sizeAr);
//                    int size = ByteBuffer.wrap(sizeAr).asIntBuffer().get();
//
//                    byte[] imageAr = new byte[size];
//                    imgInputStream.read(imageAr);
//
//                    BufferedImage image = ImageIO.read(new ByteArrayInputStream(imageAr));
//
//                    System.out.println("Received " + image.getHeight() + "x" + image.getWidth() + ": " + System.currentTimeMillis());
//                    ImageIO.write(image, "jpg", new File("/media/sandu/0559F5C021740317/GDSE/Project_Zone/IdeaProjects/INP_Course_Work/src/lk/play_tech/chat_application/bo/test4.jpg"));
//                    //BufferedImage sendImage = ImageIO.read(new File("/home/sandu/Downloads/296351115_1695464754171592_2138034279597586981_n.jpg"));
//
//                    Platform.runLater(new Runnable() {
//                        @Override
//                        public void run() {
//                            Image img = SwingFXUtils.toFXImage(image, null);
//                            ImageView imageView = new ImageView(img);
//                            imageView.setFitHeight(150);
//                            imageView.setFitWidth(150);
//                            imageView.setLayoutY(100);
//                            context.getChildren().add(imageView);
//                            i += 120;
//                        }
//                    });
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }).start();
        new Thread(() -> {
            serverClient = new Client(PORT);
            try {
                serverClient.setName("You ");
                serverClient.acceptConnection();
                serverClient.setInputAndOutput();
                processTextMessage(serverClient, serverClient.getDataInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
        new Thread(() -> {
            try {
                client = new Client(PORT1);
                client.setName("Client 01");
                client.acceptConnection();
                client.setInputAndOutput();
                processTextMessage(client, client.getDataInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
        new Thread(() -> {
            client2 = new Client(PORT2);
            try {
                client2.setName("Client 02");
                client2.acceptConnection();
                client2.setInputAndOutput();
                processTextMessage(client2, client2.getDataInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
        new Thread(() -> {
            client3 = new Client(PORT3);
            try {
                client3.setName("Client 03");
                client3.acceptConnection();
                client3.setInputAndOutput();
                processTextMessage(client3, client3.getDataInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
        new Thread(() -> {
            try {
                serverClient.acceptImgConnection(PORT + 1);
                serverClient.setImageInputAndOutput();
                processImage(serverClient.getImgInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
        new Thread(() -> {
            try {
                client.acceptImgConnection(PORT1 + 1);
                client.setImageInputAndOutput();
                processImage(client.getImgInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
        new Thread(() -> {
            try {
                client2.acceptImgConnection(PORT2 + 1);
                client2.setImageInputAndOutput();
                processImage(client2.getImgInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
        new Thread(() -> {
            try {
                client3.acceptImgConnection(PORT3 + 1);
                client3.setImageInputAndOutput();
                processImage(client3.getImgInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
        new Thread(() -> {
            try {
                socket = new Socket("localhost", PORT);
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        Label label = new Label("Server Started...");
                        label.setStyle("-fx-font-family: Ubuntu; -fx-font-size: 20px;");
                        label.setLayoutY(i);
                        context.getChildren().add(label);
                        i += 30;
                    }
                });
                while (true) {
                    dataOutputStream0 = new DataOutputStream(socket.getOutputStream());
                    dataInputStream0 = new DataInputStream(socket.getInputStream());

                    message = dataInputStream0.readUTF();
                    System.out.println(message);
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            Label label = new Label(message);
                            label.setStyle(" -fx-font-family: Ubuntu; -fx-font-size: 20px; -fx-background-color: #CDB4DB; -fx-text-fill: #5c5c5c");
                            label.setLayoutY(i);
                            context.getChildren().add(label);
                            i += 30;
                        }
                    });
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void btnSendOnAction(MouseEvent actionEvent) throws IOException {
        dataOutputStream0.writeUTF(txtMessage.getText().trim());
        dataOutputStream0.flush();
    }

    public void btnImageChooserOnAction(ActionEvent actionEvent) {
    }

    public void btnExitOnAction(MouseEvent actionEvent) throws IOException {
        message = "Server Offline";
        sendTextMessage(message);
        System.exit(0);
    }

    public void processTextMessage(Client client, DataInputStream dataInputStream) throws IOException {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Label label = new Label(client.getName()+" Joined");
                label.setStyle("-fx-font-family: Ubuntu; -fx-font-size: 20px;");
                label.setLayoutY(i);
                label.setLayoutX(50);
                context.getChildren().add(label);
                i += 30;
            }
        });
        while (!message.equals("exit")) {
            message = dataInputStream.readUTF();
            System.out.println(message);

            if (message.contains("exit")) {
                client.setAccept(null);
                return;
            }
            sendTextMessage(message);
        }
    }

    private void sendTextMessage(String message) throws IOException {
        if (serverClient.getAccept() != null) {
            serverClient.getDataOutputStream().writeUTF(message.trim());
            serverClient.getDataOutputStream().flush();
        }
        if (client.getAccept() != null) {
            client.getDataOutputStream().writeUTF(message.trim());
            client.getDataOutputStream().flush();
        }
        if (client2.getAccept() != null) {
            client2.getDataOutputStream().writeUTF(message.trim());
            client2.getDataOutputStream().flush();
        }
        if (client3.getAccept() != null) {
            client3.getDataOutputStream().writeUTF(message.trim());
            client3.getDataOutputStream().flush();
        }
    }

    private void processImage(InputStream inputStream) throws IOException {
        while (true) {
            byte[] sizeAr = new byte[4];
            inputStream.read(sizeAr);
            int size = ByteBuffer.wrap(sizeAr).asIntBuffer().get();
            byte[] imageAr = new byte[size];
            inputStream.read(imageAr);
            BufferedImage image = ImageIO.read(new ByteArrayInputStream(imageAr));

            System.out.println("Received " + image.getHeight() + "x" + image.getWidth() + ": " + System.currentTimeMillis());
            ImageIO.write(image, "jpg", new File("/media/sandu/0559F5C021740317/GDSE/Project_Zone/IdeaProjects/INP_Course_Work/src/lk/play_tech/chat_application/bo/test.jpg"));

            BufferedImage sendImage = ImageIO.read(new File("/media/sandu/0559F5C021740317/GDSE/Project_Zone/IdeaProjects/INP_Course_Work/src/lk/play_tech/chat_application/bo/test.jpg"));

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ImageIO.write(sendImage, "jpg", byteArrayOutputStream);

            byte[] sendSize = ByteBuffer.allocate(4).putInt(byteArrayOutputStream.size()).array();
            sendImgMessage(sendSize, byteArrayOutputStream);
            System.out.println("Flushed: " + System.currentTimeMillis());
            System.out.println("Closing: " + System.currentTimeMillis());
            sendImgMessage(sendSize, byteArrayOutputStream);
        }
    }

    private void sendImgMessage(byte[] sendSize, ByteArrayOutputStream byteArrayOutputStream) throws IOException {
        if (serverClient.getImgSocket() != null) {
            serverClient.getImgOutputStream().write(sendSize);
            serverClient.getImgOutputStream().write(byteArrayOutputStream.toByteArray());
            serverClient.getImgOutputStream().flush();
        }
        if (client.getImgSocket() != null) {
            client.getImgOutputStream().write(sendSize);
            client.getImgOutputStream().write(byteArrayOutputStream.toByteArray());
            client.getImgOutputStream().flush();
            System.out.println("done");
        }
        if (client2.getImgSocket() != null) {
            client2.getImgOutputStream().write(sendSize);
            client2.getImgOutputStream().write(byteArrayOutputStream.toByteArray());
            client2.getImgOutputStream().flush();
        }
        if (client3.getImgSocket() != null) {
            client3.getImgOutputStream().write(sendSize);
            client3.getImgOutputStream().write(byteArrayOutputStream.toByteArray());
            client3.getImgOutputStream().flush();
        }
    }
}
