package uet.librarymanagementsystem.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.AccessibleAction;
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


public class login_ctrl implements Initializable {
    @FXML
    private ChoiceBox<String> AccSelector;

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
                try {
                    // Tải AdminPage.fxml và cập nhật currentParent
                    Parent adminPage = FXMLLoader.load(getClass().getResource("/uet/librarymanagementsystem/fxml/adminPage.fxml"));
                    LMSApplication.currentParent = adminPage;

                    Stage currentStage = (Stage) LoginButton.getScene().getWindow();
                    currentStage.getScene().setRoot(adminPage);

                    currentStage.sizeToScene();

                    currentStage.centerOnScreen();

                } catch (Exception e) {
                    e.printStackTrace();
                    LoginMessegeLabel.setText("Error loading admin page");
                }
            } else {
                LoginMessegeLabel.setText("Incorrect ! Try to login again");
            }
        }
        else if(myAccType.equals("Student")){
            LoginMessegeLabel.setText("Incomplete !");
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
