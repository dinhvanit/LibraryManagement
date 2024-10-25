package uet.librarymanagementsystem.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import uet.librarymanagementsystem.LMSApplication;

import java.net.URL;
import java.util.ResourceBundle;

public class AdminController implements Initializable {

    @FXML
    private Button signOutButton;

    /*
    @FXML
    private Button edit_avatarButton;
     */

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    public void logOut() {
        try {
            Parent loginPage = FXMLLoader.load(getClass().getResource("/uet/librarymanagementsystem/fxml/login.fxml"));
            LMSApplication.currentParent = loginPage;

            Stage currentStage = (Stage) signOutButton.getScene().getWindow();
            currentStage.getScene().setRoot(loginPage);

            currentStage.sizeToScene();
            currentStage.centerOnScreen();

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error loading login page");
        }
    }

    /*
    public void editavatar(){

    }
    */
}
