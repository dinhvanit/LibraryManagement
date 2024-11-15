package uet.librarymanagementsystem.services.documentServices;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import uet.librarymanagementsystem.DatabaseOperation.BorrowedDocuments;
import uet.librarymanagementsystem.DatabaseOperation.DatabaseManager;
import uet.librarymanagementsystem.DatabaseOperation.TransactionsTable;
import uet.librarymanagementsystem.controllers.LoginController;
import uet.librarymanagementsystem.entity.transactions.Transaction;

import java.sql.Connection;
import java.sql.SQLException;

public class AddBorrowDocumentService {
    public Connection getConn() {
        return conn;
    }

    private Connection conn;

    public AddBorrowDocumentService() {
        this.conn = DatabaseManager.connect();
    }

    public static ObservableList<Transaction> addBorrowDocument() throws SQLException {
        String id_student = LoginController.getIdCurrentStudent();
        ObservableList<Transaction> transactionList = TransactionsTable.searchTransByStudent_id(id_student);

        ObservableList<Transaction> transactionBorrow = FXCollections.observableArrayList();
        for (Transaction transaction : transactionList) {
            if (transaction.getReturnDate() == null) {
                transactionBorrow.add(transaction);
            }
        }
        return transactionBorrow;
    }

    public static void main(String[] args) throws SQLException {
        AddBorrowDocumentService addBorrowDocumentService = new AddBorrowDocumentService();
        addBorrowDocument();
    }

}
