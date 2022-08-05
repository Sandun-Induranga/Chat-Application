package lk.play_tech.chat_application.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Client01FormController {
    public ScrollPane msgContext;
    public TextField txtMessage;
    public AnchorPane context;

    final int PORT = 5000;
    Socket socket;
    DataInputStream dataInputStream;
    DataOutputStream dataOutputStream;
    String message = "";

    public void initialize(){
        Platform.setImplicitExit(false);
        msgContext.setContent(context);

        new Thread(() -> {
            try {
                socket = new Socket("localhost",PORT);

                dataOutputStream = new DataOutputStream(socket.getOutputStream());
                dataInputStream = new DataInputStream(socket.getInputStream());

            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void btnSendOnAction(ActionEvent actionEvent) {
    }
}
