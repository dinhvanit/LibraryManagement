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
    private BorderPane borderPaneAdminPage;

    @FXML
    private AnchorPane notionArchorPaneImage;

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

    @FXML
    void exploreClick(MouseEvent event) {

    }

    @FXML
    void gmailClick(MouseEvent event) {
        WindowUtil.loadCenterPane(Page.NOTION_GMAIL, borderPaneAdminPage);
    }

    @FXML
    void homeClick(MouseEvent event) {
        WindowUtil.loadCenterPane(Page.HOME_ADMIN_PAGE, borderPaneAdminPage);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        CheckOverDateTrans checkOverDateTrans = new CheckOverDateTrans();
        ObservableList<Transaction> overDateTransactionsList = checkOverDateTrans.getOverdueTransactions();
        if (overDateTransactionsList.isEmpty()) {
            System.out.println("HE");
            notionArchorPaneImage.setVisible(false);
        } else {
            System.out.println("co nguoi chua tra");
            notionArchorPaneImage.setVisible(true);
        }
        WindowUtil.loadCenterPane(Page.HOME_ADMIN_PAGE, borderPaneAdminPage);
    }
}