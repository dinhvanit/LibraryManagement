package uet.librarymanagementsystem.DatabaseOperation;

import javafx.collections.FXCollections;
import uet.librarymanagementsystem.entity.documents.Document;
import uet.librarymanagementsystem.entity.documents.DocumentFactory;
import uet.librarymanagementsystem.entity.documents.materials.Book;
import uet.librarymanagementsystem.entity.transactions.Transaction;
import uet.librarymanagementsystem.entity.users.Student;
import uet.librarymanagementsystem.services.userServices.AddStudentService;

import java.sql.*;
import java.time.LocalDate;
import java.util.Objects;

import static uet.librarymanagementsystem.DatabaseOperation.DatabaseManager.connect;
import javafx.collections.ObservableList;

public class TransactionsTable {
    public static void insertTransaction(Transaction transaction) throws SQLException {
        Connection conn = connect();

        if (conn == null || conn.isClosed()) {
            throw new SQLException("Cannot insert transaction, connection is closed or invalid.");
        }

        try {
            String insertTransactionSQL = "INSERT INTO TransactionDocument " +
                    "(id_student, name_student, date_of_birth, phone_number, " +
                    "email, password, id_document, title_document, author, " +
                    "material, category, type, date_transaction, due_date) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement transactionStmt = conn.prepareStatement(insertTransactionSQL);

            transactionStmt.setString(1, transaction.getStudent().getId());
            transactionStmt.setString(2, transaction.getStudent().getName());
            transactionStmt.setString(3, transaction.getStudent().getDateOfBirth());

            transactionStmt.setString(4, transaction.getStudent().getPhoneNumber());
            transactionStmt.setString(5, transaction.getStudent().getEmail());
            transactionStmt.setString(6, transaction.getStudent().getPassword());

            transactionStmt.setString(7, transaction.getDocument().getId());
            transactionStmt.setString(8, transaction.getDocument().getTitle());
            transactionStmt.setString(9, transaction.getDocument().getAuthor());
            transactionStmt.setString(10, transaction.getDocument().getMaterial());
            transactionStmt.setString(11, transaction.getDocument().getCategory());

            transactionStmt.setString(12, transaction.getTypeTransaction());
            transactionStmt.setString(13, transaction.getDateTransaction());
            transactionStmt.setString(14, transaction.getDocument().getDueDate());

            transactionStmt.executeUpdate();

            String generatedIdSQL = "SELECT last_insert_rowid()";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(generatedIdSQL);

            if (rs.next()) {
                String generatedId = rs.getString(1); // Lấy giá trị id tự động sinh ra
                transaction.setId(generatedId);  // Cập nhật ID cho transaction
            }

            System.out.println("Transaction added successfully!");

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Loiiii");
            throw e;
        } finally {
            conn.close();
        }
    }

//    public static void searchTransByStudent_id(String id_student) {
//        String query = "SELECT * FROM TransactionDocument WHERE id_student = ?";
//
//        try (Connection conn = DatabaseManager.connect();
//             PreparedStatement pstmt = conn.prepareStatement(query)) {
//
//            // Set the student ID parameter
//            pstmt.setString(1, id_student);
//
//            try (ResultSet rs = pstmt.executeQuery()) {
//                boolean found = false;
//                while (rs.next()) {
//                    found = true;
//
//                    // Retrieve transaction fields from ResultSet
//                    String retrievedIdTransaction = rs.getString("id_transaction");
//                    String retrievedIdStudent = rs.getString("id_student");
//                    String retrievedNameStudent = rs.getString("name_student");
//                    String retrievedDateOfBirth = rs.getString("date_of_birth");
//                    String retrievedPhoneNumber = rs.getString("phone_number");
//                    String retrievedEmail = rs.getString("email");
//                    String retrievedPassword = rs.getString("password");
//
//                    String retrievedIdDocument = rs.getString("id_document");
//                    String retrievedTitleDocument = rs.getString("title_document");
//                    String retrievedAuthor = rs.getString("author");
//                    String retrievedMaterial = rs.getString("material");
//                    String retrievedCategory = rs.getString("category");
//
//                    String retrievedType = rs.getString("type");
//                    String retrievedDateTransaction = rs.getString("date_transaction");
//                    String retrievedDueDate = rs.getString("due_date");
//
//                    // Print transaction details
//                    System.out.println("\nTransaction found for Student ID: " + id_student);
//                    System.out.println("Transaction ID: " + retrievedIdTransaction);
//                    System.out.println("Student Name: " + retrievedNameStudent);
//                    System.out.println("Date of Birth: " + retrievedDateOfBirth);
//                    System.out.println("Phone Number: " + retrievedPhoneNumber);
//                    System.out.println("Email: " + retrievedEmail);
//                    System.out.println("Password: " + retrievedPassword);
//
//                    System.out.println("Document ID: " + retrievedIdDocument);
//                    System.out.println("Document Title: " + retrievedTitleDocument);
//                    System.out.println("Author: " + retrievedAuthor);
//                    System.out.println("Material: " + retrievedMaterial);
//                    System.out.println("Category: " + retrievedCategory);
//
//                    System.out.println("Transaction Type: " + retrievedType);
//                    System.out.println("Date of Transaction: " + retrievedDateTransaction);
//                    System.out.println("Due Date: " + retrievedDueDate);
//                }
//
//                if (!found) {
//                    System.out.println("No transactions found for Student ID: " + id_student);
//                }
//            }
//        } catch (SQLException e) {
//            System.err.println("An error occurred while searching transactions by Student ID: " + e.getMessage());
//        }
//    }

