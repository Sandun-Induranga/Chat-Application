package lk.play_tech.chat_application.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class Client01FormController {
    public ScrollPane msgContext;
    public TextField txtMessage;
    public AnchorPane context;

    public void initialize(){
        Platform.setImplicitExit(false);
        msgContext.setContent(context);

        new Thread(() -> {

        }).start();
    }

    public void btnSendOnAction(ActionEvent actionEvent) {
    }
}
