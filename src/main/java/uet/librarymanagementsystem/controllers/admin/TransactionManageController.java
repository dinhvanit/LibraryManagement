package uet.librarymanagementsystem.controllers.admin;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import uet.librarymanagementsystem.controllers.LoginController;
import uet.librarymanagementsystem.entity.Page;
import uet.librarymanagementsystem.entity.documents.Document;
import uet.librarymanagementsystem.services.shareDataServers.ShareDataService;
import uet.librarymanagementsystem.services.transactionServices.SearchTransactionService;
import uet.librarymanagementsystem.entity.transactions.Transaction;
import uet.librarymanagementsystem.util.WindowUtil;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class TransactionManageController implements Initializable {

    private ObservableList<Transaction> transactionManageList;

    @FXML
    private TableColumn<Transaction, String> authorManageColumnTransaction;

    @FXML
    private TableColumn<Transaction, String> borrowDateManageColumnTransaction;
    @FXML
    private TableColumn<Transaction, String> categoryManageColumnTransaction;

    @FXML
    private TableColumn<Transaction, String> dueDateManageColumnTransaction;

    @FXML
    private TableColumn<Transaction, String> idDocumentManageColumnTransaction;

    @FXML
    private TextField idManageDocumentTextField;

    @FXML
    private TextField idManageStudentTextField;

    @FXML
    private TableColumn<Transaction, String> idStudentManageColumnTransaction;

    @FXML
    private TableColumn<Transaction, String> idTransactionManageColumnTransaction;

    @FXML
    private TableColumn<Transaction, String> materialManageColumnTransaction;

    @FXML
    private TableColumn<Transaction, String> ratingManageColumnTransaction;

    @FXML
    private TableColumn<Transaction, String> returnDateManageColumnTransaction;

    @FXML
    private TableColumn<Transaction, String> reviewDateManageColumnTransaction;

    @FXML
    private TableColumn<Transaction, String> reviewManageColumnTransaction;

    @FXML
    private TableColumn<Transaction, String> titleManageColumnTransaction;

    @FXML
    private TableColumn<Transaction, String> nameStudentManageColumnTransaction;

    @FXML
    private TableView<Transaction> transactionManageTableView;

    @FXML
    private Label notionChoiceTransactionLabel;

    /**
     * Opens a new window with document details.
     * @param event the mouse event triggered when the button is clicked
     */
    @FXML
    void infoDocumentManageButtonOnClick(MouseEvent event) {
        if (transactionManageTableView.getSelectionModel().getSelectedItem() == null) {
            notionChoiceTransactionLabel.setVisible(true);
        } else {
            notionChoiceTransactionLabel.setVisible(false);
            Document selectedDocument = transactionManageTableView.getSelectionModel().getSelectedItem().getDocument();
            Transaction selectedTransaction = transactionManageTableView.getSelectionModel().getSelectedItem();
            if (selectedDocument != null && selectedTransaction != null) {
                ShareDataService.setDocumentShare(selectedDocument);
                ShareDataService.setTransactionShare(selectedTransaction);
                Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                WindowUtil.showSecondaryWindowWithShowInfo(
                        Page.SHOW_INFO_DOCUMENT, "Information Document", currentStage, false, false);
            }
        }
    }

    /**
     * Searches by student ID, document ID, or retrieves all transactions if no criteria are provided.
     * @param event the mouse event triggered when the button is clicked
     */
    @FXML
    void searchTransactionManageButtonOnClick(MouseEvent event) {
        String idStudent = idManageStudentTextField.getText().trim();
        String idDocument = idManageDocumentTextField.getText().trim();

        SearchTransactionService searchTransactionService = new SearchTransactionService();
        ObservableList<Transaction> transactions;

        try {
            if (!idStudent.isEmpty()) {
                transactions = searchTransactionService.searchTransactionByIdStudent(idStudent);
            } else if (!idDocument.isEmpty()) {
                transactions = searchTransactionService.searchTransactionByIdDocument(idDocument);
            } else {
                transactions = searchTransactionService.getAllTransactions();
            }

            transactionManageTableView.setItems(transactions);
            System.out.println("Number of transactions found: " + transactions.size());
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error occurred while searching transactions: " + e.getMessage());
        }
    }

    /**
     * Initializes the controller, configuring table columns and loading initial data.
     * @param url the location used to resolve relative paths for the root object
     * @param resourceBundle the resources used to localize the root object
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        SearchTransactionService searchTransactionService = new SearchTransactionService();

        // Configure table columns.
        idTransactionManageColumnTransaction.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getId()));
        idStudentManageColumnTransaction.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getStudent().getId()));
        nameStudentManageColumnTransaction.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getStudent().getName()));
        idDocumentManageColumnTransaction.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDocument().getId()));
        titleManageColumnTransaction.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDocument().getTitle()));
        authorManageColumnTransaction.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDocument().getAuthor()));
        materialManageColumnTransaction.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDocument().getMaterial()));
        categoryManageColumnTransaction.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDocument().getCategory()));
        borrowDateManageColumnTransaction.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getBorrowDate()));
        returnDateManageColumnTransaction.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getReturnDate()));
        dueDateManageColumnTransaction.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDueDate()));
        reviewDateManageColumnTransaction.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getReviewDate()));
        reviewManageColumnTransaction.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getReview()));
        ratingManageColumnTransaction.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getRating()));

        transactionManageList = FXCollections.observableArrayList();

        try {
            transactionManageList = searchTransactionService.getAllTransactions();
            System.out.println("Total transactions: " + transactionManageList.size());
        } catch (SQLException e) {
            System.out.println("Error loading transaction data: " + e.getMessage());
            throw new RuntimeException(e);
        }

        transactionManageTableView.setItems(transactionManageList);
    }
}
