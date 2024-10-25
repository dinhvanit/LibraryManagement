package uet.librarymanagementsystem.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;

import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;
import uet.librarymanagementsystem.LMSApplication;

import java.net.URL;
import java.util.ResourceBundle;


public class LoginController implements Initializable {
    @FXML
    private ChoiceBox<String> accSelectorChoiceBox;

    @FXML
    private Button loginButton;

    @FXML
    private Label loginMessegeLabel;

    @FXML
    private TextField credentialUserNameField;

    @FXML
    private PasswordField credentialPasswordField;

    private String myAccType;


    public void loginButtonOnAction(ActionEvent event) {
        if(myAccType == null){
            loginMessegeLabel.setText("Please choose your type account !");
        }
        else if(myAccType.equals("Admin")) {
            if (credentialUserNameField.getText().equals("admin1") && credentialPasswordField.getText().equals("admin1")) {
                try {
                    // Tải AdminPage.fxml và cập nhật currentParent
                    Parent adminPage = FXMLLoader.load(getClass().getResource("/uet/librarymanagementsystem/fxml/adminPage.fxml"));
                    LMSApplication.currentParent = adminPage;

                    Stage currentStage = (Stage) loginButton.getScene().getWindow();
                    currentStage.getScene().setRoot(adminPage);

                    currentStage.sizeToScene();

                    currentStage.centerOnScreen();

                } catch (Exception e) {
                    e.printStackTrace();
                    loginMessegeLabel.setText("Error loading admin page");
                }
            } else {
                loginMessegeLabel.setText("Incorrect ! Try to login again");
            }
        }
        else if(myAccType.equals("Student")){
            loginMessegeLabel.setText("Incomplete !");
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        accSelectorChoiceBox.getItems().addAll("Student", "Admin");
        accSelectorChoiceBox.setOnAction(this::getAccType);
    }

    public void getAccType(ActionEvent event) {
        this.myAccType = accSelectorChoiceBox.getValue();
    }
}
