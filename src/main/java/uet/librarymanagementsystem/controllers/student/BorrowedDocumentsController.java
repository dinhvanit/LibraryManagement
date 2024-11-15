package uet.librarymanagementsystem.controllers.student;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import uet.librarymanagementsystem.DatabaseOperation.TransactionsTable;
import uet.librarymanagementsystem.entity.documents.Document;
import uet.librarymanagementsystem.entity.transactions.Transaction;
import uet.librarymanagementsystem.services.documentServices.AddBorrowDocumentService;
import uet.librarymanagementsystem.services.documentServices.SearchDocumentService;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

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
    void infoDocumentButtonOnClick(MouseEvent event) {

    }

    @FXML
    void returnAllDocumentsButtonOnClick(MouseEvent event) throws SQLException{
        String today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
        for (Transaction transaction : borrowedDocumentsList) {
            TransactionsTable.updateReturnDate(transaction.getId(), today);
        }
        borrowedDocumentsList.clear();
        borrowedDocumentsTableView.setItems(borrowedDocumentsList);
    }

    @FXML
    void returnDocumentButtonOnClick(MouseEvent event) throws SQLException {
        Transaction selectedTransaction = borrowedDocumentsTableView.getSelectionModel().getSelectedItem();
        String today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
        if (selectedTransaction != null) {
            TransactionsTable.updateReturnDate(selectedTransaction.getId(), today);
            borrowedDocumentsList.remove(selectedTransaction);
            borrowedDocumentsTableView.setItems(borrowedDocumentsList);
        }
    }

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
        try {
            borrowedDocumentsList = AddBorrowDocumentService.addBorrowDocument();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        borrowedDocumentsTableView.setItems(borrowedDocumentsList);

    }

}
