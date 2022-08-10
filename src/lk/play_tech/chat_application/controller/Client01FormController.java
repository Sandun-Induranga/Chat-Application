package lk.play_tech.chat_application.controller;

import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.Socket;
import java.nio.ByteBuffer;

public class Client01FormController {
    public ScrollPane msgContext;
    public TextField txtMessage;
    public AnchorPane context = new AnchorPane();

    final int PORT = 50000;
    public Label lblClient;
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

    public void initialize() {
        Platform.setImplicitExit(false);
        msgContext.setContent(context);
        msgContext.vvalueProperty().bind(context.heightProperty());
        lblClient.setText(LoginForm01Controller.name);

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

        new Thread(() -> {
            try {
                imgSocket = new Socket("localhost", PORT + 1);
                while (true) {
                    imgOutputStream = imgSocket.getOutputStream();
                    imgInputStream = imgSocket.getInputStream();

                    byte[] sizeAr = new byte[4];
                    imgInputStream.read(sizeAr);
                    int size = ByteBuffer.wrap(sizeAr).asIntBuffer().get();

                    byte[] imageAr = new byte[size];
                    imgInputStream.read(imageAr);

                    BufferedImage image = ImageIO.read(new ByteArrayInputStream(imageAr));

                    System.out.println("Received " + image.getHeight() + "x" + image.getWidth() + ": " + System.currentTimeMillis());
                    ImageIO.write(image, "jpg", new File("/media/sandu/0559F5C021740317/GDSE/Project_Zone/IdeaProjects/INP_Course_Work/src/lk/play_tech/chat_application/bo/test1.jpg"));
                    BufferedImage sendImage = ImageIO.read(new File("/media/sandu/0559F5C021740317/GDSE/Project_Zone/IdeaProjects/INP_Course_Work/src/lk/play_tech/chat_application/bo/test1.jpg"));

                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            Image img = SwingFXUtils.toFXImage(sendImage, null);
                            ImageView imageView = new ImageView(img);
                            imageView.setFitHeight(150);
                            imageView.setFitWidth(150);
                            imageView.setLayoutY(100);
                            context.getChildren().add(imageView);
                            i += 120;
                        }
                    });
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
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
        dataOutputStream.writeUTF(lblClient.getText() + " : " + txtMessage.getText().trim());
        dataOutputStream.flush();
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
        dataOutputStream.writeUTF("exit".trim());
        dataOutputStream.flush();
        System.exit(0);
    }


    public void btnOnAction(ActionEvent actionEvent) throws IOException {

        BufferedImage image = ImageIO.read(new File(path));

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ImageIO.write(image, "jpg", byteArrayOutputStream);

        byte[] size = ByteBuffer.allocate(4).putInt(byteArrayOutputStream.size()).array();
        imgOutputStream.write(size);
        imgOutputStream.write(byteArrayOutputStream.toByteArray());
        imgOutputStream.flush();
        System.out.println("Flushed: " + System.currentTimeMillis());
        System.out.println("Closing: " + System.currentTimeMillis());
    }

    public void btnEmojiOnAction(MouseEvent mouseEvent) {
        final Stage dialog = new Stage();
        dialog.initModality(Modality.NONE);
        dialog.initOwner(new Stage());
        VBox dialogVbox = new VBox(20);
        ImageView smile = new ImageView(new Image("lk/play_tech/chat_application/assets/smile.png"));
        smile.setFitWidth(30);
        smile.setFitHeight(30);
        dialogVbox.getChildren().add(smile);
        smile.setOnMouseClicked(event -> {
            txtMessage.setText(txtMessage.getText()+"☺");
        });
        Scene dialogScene = new Scene(dialogVbox, 300, 200);
        dialog.setScene(dialogScene);
        dialog.show();
    }
}
