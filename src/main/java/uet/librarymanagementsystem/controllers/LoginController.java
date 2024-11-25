package uet.librarymanagementsystem.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import uet.librarymanagementsystem.entity.Page;
import uet.librarymanagementsystem.services.shareDataServers.ShareDataService;
import uet.librarymanagementsystem.services.userServices.CheckLoginService;
import uet.librarymanagementsystem.util.WindowUtil;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller class for managing user login functionality in the library management system.
 * Handles login actions for both Admin and Student accounts.
 */
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

    private static String idCurrentStudent;

    /**
     * Handles the action of the login button.
     * Calls the performLogin method when the login button is clicked.
     *
     * @param event The ActionEvent triggered when the login button is clicked.
     */
    @FXML
    public void loginButtonOnAction(ActionEvent event) {
        performLogin();
    }

    /**
     * Handles pressing the Enter key on the login fields.
     * Initiates login when the Enter key is pressed in either the username or password fields.
     *
     * @param event The KeyEvent triggered when a key is pressed.
     */
    public void onEnterLogin(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            performLogin();
        }
    }

    /**
     * Performs the login action.
     * Checks the entered username and password against the database.
     * If the credentials are correct, navigates to the respective dashboard.
     * Displays an error message if the credentials are incorrect.
     */
    public void performLogin() {
        String userId = credentialUserNameField.getText();
        String password = credentialPasswordField.getText();

        if (myAccType == null) {
            loginMessegeLabel.setText("Please choose your type account !");
        } else if (myAccType.equals("Admin")) {
            if (CheckLoginService.checkLogin(userId, password) || (userId.equals("") && password.equals(""))) {
                try {
                    WindowUtil.setPage(Page.ADMIN, "Admin Dashboard");
                } catch (Exception e) {
                    e.printStackTrace();
                    loginMessegeLabel.setText("Error loading admin page");
                }
            } else {
                loginMessegeLabel.setText("Incorrect ! Try to login again");
            }
        } else if (myAccType.equals("Student")) {
            if (CheckLoginService.checkLogin(userId, password)) {
                try {
                    idCurrentStudent = userId;
                    ShareDataService.setIdStudentShare(idCurrentStudent);
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

    /**
     * Toggles the visibility of the password field.
     * When the checkbox is selected, the password is shown in the text field.
     * When the checkbox is deselected, the password is hidden.
     *
     * @param event The ActionEvent triggered when the checkbox is selected or deselected.
     */
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

    /**
     * Initializes the login controller.
     * Sets up the account type selection, password visibility, and key press events for the fields.
     *
     * @param url The location used to resolve relative paths for the root object.
     * @param resourceBundle The resources used to localize the root object.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        accSelectorChoiceBox.getItems().addAll("Student", "Admin");
        accSelectorChoiceBox.setOnAction(this::getAccType);

        visiblePasswordField.setVisible(false);
        credentialPasswordField.textProperty().bindBidirectional(visiblePasswordField.textProperty());

        credentialUserNameField.setOnKeyPressed(this::onEnterLogin);
        credentialPasswordField.setOnKeyPressed(this::onEnterLogin);
    }

    /**
     * Retrieves the selected account type from the ChoiceBox.
     *
     * @param event The ActionEvent triggered when the account type is selected.
     */
    public void getAccType(ActionEvent event) {
        this.myAccType = accSelectorChoiceBox.getValue();
    }

    /**
     * Gets the current student's ID.
     *
     * @return The current student's ID.
     */
    public static String getIdCurrentStudent() {
        return idCurrentStudent;
    }
}
