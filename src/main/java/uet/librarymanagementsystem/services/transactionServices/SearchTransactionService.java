package uet.librarymanagementsystem.services.transactionServices;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import uet.librarymanagementsystem.DatabaseOperation.DatabaseManager;
import uet.librarymanagementsystem.DatabaseOperation.TransactionsTable;
import uet.librarymanagementsystem.entity.transactions.Transaction;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class SearchTransactionService {
    private Connection conn;

    public SearchTransactionService() {
        conn = DatabaseManager.connect();
    }

    public ObservableList<Transaction> searchTransactionByIdStudent(String id_student) throws SQLException {
        return TransactionsTable.searchTransByField(id_student, null, false, false, false, null, false);
    }

    public ObservableList<Transaction> searchTransactionByIdDocumentAndReviewed(String id_document) throws SQLException {
        return TransactionsTable.searchTransByField(null, id_document, false, false, false, null, true);
    }

    public ObservableList<Transaction> searchTransactionByIdDocument(String id_document) throws SQLException {
        return TransactionsTable.searchTransByField(null, id_document, false, false, false, null, false);
    }

    public ObservableList<Transaction> getAllTransactions() throws SQLException {
        return TransactionsTable.searchTransByField(null, null, false, false, false, null, false);
    }

    public ObservableList<Transaction> searchTransactionByIdStudentBorrowing(String id_student) throws SQLException {
        return TransactionsTable.searchTransByField(id_student, null, true, false, false, null, false);
    }

    public ObservableList<Transaction> searchTransactionByIdStudentReturned(String id_student) throws SQLException {
        return TransactionsTable.searchTransByField(id_student, null, true, true, false, null, false);
    }

    public ObservableList<Transaction> searchTransactionByIdStudentOverDate(String id_student) throws SQLException {
        return TransactionsTable.searchTransByField(id_student, null, true, false, true, null, false);
    }

    public ObservableList<Transaction> searchTransactionByAllStudentOverDate() throws SQLException {
        return TransactionsTable.searchTransByField(null, null, true, false, true, null, false);
    }

    public ObservableList<Transaction> searchTransactionBorrowing() throws SQLException {
        return TransactionsTable.searchTransByField(null, null, true, false, false, null, false);
    }

    public int[] countBooksByStatus(String idStudent) throws SQLException {
        int borrowingBooks = searchTransactionByIdStudentBorrowing(idStudent).size();
        int returnedBooks = searchTransactionByIdStudentReturned(idStudent).size();
        int overdueBooks = searchTransactionByIdStudentOverDate(idStudent).size();
        int withinDueBooks = borrowingBooks- overdueBooks;

        return new int[]{returnedBooks, withinDueBooks, overdueBooks};
    }

    public int[] ratingOfIdDocument(String id_document) throws SQLException {
        ObservableList<Transaction> transactionByIdDocument = searchTransactionByIdDocumentAndReviewed(id_document);
        int[] resultOfRating = new int[]{0, 0, 0, 0, 0 ,0};
        for (Transaction transaction : transactionByIdDocument) {
            if (Objects.equals(transaction.getRating(), "1")) {
                resultOfRating[1]++;
            } else if (Objects.equals(transaction.getRating(), "2")) {
                resultOfRating[2]++;
            } else if (Objects.equals(transaction.getRating(), "3")) {
                resultOfRating[3]++;
            } else if (Objects.equals(transaction.getRating(), "4")) {
                resultOfRating[4]++;
            } else if (Objects.equals(transaction.getRating(), "5")) {
                resultOfRating[5]++;
            }
        }
        return resultOfRating;
    }
}
