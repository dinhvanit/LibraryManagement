package uet.librarymanagementsystem.services.transactionServices;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import uet.librarymanagementsystem.DatabaseOperation.DatabaseManager;
import uet.librarymanagementsystem.entity.documents.Document;
import uet.librarymanagementsystem.entity.documents.DocumentFactory;
import uet.librarymanagementsystem.entity.transactions.Transaction;
import uet.librarymanagementsystem.entity.users.Student;

import java.sql.*;
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
            pstmt.setString(1, "23020714");

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    String retrievedIdTransaction = rs.getString("id_transaction");

                    String retrievedIdStudent = rs.getString("id_student");
                    String retrievedNameStudent = rs.getString("name_student");
                    //String retrievedDateOfBirth = String.valueOf(rs.getDate("date_of_birth"));
                    //Date sqlDate = rs.getDate("date_of_birth");

                    Date dateOfBirth = rs.getDate("date_of_birth");
                    String retrievedDateOfBirth = "";

                    if (dateOfBirth != null) {
                        LocalDate localDate = dateOfBirth.toLocalDate();
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
                        retrievedDateOfBirth = localDate.format(formatter);
                    } else {
                        System.out.println("Date of birth is null.");
                    }

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
        }
        return transactionSearchList;
    }
}
