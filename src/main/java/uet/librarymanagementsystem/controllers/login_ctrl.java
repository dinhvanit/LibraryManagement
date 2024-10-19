package uet.librarymanagementsystem.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.AccessibleAction;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;


public class login_ctrl implements Initializable {
    @FXML
    private  ChoiceBox<String> AccSelector;

    @FXML
    private Button LoginButton;

    @FXML
    private Label LoginMessegeLabel;

    @FXML
    private TextField UserName;

    @FXML
    private PasswordField Password;

    private String myAccType;


    public void loginButtonOnAction(ActionEvent event) {
        if(myAccType == null){
            LoginMessegeLabel.setText("Please choose your type account !");
        }
        else if(myAccType.equals("Admin")) {
            if (UserName.getText().equals("admin1") && Password.getText().equals("admin1")) {
                LoginMessegeLabel.setText("Try to login");
            } else {
                LoginMessegeLabel.setText("Incorrect ! Try to login again");
            }
        }
        else if(myAccType.equals("Student")){
            LoginMessegeLabel.setText("Uncomplete !");
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        AccSelector.getItems().addAll("Student", "Admin");
        AccSelector.setOnAction(this::getAccType);
    }

    public void getAccType(ActionEvent event) {
        this.myAccType = AccSelector.getValue();
    }
}
