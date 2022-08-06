package lk.play_tech.chat_application.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Client03FormController {
    public ScrollPane msgContext;
    public TextField txtMessage;
    public AnchorPane context = new AnchorPane();

    final int PORT = 65000;
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
    }
}
