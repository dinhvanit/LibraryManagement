package uet.librarymanagementsystem.controllers.admin;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import uet.librarymanagementsystem.entity.Page;
import uet.librarymanagementsystem.entity.transactions.Transaction;
import uet.librarymanagementsystem.services.EmailServices.CheckOverDateTrans;
import uet.librarymanagementsystem.util.WindowUtil;

import javax.swing.text.html.ImageView;
import java.net.URL;
import java.util.ResourceBundle;

public class AdminPageController implements Initializable {

    @FXML
    private BorderPane borderPaneAdminPage; // The main layout container for the admin page.

    @FXML
    private AnchorPane notionArchorPaneImage; // AnchorPane to display notifications for overdue transactions.

    /**
     * Loads the Add Student page into the center pane.
     * @param event the mouse event triggering this action
     */
    @FXML
    void clickManageStudents(MouseEvent event) {
        WindowUtil.loadCenterPane(Page.ADD_STUDENT, borderPaneAdminPage);
    }

    /**
     * Loads the Add Document page into the center pane.
     * @param event the mouse event triggering this action
     */
    @FXML
    void clickManageDocuments(MouseEvent event) {
        WindowUtil.loadCenterPane(Page.ADD_DOCUMENT, borderPaneAdminPage);
    }

    /**
     * Loads the Search and Remove Student page into the center pane.
     * @param event the mouse event triggering this action
     */
    @FXML
    void clickListOfStudents(MouseEvent event) {
        WindowUtil.loadCenterPane(Page.SEARCH_AND_REMOVE_STUDENT, borderPaneAdminPage);
    }

    /**
     * Loads the Search and Remove Document page into the center pane.
     * @param event the mouse event triggering this action
     */
    @FXML
    void clickListOfDocuments(MouseEvent event) {
        WindowUtil.loadCenterPane(Page.SEARCH_AND_REMOVE_DOCUMENT, borderPaneAdminPage);
    }

    /**
     * Loads the Transaction Manage page into the center pane.
     * @param event the mouse event triggering this action
     */
    @FXML
    void clickListOfTransactions(MouseEvent event) {
        WindowUtil.loadCenterPane(Page.TRANSACTION_MANAGE, borderPaneAdminPage);
    }

    /**
     * Reloads the center pane content.
     * @param mouseEvent the mouse event triggering this action
     */
    @FXML
    void reloadOnButton(MouseEvent mouseEvent) {
        WindowUtil.reloadCenterPane(borderPaneAdminPage);
    }

    /**
     * Ends the session and redirects to the login page.
     * @param event the mouse event triggering this action
     */
    @FXML
    void clickLogOut(MouseEvent event) {
        WindowUtil.logoutSession();
    }

    /**
     * Placeholder for future functionality.
     * @param event the mouse event triggering this action
     */
    @FXML
    void exploreClick(MouseEvent event) {
        // Placeholder for future implementation.
    }

    /**
     * Loads the Gmail notifications page into the center pane.
     * @param event the mouse event triggering this action
     */
    @FXML
    void gmailClick(MouseEvent event) {
        WindowUtil.loadCenterPane(Page.NOTION_GMAIL, borderPaneAdminPage);
    }

    /**
     * Loads the Home Admin Page into the center pane.
     * @param event the mouse event triggering this action
     */
    @FXML
    void homeClick(MouseEvent event) {
        WindowUtil.loadCenterPane(Page.HOME_ADMIN_PAGE, borderPaneAdminPage);
    }

    /**
     * Initializes the controller.
     * @param url the location used to resolve relative paths for the root object
     * @param resourceBundle the resources used to localize the root object
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        CheckOverDateTrans checkOverDateTrans = new CheckOverDateTrans();
        ObservableList<Transaction> overDateTransactionsList = checkOverDateTrans.getOverdueTransactions();
        if (overDateTransactionsList.isEmpty()) {
            System.out.println("No overdue transactions.");
            notionArchorPaneImage.setVisible(false);
        } else {
            System.out.println("There are overdue transactions.");
            notionArchorPaneImage.setVisible(true);
        }
        WindowUtil.loadCenterPane(Page.HOME_ADMIN_PAGE, borderPaneAdminPage);
    }
}
