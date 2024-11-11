package uet.librarymanagementsystem.services.transactionServices;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import uet.librarymanagementsystem.DatabaseOperation.DatabaseManager;
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

        ObservableList<Transaction> transactionSearchList = FXCollections.observableArrayList();
        String query = "SELECT * FROM TransactionDocument WHERE id_student = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            // Use the actual id_student parameter instead of hardcoding it
            pstmt.setString(1, id_student);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    // Retrieve transaction fields from the ResultSet
                    String retrievedIdTransaction = rs.getString("id_transaction");
                    String retrievedIdStudent = rs.getString("id_student");
                    String retrievedNameStudent = rs.getString("name_student");

                    String retrievedDateOfBirth = rs.getString("date_of_birth") != null ? rs.getString("date_of_birth") : "N/A";
                    String retrievedPhoneNumber = rs.getString("phone_number");
                    String retrievedEmail = rs.getString("email");
                    String retrievedPassword = rs.getString("password");

                    String retrievedIdDocument = rs.getString("id_document");
                    String retrievedTitleDocument = rs.getString("title_document");
                    String retrievedAuthor = rs.getString("author");
                    String retrievedMaterial = rs.getString("material");
                    String retrievedCategory = rs.getString("category");

                    String retrievedType = rs.getString("type");
                    String retrievedDateTransaction = rs.getString("date_transaction");
                    String retrievedDueDate = rs.getString("due_date");

                    // Create Student and Document objects
                    Student student = new Student(
                            retrievedIdStudent,
                            retrievedNameStudent,
                            retrievedDateOfBirth,
                            retrievedPhoneNumber,
                            retrievedEmail,
                            retrievedPassword
                    );

                    Document document = DocumentFactory.createDocument(
                            retrievedIdDocument,
                            retrievedTitleDocument,
                            retrievedAuthor,
                            retrievedMaterial,
                            retrievedCategory,
                            retrievedDueDate
                    );

                    // Add transaction to list based on type
                    if (Objects.equals(retrievedType, "Borrow")) {
                        Transaction transaction = new Transaction(document, student, TypeTransaction.BORROW, retrievedDateTransaction);
                        transaction.setId(retrievedIdTransaction);
                        transactionSearchList.add(transaction);
                    } else if (Objects.equals(retrievedType, "Return")) {
                        Transaction transaction = new Transaction(document, student, TypeTransaction.RETURN, retrievedDateTransaction);
                        transaction.setId(retrievedIdTransaction);
                        transactionSearchList.add(transaction);
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("An error occurred while searching transactions: " + e.getMessage());
        }
        return transactionSearchList;
    }


    public static void main(String[] args) {
        SearchTransactionService searchService = new SearchTransactionService();

        try {
            // Testing with the specific student ID "23020714"
            ObservableList<Transaction> transactions = searchService.searchTransaction("23020714");

            if (transactions.isEmpty()) {
                System.out.println("No transactions found for student ID: 23020714");
            } else {
                System.out.println("Transactions found:");
                for (Transaction transaction : transactions) {
                    System.out.println(transaction);
                }
            }
        } catch (SQLException e) {
            System.err.println("An error occurred while searching transactions: " + e.getMessage());
        }
    }


}
