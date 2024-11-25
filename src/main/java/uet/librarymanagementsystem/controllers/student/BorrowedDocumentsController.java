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
import uet.librarymanagementsystem.DatabaseOperation.TransactionsTable;
import uet.librarymanagementsystem.entity.Page;
import uet.librarymanagementsystem.entity.documents.Document;
import uet.librarymanagementsystem.entity.transactions.Transaction;
import uet.librarymanagementsystem.services.documentServices.AddBorrowDocumentService;
import uet.librarymanagementsystem.services.shareDataServers.ShareDataService;
import uet.librarymanagementsystem.util.WindowUtil;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

/**
 * The {@code BorrowedDocumentsController} class manages the borrowed documents' data display and interactions
 * in the student view. It allows users to view borrowed documents, return individual documents, or return all borrowed documents.
 */
public class BorrowedDocumentsController implements Initializable {

    @FXML
    private TableView<Transaction> borrowedDocumentsTableView;

    private ObservableList<Transaction> borrowedDocumentsList;

    @FXML
    private TableColumn<Transaction, String> authorColumnBorrowedDocuments;

    @FXML
    private TableColumn<Transaction, String> borrowDateColumnBorrowedDocuments;

    @FXML
    private TableColumn<Transaction, String> categoryColumnBorrowedDocuments;

    @FXML
    private TableColumn<Transaction, String> dueDateColumnBorrowedDocuments;

    @FXML
    private TableColumn<Transaction, String> idColumnBorrowedDocuments;

    @FXML
    private TableColumn<Transaction, String> materialColumnBorrowedDocuments;

    @FXML
    private TableColumn<Transaction, String> titleColumnBorrowedDocuments;

    @FXML
    private Label notionChoiceTransactionLabel;

    /**
     * Handles the event when the user clicks the 'Document Info' button.
     * Displays detailed information of the selected document in a new window.
     * If no document is selected, a notification is shown.
     *
     * @param event The MouseEvent triggered by clicking the 'Document Info' button.
     */
    @FXML
    void infoDocumentButtonOnClick(MouseEvent event) {
        if (borrowedDocumentsTableView.getSelectionModel().getSelectedItem() == null) {
            notionChoiceTransactionLabel.setVisible(true);
        } else {
            notionChoiceTransactionLabel.setVisible(false);
            Document selectedDocument = borrowedDocumentsTableView.getSelectionModel().getSelectedItem().getDocument();
            if (selectedDocument != null) {
                ShareDataService.setDocumentShare(selectedDocument);
                Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                WindowUtil.showSecondaryWindowWithShowInfo(
                        Page.SHOW_INFO_DOCUMENT, "Information Document", currentStage, false, false);
            }
        }
    }

    /**
     * Handles the event when the user clicks the 'Return All Documents' button.
     * Updates the return date for all borrowed documents and clears the borrowed documents list.
     *
     * @param event The MouseEvent triggered by clicking the 'Return All Documents' button.
     * @throws SQLException If a database error occurs while updating return dates.
     */
    @FXML
    void returnAllDocumentsButtonOnClick(MouseEvent event) throws SQLException {
        String today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
        for (Transaction transaction : borrowedDocumentsList) {
            TransactionsTable.updateReturnDate(transaction.getId(), today);
        }
        borrowedDocumentsList.clear();
        borrowedDocumentsTableView.setItems(borrowedDocumentsList);
    }

    /**
     * Handles the event when the user clicks the 'Return Document' button.
     * Updates the return date for the selected document and removes it from the borrowed documents list.
     *
     * @param event The MouseEvent triggered by clicking the 'Return Document' button.
     * @throws SQLException If a database error occurs while updating the return date.
     */
    @FXML
    void returnDocumentButtonOnClick(MouseEvent event) throws SQLException {
        if (borrowedDocumentsTableView.getSelectionModel().getSelectedItem() == null) {
            notionChoiceTransactionLabel.setVisible(true);
        } else {
            notionChoiceTransactionLabel.setVisible(false);
            Transaction selectedTransaction = borrowedDocumentsTableView.getSelectionModel().getSelectedItem();
            String today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
            if (selectedTransaction != null) {
                TransactionsTable.updateReturnDate(selectedTransaction.getId(), today);
                borrowedDocumentsList.remove(selectedTransaction);
                borrowedDocumentsTableView.setItems(borrowedDocumentsList);
            }
        }
    }

    /**
     * Initializes the controller by setting up the table columns and populating the list of borrowed documents.
     * The borrowed documents list is fetched using the {@code AddBorrowDocumentService}.
     *
     * @param url The location used to resolve relative paths for the root object, or {@code null} if the location is not available.
     * @param resourceBundle The resources used to localize the root object, or {@code null} if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        idColumnBorrowedDocuments.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDocument().getId()));
        titleColumnBorrowedDocuments.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDocument().getTitle()));
        authorColumnBorrowedDocuments.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDocument().getAuthor()));
        materialColumnBorrowedDocuments.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDocument().getMaterial()));
        categoryColumnBorrowedDocuments.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDocument().getCategory()));
        borrowDateColumnBorrowedDocuments.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getBorrowDate()));
        dueDateColumnBorrowedDocuments.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDueDate()));

        borrowedDocumentsList = FXCollections.observableArrayList();
        borrowedDocumentsList = AddBorrowDocumentService.addBorrowDocument();
        borrowedDocumentsTableView.setItems(borrowedDocumentsList);
    }
}
