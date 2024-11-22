package uet.librarymanagementsystem.controllers.student;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import uet.librarymanagementsystem.entity.Page;
import uet.librarymanagementsystem.util.WindowUtil;

import java.net.URL;
import java.util.ResourceBundle;

public class StudentPageController implements Initializable {

    @FXML
    private BorderPane borderPaneStudentPage;

    @FXML
    void searchAndBorrowButtonClick(MouseEvent event) {
        WindowUtil.loadCenterPane(Page.SEARCH_AND_BORROW_DOCUMENT, borderPaneStudentPage);
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
    void showInfoStudentMenuClick() {
        WindowUtil.loadCenterPane(Page.SHOW_INFO, borderPaneStudentPage);
    }

    @FXML
    public void reloadPageButton(MouseEvent event) {
        WindowUtil.reloadCenterPane(borderPaneStudentPage);
    }

    public void homeButtonOnClick() {
        WindowUtil.loadCenterPane(Page.HOME_STUDENT, borderPaneStudentPage);
    }

    @FXML
    void logoutMenuClick() {
        WindowUtil.logoutSession();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Tải HOME_STUDENT vào CenterPane khi StudentPage được hiển thị
        WindowUtil.loadCenterPane(Page.HOME_STUDENT, borderPaneStudentPage);
    }
}
