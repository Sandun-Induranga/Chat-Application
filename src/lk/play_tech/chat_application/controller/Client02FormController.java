package lk.play_tech.chat_application.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

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
    Socket socket;
    DataInputStream dataInputStream;
    DataOutputStream dataOutputStream;
    String message = "";
    int i = 10;

    public void initialize(){
        Platform.setImplicitExit(false);
        msgContext.setContent(context);

        new Thread(() -> {
            try {
                socket = new Socket("localhost",PORT);

                while (true){
                    dataOutputStream = new DataOutputStream(socket.getOutputStream());
                    dataInputStream = new DataInputStream(socket.getInputStream());

                    message = dataInputStream.readUTF();
                    System.out.println(message);
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            Label label = new Label(message);
                            label.setLayoutY(i);
                            context.getChildren().add(label);
                            i+=20;
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

    public void btnExitOnAction(ActionEvent actionEvent) throws IOException {
        dataOutputStream.writeUTF("exit".trim());
        dataOutputStream.flush();
        System.exit(0);
    }

    public void btnOnAction(ActionEvent actionEvent) throws IOException {
        Socket imgSocket = new Socket("localhost", 13085);
        OutputStream outputStream = imgSocket.getOutputStream();

        BufferedImage image = ImageIO.read(new File("/home/sandu/Downloads/296351115_1695464754171592_2138034279597586981_n.jpg"));

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ImageIO.write(image, "jpg", byteArrayOutputStream);

        byte[] size = ByteBuffer.allocate(4).putInt(byteArrayOutputStream.size()).array();
        outputStream.write(size);
        outputStream.write(byteArrayOutputStream.toByteArray());
        outputStream.flush();
        System.out.println("Flushed: " + System.currentTimeMillis());
        System.out.println("Closing: " + System.currentTimeMillis());
        imgSocket.close();
    }
}
