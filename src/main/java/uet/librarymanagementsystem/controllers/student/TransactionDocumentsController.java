
package uet.librarymanagementsystem.controllers.student;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import uet.librarymanagementsystem.controllers.LoginController;
import uet.librarymanagementsystem.entity.transactions.Transaction;
import uet.librarymanagementsystem.services.transactionServices.SearchTransactionService;

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
    private TableColumn<Transaction, String> dateTransactionColumnTransaction;

    @FXML
    private TableColumn<Transaction, String> dueDateColumnTransaction;

    @FXML
    private TableColumn<Transaction, String> idDocumentColumnTransaction;

    @FXML
    private TableColumn<Transaction, String> idTransactionColumnTransaction;

    @FXML
    private TableColumn<Transaction, String> materialColumnTransaction;

    @FXML
    private TableColumn<Transaction, String> titleColumnTransaction;

    @FXML
    private TableColumn<Transaction, String> typeColumnTransaction;

    @FXML
    void infoDocumentButtonOnClick(MouseEvent event) {

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
        typeColumnTransaction.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTypeTransaction()));
        dateTransactionColumnTransaction.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDateTransaction()));
        dueDateColumnTransaction.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDocument().getDueDate()));

        transactionList = FXCollections.observableArrayList();
        try {
            transactionList = searchTransactionService.searchTransaction(LoginController.getIdCurrentStudent());
            System.out.println("size = " + transactionList.size());
        } catch (SQLException e) {
            System.out.println("Loi");
            throw new RuntimeException(e);
        }

        transactionTableView.setItems(transactionList);
    }
}

