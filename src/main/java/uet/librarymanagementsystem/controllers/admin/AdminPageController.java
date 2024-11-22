package uet.librarymanagementsystem.controllers.admin;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import uet.librarymanagementsystem.entity.Page;
import uet.librarymanagementsystem.util.WindowUtil;

public class AdminPageController {

    @FXML
    private BorderPane borderPaneAdminPage;

    @FXML
    void clickManageStudents(MouseEvent event) {
        WindowUtil.loadCenterPane(Page.ADD_STUDENT, borderPaneAdminPage);
    }

    @FXML
    void clickManageDocuments(MouseEvent event) {
        WindowUtil.loadCenterPane(Page.ADD_DOCUMENT, borderPaneAdminPage);
    }

    @FXML
    void clickListOfStudents(MouseEvent event) {
        WindowUtil.loadCenterPane(Page.SEARCH_AND_REMOVE_STUDENT, borderPaneAdminPage);
    }

    @FXML
    void clickListOfDocuments(MouseEvent event) {
        WindowUtil.loadCenterPane(Page.SEARCH_AND_REMOVE_DOCUMENT, borderPaneAdminPage);
    }

    @FXML
    void clickListOfTransactions(MouseEvent event) {
        WindowUtil.loadCenterPane(Page.TRANSACTION_MANAGE, borderPaneAdminPage);
    }

    @FXML
    void reloadOnButton(MouseEvent mouseEvent) {
        WindowUtil.reloadCenterPane(borderPaneAdminPage);
    }

    @FXML
    void clickLogOut(MouseEvent event) {
        WindowUtil.logoutSession();
    }

}
