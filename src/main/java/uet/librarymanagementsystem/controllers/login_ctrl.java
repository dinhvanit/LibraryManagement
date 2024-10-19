package uet.librarymanagementsystem.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.AccessibleAction;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;


public class login_ctrl {
    @FXML
    private Button LoginButton;

    @FXML
    private Label LoginMessegeLabel;

    @FXML
    private TextField UserName;

    @FXML
    private PasswordField Password;

    @FXML
    private Button CancelButton;

    public void cancelButtonOnAction(ActionEvent event) {
        Stage stage = (Stage) LoginButton.getScene().getWindow();
        stage.close();
    }

    public void loginButtonOnAction(ActionEvent event) {
        if (UserName.getText().equals("Dinhvandz") && Password.getText().equals("vandz")) {
            LoginMessegeLabel.setText("Try to login");
        }
        else{
            LoginMessegeLabel.setText("Incorrect ! Try to login again");
        }
    }


}