    public static ObservableList<Transaction> searchTransByStudent_id(String id_student) {
        String query = "SELECT * FROM TransactionDocument WHERE id_student = ?";
        ObservableList<Transaction> transactionList = FXCollections.observableArrayList();

        try (Connection conn = DatabaseManager.connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            // Set the student ID parameter
            pstmt.setString(1, id_student);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    // Retrieve transaction fields from ResultSet
                    String retrievedIdTransaction = rs.getString("id_transaction");
                    String retrievedIdStudent = rs.getString("id_student");
                    String retrievedNameStudent = rs.getString("name_student");
                    String retrievedDateOfBirth = rs.getString("date_of_birth");
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

                    // Create Transaction object based on the type
                    Transaction transaction;
                    if (Objects.equals(retrievedType, "Borrow")) {
                        transaction = new Transaction(document, student, Transaction.TypeTransaction.BORROW, retrievedDateTransaction);
                    } else {
                        transaction = new Transaction(document, student, Transaction.TypeTransaction.RETURN, retrievedDateTransaction);
                    }
                    transaction.setId(retrievedIdTransaction);

                    // Add transaction to list
                    transactionList.add(transaction);
                }

                if (transactionList.isEmpty()) {
                    System.out.println("No transactions found for Student ID: " + id_student);
                }
            }
        } catch (SQLException e) {
            System.err.println("An error occurred while searching transactions by Student ID: " + e.getMessage());
        }

        return transactionList;
    }

    public static void main(String[] args) throws SQLException {
//        String id = "23020675";
//        String name = "Dang Dinh Khang";
//        String birthday = "2005-03-27";
//        String phone = "123456789";
//        String email = "lhang18022005@gmail.com";
//        Student student = new Student(id, name, birthday, phone, email, id);
//
//        Book book = new Book("0101000200310001", "Hungary 56", "Andy Anderson", Book.BookCategory.FICTION);
//
//        Transaction transaction = new Transaction(book, student, Transaction.TypeTransaction.BORROW, "11-12-2025");
//        insertTransaction(transaction);
//        System.out.println(transaction.getId());
//          searchTransByStudent_id("23020675")

    }
}
