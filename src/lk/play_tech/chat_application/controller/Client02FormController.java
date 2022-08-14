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
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.Socket;
import java.nio.ByteBuffer;

public class Client02FormController {
    public ScrollPane msgContext;
    public TextField txtMessage;
    public AnchorPane context = new AnchorPane();

    final int PORT = 60000;
    public Label lblClient;
    public AnchorPane emoji;
    Socket socket;
    Socket imgSocket;
    DataInputStream dataInputStream;
    DataOutputStream dataOutputStream;
    String message = "";
    int i = 10;
    String path = "";
    public static boolean isImageChoose = false;
    ObjectOutputStream oos;
    ObjectInputStream ois;
    File file;
    OutputStream imgOutputStream;
    InputStream imgInputStream;
    public static String name;
    boolean isUsed = false;

    public void initialize() {
        Platform.setImplicitExit(false);
        msgContext.setContent(context);
        msgContext.vvalueProperty().bind(context.heightProperty());
        msgContext.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        msgContext.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        lblClient.setText(LoginForm02Controller.name);
        name = lblClient.getText();

        new Thread(() -> {
            try {
                socket = new Socket("localhost", PORT);

                while (true) {
                    dataOutputStream = new DataOutputStream(socket.getOutputStream());
                    dataInputStream = new DataInputStream(socket.getInputStream());
                    message = dataInputStream.readUTF();
                    System.out.println(message);

                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            if (message.startsWith("/")) {
                                BufferedImage sendImage = null;
                                try {
                                    sendImage = ImageIO.read(new File(message));
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                Image img = SwingFXUtils.toFXImage(sendImage, null);
                                ImageView imageView = new ImageView(img);
                                imageView.setFitHeight(150);
                                imageView.setFitWidth(150);
                                imageView.setLayoutY(i);
                                context.getChildren().add(imageView);
                                i += 120;

                            }else if (message.startsWith(LoginForm02Controller.name)){
                                message = message.replace(LoginForm02Controller.name,"You");
                                Label label = new Label(message);
                                label.setStyle(" -fx-font-family: Ubuntu; -fx-font-size: 20px; -fx-background-color: #85b6ff; -fx-text-fill: #5c5c5c");
                                label.setLayoutY(i);
                                context.getChildren().add(label);
                            }else {
                                Label label = new Label(message);
                                label.setStyle(" -fx-font-family: Ubuntu; -fx-font-size: 20px; -fx-background-color: #CDB4DB; -fx-text-fill: #5c5c5c");
                                label.setLayoutY(i);
                                context.getChildren().add(label);
                            }
                            i += 30;
                        }
                    });
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

//        new Thread(() -> {
//            try {
//                imgSocket = new Socket("localhost", PORT + 1);
//                while (true) {
//                    imgOutputStream = imgSocket.getOutputStream();
//                    imgInputStream = imgSocket.getInputStream();
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
//                    ImageIO.write(image, "jpg", new File("/media/sandu/0559F5C021740317/GDSE/Project_Zone/IdeaProjects/INP_Course_Work/src/lk/play_tech/chat_application/bo/test2.jpg"));
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
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }).start();
    }

    public void btnSendOnAction(MouseEvent actionEvent) throws IOException {
////        if (isImageChoose){
//            BufferedImage image = ImageIO.read(new File(path));
//
//            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//            ImageIO.write(image, "png", byteArrayOutputStream);
//
//            byte[] size = ByteBuffer.allocate(4).putInt(byteArrayOutputStream.size()).array();
//            dataOutputStream.write(size);
//            dataOutputStream.write(byteArrayOutputStream.toByteArray());
//            dataOutputStream.flush();
////        }else {
////
////        }
        if (isImageChoose){
            dataOutputStream.writeUTF(path.trim());
            dataOutputStream.flush();
            isImageChoose = false;
        }else {
            dataOutputStream.writeUTF(lblClient.getText() + " : " + txtMessage.getText().trim());
            dataOutputStream.flush();
        }
        txtMessage.clear();
    }

    public void btnImageChooserOnAction(ActionEvent actionEvent) throws IOException {
        // get the file selected
        FileChooser chooser = new FileChooser();
        Stage stage = new Stage();
        file = chooser.showOpenDialog(stage);

        if (file != null) {
//            dataOutputStream.writeUTF(file.getPath());
            path = file.getPath();
            System.out.println("selected");
            System.out.println(file.getPath());
            isImageChoose = true;
        }
    }

    public void btnExitOnAction(MouseEvent actionEvent) throws IOException {
        if (socket!=null){
            dataOutputStream.writeUTF("exit".trim());
            dataOutputStream.flush();
            System.exit(0);
        }
        System.exit(0);
    }


    public void btnOnAction(ActionEvent actionEvent) throws IOException {
//
//        BufferedImage image = ImageIO.read(new File(path));
//
//        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//        ImageIO.write(image, "jpg", byteArrayOutputStream);
//
//        byte[] size = ByteBuffer.allocate(4).putInt(byteArrayOutputStream.size()).array();
//        imgOutputStream.write(size);
//        imgOutputStream.write(byteArrayOutputStream.toByteArray());
//        imgOutputStream.flush();
//        System.out.println("Flushed: " + System.currentTimeMillis());
//        System.out.println("Closing: " + System.currentTimeMillis());
        dataOutputStream.writeUTF(path.trim());
        dataOutputStream.flush();
    }

    public void btnEmojiOnAction(MouseEvent mouseEvent) {
        if (isUsed) {
            emoji.getChildren().clear();
            isUsed = false;
            return;
        }
        isUsed = true;
        VBox dialogVbox = new VBox(20);
        ImageView smile = new ImageView(new Image("lk/play_tech/chat_application/assets/smile.png"));
        smile.setFitWidth(30);
        smile.setFitHeight(30);
        dialogVbox.getChildren().add(smile);
        ImageView heart = new ImageView(new Image("lk/play_tech/chat_application/assets/heart.png"));
        heart.setFitWidth(30);
        heart.setFitHeight(30);
        dialogVbox.getChildren().add(heart);
        ImageView sadFace = new ImageView(new Image("lk/play_tech/chat_application/assets/sad-face.png"));
        sadFace.setFitWidth(30);
        sadFace.setFitHeight(30);
        dialogVbox.getChildren().add(sadFace);
        smile.setOnMouseClicked(event -> {
            txtMessage.setText(txtMessage.getText() + "☺");
        });
        heart.setOnMouseClicked(event -> {
            txtMessage.setText(txtMessage.getText() + "♥");
        });
        sadFace.setOnMouseClicked(event -> {
            txtMessage.setText(txtMessage.getText() + "☹");
        });
        emoji.getChildren().add(dialogVbox);
    }
}
