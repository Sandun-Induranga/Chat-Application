package lk.play_tech.chat_application.controller;

import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
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
    Socket socket;
    DataInputStream dataInputStream;
    DataOutputStream dataOutputStream;
    BufferedReader bufferedReader;
    String message = "";
    int i = 10;

    public void initialize() {
        Platform.setImplicitExit(false);
        msgContext.setContent(context);

        new Thread(() -> {
            try {
                socket = new Socket("localhost", PORT);

                while (true) {
                    dataOutputStream = new DataOutputStream(socket.getOutputStream());
                    dataInputStream = new DataInputStream(socket.getInputStream());
                    message = dataInputStream.readUTF();
//                    BufferedImage image = ImageIO.read(new File(dataInputStream.readUTF()));
//
//                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//                    ImageIO.write(image, "png", byteArrayOutputStream);
//
//                    byte[] size = ByteBuffer.allocate(4).putInt(byteArrayOutputStream.size()).array();
//                    dataOutputStream.write(size);
//                    dataOutputStream.write(byteArrayOutputStream.toByteArray());
//                    dataOutputStream.flush();


//                    byte[] sizeAr = new byte[4];
//                    dataInputStream.read(sizeAr);
//                    int sizeg = ByteBuffer.wrap(sizeAr).asIntBuffer().get();
//
//                    byte[] imageAr = new byte[sizeg];
//                    dataInputStream.read(imageAr);
//
//                    BufferedImage img = ImageIO.read(new ByteArrayInputStream(imageAr));
//
//                    System.out.println("Received " + image.getHeight() + "x" + image.getWidth() + ": " + System.currentTimeMillis());
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            Label label = new Label(message);
                            label.setLayoutY(i);
                            context.getChildren().add(label);
//                            Image img = SwingFXUtils.toFXImage(image, null);
//                            ImageView imageView = new ImageView(img);
//                            imageView.setFitHeight(150);
//                            imageView.setFitWidth(150);
//                            context.getChildren().add(imageView);
                            i += 20;
                        }
                    });
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

    public void btnImageChooserOnAction(ActionEvent actionEvent) throws IOException {
        // get the file selected
        FileChooser fil_chooser = new FileChooser();
        Stage stage = new Stage();
        File file = fil_chooser.showOpenDialog(stage);

        if (file != null) {
            dataOutputStream.writeUTF(file.getPath());
            System.out.println("selected");
            System.out.println(file.getPath());
        }
    }
}
