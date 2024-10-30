package uet.librarymanagementsystem.controllers.admin;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import uet.librarymanagementsystem.util.WindowUtil;

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
            WindowUtil.logoutSession(); // Sử dụng phương thức logoutSession
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
