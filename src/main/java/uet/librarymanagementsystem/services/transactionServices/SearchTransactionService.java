package uet.librarymanagementsystem.services.transactionServices;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import uet.librarymanagementsystem.DatabaseOperation.DatabaseManager;
import uet.librarymanagementsystem.DatabaseOperation.TransactionsTable;
import uet.librarymanagementsystem.entity.documents.Document;
import uet.librarymanagementsystem.entity.documents.DocumentFactory;
import uet.librarymanagementsystem.entity.transactions.Transaction;
import uet.librarymanagementsystem.entity.users.Student;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

import static uet.librarymanagementsystem.entity.transactions.Transaction.*;

public class SearchTransactionService {
    private Connection conn;

    public SearchTransactionService() {
        conn = DatabaseManager.connect();
    }

    public ObservableList<Transaction> searchTransaction(String id_student) throws SQLException {
           return TransactionsTable.searchTransByStudent_id(id_student);
    }


    public static void main(String[] args) throws SQLException {
        SearchTransactionService searchTransactionService = new SearchTransactionService();
        ObservableList<Transaction> transactions = searchTransactionService.searchTransaction("23020714");

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
