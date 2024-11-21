package uet.librarymanagementsystem.services.transactionServices;

import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import uet.librarymanagementsystem.DatabaseOperation.DatabaseManager;
import uet.librarymanagementsystem.DatabaseOperation.TransactionsTable;
import uet.librarymanagementsystem.entity.transactions.Transaction;

import java.sql.*;
import java.util.ArrayList;
import java.util.Objects;

public class SearchTransactionService {
    private Connection conn;

    public SearchTransactionService() {
        conn = DatabaseManager.connect();
    }

    public ObservableList<Transaction> searchTransactionByIdStudent(String id_student) throws SQLException {
        return TransactionsTable.searchTransByField(id_student, null, null, null, false);
    }

    public ObservableList<Transaction> searchTransactionByIdDocumentAndReviewed(String id_document) throws SQLException {
        return TransactionsTable.searchTransByField(null, id_document, null, null, true);
    }

    public ObservableList<Transaction> searchTransactionByIdDocument(String id_document) throws SQLException {
        return TransactionsTable.searchTransByField(null, id_document, null, null, false);
    }

    public ObservableList<Transaction> getAllTransactions() throws SQLException {
        return TransactionsTable.searchTransByField(null, null, null, null, false);
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

    public static void main(String[] args) throws SQLException {
        SearchTransactionService searchTransactionService = new SearchTransactionService();
        ObservableList<Transaction> transactions = searchTransactionService.searchTransactionByIdStudent("23020714");

        // Check if the list is empty or contains transactions
        if (transactions.isEmpty()) {
            System.out.println("No transactions found for Student ID: 23020714");
        } else {
            System.out.println("Transactions found for Student ID: 23020714");
            for (Transaction transaction : transactions) {
                System.out.println("Transaction ID: " + transaction.getId());
                System.out.println("Student ID: " + transaction.getStudent().getId());
                System.out.println("Student Name: " + transaction.getStudent().getName());
                System.out.println("Date of Birth: " + transaction.getStudent().getDateOfBirth());
                System.out.println("Phone Number: " + transaction.getStudent().getPhoneNumber());
                System.out.println("Email: " + transaction.getStudent().getEmail());
                System.out.println("Document ID: " + transaction.getDocument().getId());
                System.out.println("Document Title: " + transaction.getDocument().getTitle());
                System.out.println("Author: " + transaction.getDocument().getAuthor());
                System.out.println("Material: " + transaction.getDocument().getMaterial());
                System.out.println("Category: " + transaction.getDocument().getCategory());
                System.out.println("Borrow_Date: " + transaction.getBorrowDate());
                System.out.println("Return_Date: " + transaction.getReturnDate());
                System.out.println("Due_Date: " + transaction.getDueDate());
                System.out.println("--------------------------------------------------");

            }
        }
    }


}
