package uet.librarymanagementsystem.controllers.student;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import uet.librarymanagementsystem.entity.Page;
import uet.librarymanagementsystem.util.WindowUtil;

import java.io.IOException;

public class StudentPageController {


    @FXML
    private BorderPane borderPaneStudentPage;

    @FXML
    void searchAndBorrowButtonClick(MouseEvent event) {
        WindowUtil.loadCenterPane(Page.SEARCH_AND_BORROW_DOCUMENT, borderPaneStudentPage);
    }

    @FXML
    void logOutButtonClick(MouseEvent event) {
        WindowUtil.logoutSession();
    }

    @FXML
    void returnButtonClick(MouseEvent event) {
        WindowUtil.loadCenterPane(Page.RETURN_DOCUMENT, borderPaneStudentPage);
    }

    @FXML
    void transactionButtonClick(MouseEvent event) {
        WindowUtil.loadCenterPane(Page.TRANSACTION_DOCUMENT, borderPaneStudentPage);
    }

    @FXML
    void updatePasswordMenuClick() {
        WindowUtil.loadCenterPane(Page.CHANGE_PASSWORD, borderPaneStudentPage);
    }

    @FXML
    void showInfoStudentMenuClick(){
        WindowUtil.loadCenterPane(Page.SHOW_INFO, borderPaneStudentPage);
    }

    @FXML
    void logoutMenuClick() {
        // Gọi hàm logout
        WindowUtil.logoutSession();
    }
}
