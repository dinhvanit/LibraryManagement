
package uet.librarymanagementsystem.controllers.student;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import uet.librarymanagementsystem.controllers.LoginController;
import uet.librarymanagementsystem.entity.Page;
import uet.librarymanagementsystem.entity.documents.Document;
import uet.librarymanagementsystem.entity.transactions.Transaction;
import uet.librarymanagementsystem.services.PDFServices.ExportTransactionToPDF;
import uet.librarymanagementsystem.services.shareDataServers.ShareDataService;
import uet.librarymanagementsystem.services.transactionServices.SearchTransactionService;
import uet.librarymanagementsystem.util.WindowUtil;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class TransactionDocumentsController implements Initializable {

    @FXML
    private TableView<Transaction> transactionTableView;

    private ObservableList<Transaction> transactionList;

    @FXML
    private TableColumn<Transaction, String> authorColumnTransaction;

    @FXML
    private TableColumn<Transaction, String> categoryColumnTransaction;

    @FXML
    private TableColumn<Transaction, String> idDocumentColumnTransaction;

    @FXML
    private TableColumn<Transaction, String> idTransactionColumnTransaction;

    @FXML
    private TableColumn<Transaction, String> materialColumnTransaction;

    @FXML
    private TableColumn<Transaction, String> titleColumnTransaction;

    @FXML
    private TableColumn<Transaction, String> borrowDateColumnTransaction;

    @FXML
    private TableColumn<Transaction, String> returnDateColumnTransaction;

    @FXML
    private TableColumn<Transaction, String> dueDateColumnTransaction;

    @FXML
    private TableColumn<Transaction, String> ratingColumnTransaction;

    @FXML
    private TableColumn<Transaction, String> reviewColumnTransaction;

    @FXML
    private TableColumn<Transaction, String> reviewDateColumnTransaction;

    @FXML
    private Label notionChoiceTransactionLabel;

    @FXML
    void infoDocumentButtonOnClick(MouseEvent event) {
        if (transactionTableView.getSelectionModel().getSelectedItem() == null) {
            notionChoiceTransactionLabel.setVisible(true);
        } else {
            notionChoiceTransactionLabel.setVisible(false);
            Document selectedDocument = transactionTableView.getSelectionModel().getSelectedItem().getDocument();
            Transaction selectedTransaction = transactionTableView.getSelectionModel().getSelectedItem();
            if (selectedDocument != null && selectedTransaction != null) {
                ShareDataService.setDocumentShare(selectedDocument);
                ShareDataService.setTransactionShare(selectedTransaction);
                Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                if (selectedTransaction.getReturnDate() == null) {
                    WindowUtil.showSecondaryWindowWithShowInfo(
                            Page.SHOW_INFO_DOCUMENT, "Information Document", currentStage, false, false);
                } else if (selectedTransaction.getRating() == null){
                    WindowUtil.showSecondaryWindowWithShowInfo(
                            Page.SHOW_INFO_DOCUMENT, "Information Document", currentStage, true, false);
                } else {
                    WindowUtil.showSecondaryWindowWithShowInfo(
                            Page.SHOW_INFO_DOCUMENT, "Information Document", currentStage, false, true);
                }
            }
        }
    }

    @FXML
    void qrCodeClick(MouseEvent event) {
        if (transactionTableView.getSelectionModel().getSelectedItem() == null) {
            notionChoiceTransactionLabel.setVisible(true);
        } else {
            notionChoiceTransactionLabel.setVisible(false);
            Document selectedDocument = transactionTableView.getSelectionModel().getSelectedItem().getDocument();
            Transaction selectedTransaction = transactionTableView.getSelectionModel().getSelectedItem();
            if (selectedDocument != null && selectedTransaction != null) {
                ShareDataService.setDocumentShare(selectedDocument);
                ShareDataService.setTransactionShare(selectedTransaction);
                Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                WindowUtil.showSecondaryWindow(Page.QR_CODE_TRANSACTION, "QR code transaction", currentStage);
            }
        }
    }

    @FXML
    void exportPDFClick(MouseEvent event) throws IOException {
        ExportTransactionToPDF.exportTransactionToPDF(ShareDataService.getIdStudentShare());
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        WindowUtil.showSecondaryWindow(Page.NOTION_SUCCESS, "Export PDF", currentStage);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        SearchTransactionService searchTransactionService = new SearchTransactionService();

        idTransactionColumnTransaction.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getId()));
        idDocumentColumnTransaction.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDocument().getId()));
        titleColumnTransaction.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDocument().getTitle()));
        authorColumnTransaction.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDocument().getAuthor()));
        materialColumnTransaction.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDocument().getMaterial()));
        categoryColumnTransaction.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDocument().getCategory()));
        borrowDateColumnTransaction.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getBorrowDate()));
        returnDateColumnTransaction.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getReturnDate()));
        dueDateColumnTransaction.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDueDate()));
        reviewDateColumnTransaction.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getReviewDate()));
        reviewColumnTransaction.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getReview()));
        ratingColumnTransaction.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getRating()));

        transactionList = FXCollections.observableArrayList();
        try {
            transactionList = searchTransactionService.searchTransactionByIdStudent(LoginController.getIdCurrentStudent());
            System.out.println("size = " + transactionList.size());
        } catch (SQLException e) {
            System.out.println("Loi");
            throw new RuntimeException(e);
        }

        transactionTableView.setItems(transactionList);
    }
}

