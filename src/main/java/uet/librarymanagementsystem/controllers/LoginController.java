package uet.librarymanagementsystem.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;

import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import uet.librarymanagementsystem.LMSApplication;
import uet.librarymanagementsystem.entity.Page;
import uet.librarymanagementsystem.util.WindowUtil;

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

    @FXML
    private CheckBox showPasswordButton;

    @FXML
    private TextField visiblePasswordField;

    private String myAccType;

    @FXML
    public void loginButtonOnAction(ActionEvent event) {
        performLogin();
    }

    public void onEnterLogin(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            performLogin();
        }
    }

    public void performLogin() {
        if(myAccType == null){
            loginMessegeLabel.setText("Please choose your type account !");
        }
        else if(myAccType.equals("Admin")) {
            if (credentialUserNameField.getText().equals("admin1") && credentialPasswordField.getText().equals("admin1")) {
                try {
                    WindowUtil.setPage(Page.ADMIN, "Admin Dashboard");

                } catch (Exception e) {
                    e.printStackTrace();
                    loginMessegeLabel.setText("Error loading admin page");
                }
            } else {
                loginMessegeLabel.setText("Incorrect ! Try to login again");
            }
        }
        else if(myAccType.equals("Student")){
            if (credentialUserNameField.getText().equals("student1") && credentialPasswordField.getText().equals("student1")) {
                try {
                    WindowUtil.setPage(Page.STUDENT, "Student Dashboard");

                } catch (Exception e) {
                    e.printStackTrace();
                    loginMessegeLabel.setText("Error loading student page");
                }
            } else {
                loginMessegeLabel.setText("Incorrect ! Try to login again");
            }
        }
    }

    public void showPassword(ActionEvent event) {
        if (showPasswordButton.isSelected()) {
            visiblePasswordField.setText(credentialPasswordField.getText());
            visiblePasswordField.setVisible(true);
            credentialPasswordField.setVisible(false);
        } else {
            credentialPasswordField.setText(visiblePasswordField.getText());
            credentialPasswordField.setVisible(true);
            visiblePasswordField.setVisible(false);
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        accSelectorChoiceBox.getItems().addAll("Student", "Admin");
        accSelectorChoiceBox.setOnAction(this::getAccType);

        visiblePasswordField.setVisible(false);
        credentialPasswordField.textProperty().bindBidirectional(visiblePasswordField.textProperty());

        credentialUserNameField.setOnKeyPressed(this::onEnterLogin);
        credentialPasswordField.setOnKeyPressed(this::onEnterLogin);
    }

    public void getAccType(ActionEvent event) {
        this.myAccType = accSelectorChoiceBox.getValue();
    }
}
