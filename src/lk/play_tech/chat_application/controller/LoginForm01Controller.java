package lk.play_tech.chat_application.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginForm01Controller {
    public TextField txtName;
    public AnchorPane loginContext;
    public static String name;

    public void btnLoginOnAction(ActionEvent actionEvent) throws IOException {
        name = txtName.getText();
        loginContext.getChildren().clear();
        Stage stage = (Stage) loginContext.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/client-01-form.fxml"))));
    }
}