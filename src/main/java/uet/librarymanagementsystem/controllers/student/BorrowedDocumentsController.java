package uet.librarymanagementsystem.controllers.student;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import uet.librarymanagementsystem.entity.documents.Document;
import uet.librarymanagementsystem.entity.transactions.Transaction;
import uet.librarymanagementsystem.services.documentServices.SearchDocumentService;

import java.net.URL;
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
    void returnAllDocumentsButtonOnClick(MouseEvent event) {

    }

    @FXML
    void returnDocumentButtonOnClick(MouseEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        idColumnBorrowedDocuments.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDocument().getId()));
        titleColumnBorrowedDocuments.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDocument().getTitle()));
        authorColumnBorrowedDocuments.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDocument().getAuthor()));
        materialColumnBorrowedDocuments.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDocument().getMaterial()));
        categoryColumnBorrowedDocuments.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDocument().getCategory()));
        borrowDateColumnBorrowedDocuments.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDateTransaction()));
        dueDateColumnBorrowedDocuments.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDocument().getDueDate()));

        borrowedDocumentsList = FXCollections.observableArrayList();
        borrowedDocumentsTableView.setItems(borrowedDocumentsList);

    }

}
