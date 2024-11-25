package uet.librarymanagementsystem.controllers.student;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import uet.librarymanagementsystem.entity.Page;
import uet.librarymanagementsystem.util.WindowUtil;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller class for managing the Student Page.
 * Handles navigation and event handling for various student-related features.
 */
public class StudentPageController implements Initializable {

    @FXML
    private BorderPane borderPaneStudentPage;

    /**
     * Handles the event when the "Search and Borrow" button is clicked.
     * Loads the Search and Borrow Document page into the center pane.
     *
     * @param event Mouse event triggered by the button click.
     */
    @FXML
    void searchAndBorrowButtonClick(MouseEvent event) {
        WindowUtil.loadCenterPane(Page.SEARCH_AND_BORROW_DOCUMENT, borderPaneStudentPage);
    }

    /**
     * Handles the event when the "Return Document" button is clicked.
     * Loads the Return Document page into the center pane.
     *
     * @param event Mouse event triggered by the button click.
     */
    @FXML
    void returnButtonClick(MouseEvent event) {
        WindowUtil.loadCenterPane(Page.RETURN_DOCUMENT, borderPaneStudentPage);
    }

    /**
     * Handles the event when the "Transaction" button is clicked.
     * Loads the Transaction Document page into the center pane.
     *
     * @param event Mouse event triggered by the button click.
     */
    @FXML
    void transactionButtonClick(MouseEvent event) {
        WindowUtil.loadCenterPane(Page.TRANSACTION_DOCUMENT, borderPaneStudentPage);
    }

    /**
     * Handles the event when the "Update Password" menu item is clicked.
     * Loads the Change Password page into the center pane.
     */
    @FXML
    void updatePasswordMenuClick() {
        WindowUtil.loadCenterPane(Page.CHANGE_PASSWORD, borderPaneStudentPage);
    }

    /**
     * Handles the event when the "Show Info" menu item is clicked.
     * Loads the Show Info page into the center pane.
     */
    @FXML
    void showInfoStudentMenuClick() {
        WindowUtil.loadCenterPane(Page.SHOW_INFO, borderPaneStudentPage);
    }

    /**
     * Handles the event when the "Reload Page" button is clicked.
     * Reloads the current page displayed in the center pane.
     *
     * @param event Mouse event triggered by the button click.
     */
    @FXML
    public void reloadPageButton(MouseEvent event) {
        WindowUtil.reloadCenterPane(borderPaneStudentPage);
    }

    /**
     * Handles the event when the "Home" button is clicked.
     * Loads the Home Student page into the center pane.
     */
    public void homeButtonOnClick() {
        WindowUtil.loadCenterPane(Page.HOME_STUDENT, borderPaneStudentPage);
    }

    /**
     * Handles the event when the "Logout" menu item is clicked.
     * Logs the user out and ends the current session.
     */
    @FXML
    void logoutMenuClick() {
        WindowUtil.logoutSession();
    }

    /**
     * Initializes the Student Page controller.
     * Loads the Home Student page into the center pane when the page is displayed.
     *
     * @param location  The location used to resolve relative paths for the root object, or null if not known.
     * @param resources The resources used to localize the root object, or null if not specified.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Load the HOME_STUDENT page into the center pane when the Student Page is displayed
        WindowUtil.loadCenterPane(Page.HOME_STUDENT, borderPaneStudentPage);
    }
}
