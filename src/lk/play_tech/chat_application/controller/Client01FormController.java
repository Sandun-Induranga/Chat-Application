package lk.play_tech.chat_application.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import lk.play_tech.chat_application.bo.ClientHandler;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.ArrayList;

public class Client01FormController {
    public ScrollPane msgContext;
    public TextField txtMessage;
    public AnchorPane context = new AnchorPane();
    private static ArrayList<ClientHandler> clients = new ArrayList<>();

    final int PORT = 50000;
    Socket socket;
    DataInputStream dataInputStream;
    DataOutputStream dataOutputStream;
    ImageInputStream imageInputStream;
    ImageOutputStream imageOutputStream;
    BufferedReader bufferedReader;
    String message = "";
    int i = 10;
    String path = "";
    public static boolean isImageChoose = false;
    ObjectOutputStream oos;
    ObjectInputStream ois;
    File file;

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
                    System.out.println(message);

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
        dataOutputStream.writeUTF(txtMessage.getText().trim());
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

    public void btnExitOnAction(ActionEvent actionEvent) throws IOException {
        dataOutputStream.writeUTF("exit".trim());
        dataOutputStream.flush();
        System.exit(0);
    }

    public void btnOnAction(ActionEvent actionEvent) throws IOException {
        BufferedImage image = ImageIO.read(new File(path));

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ImageIO.write(image, "png", byteArrayOutputStream);
        byteArrayOutputStream.flush();

        if (file != null) {

//            sendingobject(file.getName(), true, file);

//            try {
//                Socket socket = null;
//
//                socket = new Socket("localhost", 4444);
//
//                // Get the size of the file
//                long length = file.length();
//                byte[] bytes = new byte[(int) length];
//                InputStream in = new FileInputStream(file);
//                OutputStream out = socket.getOutputStream();
//
//                int count;
//                while ((count = in.read(bytes)) > 0) {
//                    out.write(bytes, 0, count);
//                }
//                out.close();
//                in.close();
//                socket.close();
//
//            } catch (IOException e) {
//                System.out.println(e.getMessage());
//            }
        }

//        byte[] size = ByteBuffer.allocate(4).putInt(byteArrayOutputStream.size()).array();
//        dataOutputStream.write(size);
//        dataOutputStream.write(byteArrayOutputStream.toByteArray());
//        dataOutputStream.flush();
    }
}
