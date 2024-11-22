package uet.librarymanagementsystem.DatabaseOperation;

import javafx.collections.FXCollections;
import uet.librarymanagementsystem.entity.documents.Document;
import uet.librarymanagementsystem.entity.documents.DocumentFactory;
import uet.librarymanagementsystem.entity.documents.materials.Book;
import uet.librarymanagementsystem.entity.transactions.Transaction;
import uet.librarymanagementsystem.entity.users.Student;

import java.sql.*;
import java.time.LocalDate;

import static uet.librarymanagementsystem.DatabaseOperation.DatabaseManager.connect;
import javafx.collections.ObservableList;

public class TransactionsTable {
    public static void createTransactionTable() throws SQLException {
        Connection conn = connect();

        if (conn == null || conn.isClosed()) {
            throw new SQLException("Cannot create transaction table, connection is closed or invalid.");
        }

        try {
            String createTableSQL = """
            CREATE TABLE IF NOT EXISTS TransactionDocument (
                id_transaction INTEGER PRIMARY KEY AUTOINCREMENT,
                id_student VARCHAR(255),
                name_student VARCHAR(255),
                date_of_birth DATE,
                phone_number VARCHAR(255),
                email VARCHAR(255),
                password VARCHAR(255),
                id_document VARCHAR(255),
                title_document VARCHAR(255),
                author VARCHAR(255),
                material VARCHAR(255),
                category VARCHAR(255),
                borrow_date DATE,
                return_date DATE,
                due_date DATE,
                isbn VARCHAR(255),
                FOREIGN KEY (id_student) REFERENCES Student(student_id) ON DELETE CASCADE ON UPDATE CASCADE,
                FOREIGN KEY (id_document) REFERENCES Document(document_id) ON DELETE CASCADE ON UPDATE CASCADE
            );
            """;

            Statement stmt = conn.createStatement();
            stmt.execute(createTableSQL);
            System.out.println("Transaction table created successfully with foreign key constraints!");

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error creating transaction table with foreign key constraints.");
            throw e;
        } finally {
            conn.close();
        }
    }


    public static void insertTransaction(Transaction transaction) throws SQLException {
        Connection conn = connect();

        if (conn == null || conn.isClosed()) {
            throw new SQLException("Cannot insert transaction, connection is closed or invalid.");
        }

        try {
            String insertTransactionSQL = "INSERT INTO TransactionDocument " +
                    "(id_student, name_student, date_of_birth, phone_number, " +
                    "email, password, id_document, title_document, author, " +
                    "material, category, borrow_date, return_date, due_date, isbn) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

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

            transactionStmt.setString(12, transaction.getBorrowDate());
            transactionStmt.setString(13, transaction.getReturnDate());
            transactionStmt.setString(14, transaction.getDueDate());
            if (transaction.getDocument() instanceof Book) {
                transactionStmt.setString(15,  ((Book) transaction.getDocument()).getIsbn());
            } else {
                transactionStmt.setNull(15, Types.VARCHAR);
            }

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

    public static void updateReturnDate(String transactionId, String returnDate) throws SQLException {
        Connection conn = connect();

        if (conn == null || conn.isClosed()) {
            throw new SQLException("Cannot update return_date, connection is closed or invalid.");
        }

        try {
            // Cập nhật giá trị return_date cho giao dịch có id_transaction cụ thể
            String updateReturnDateSQL = "UPDATE TransactionDocument " +
                    "SET return_date = ? " +
                    "WHERE id_transaction = ? AND return_date IS NULL";

            PreparedStatement pstmt = conn.prepareStatement(updateReturnDateSQL);

            pstmt.setString(1, returnDate);
            pstmt.setString(2, transactionId);

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected == 1) {
                System.out.println("Return date updated successfully.");
            } else {
                System.out.println("No transaction found with the given ID or return_date is already set.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error updating return_date.");
            throw e;
        } finally {
            conn.close();
        }
    }

    public static void updateRatingReviewDateReview(String transactionId, String rating, String review, String dateReview) throws SQLException {
        Connection conn = connect();

        if (conn == null || conn.isClosed()) {
            throw new SQLException("Cannot update rating, review, date review, connection is closed or invalid.");
        }

        try {
            String updateRatingReviewDateReviewSQL = "UPDATE TransactionDocument " +
                    "SET rating = ?, review = ?, review_date = ? " +
                    "WHERE id_transaction = ? AND rating IS NULL AND review IS NULL AND review_date IS NULL";

            PreparedStatement pstmt = conn.prepareStatement(updateRatingReviewDateReviewSQL);

            pstmt.setString(1, rating);
            pstmt.setString(2, review);
            pstmt.setString(3, dateReview);
            pstmt.setString(4, transactionId);

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected == 1) {
                System.out.println("Rating, review, and date review updated successfully.");
            } else {
                System.out.println("No transaction found with the given ID or return_date is already set.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error updating rating, review, or date review.");
            throw e;
        } finally {
            conn.close();
        }
    }


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
                    String retrievedISBN = rs.getString("isbn");

                    String retrievedBorrowDate = rs.getString("borrow_date");
                    String retrievedReturnDate = rs.getString("return_date");
                    String retrievedDueDate = rs.getString("due_date");
                    String retrievedReviewDate = rs.getString("review_date");
                    String retrievedRating = rs.getString("rating");
                    String retrievedReview = rs.getString("review");

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
                            retrievedISBN
                    );

                    // Create Transaction object based on the type
                    Transaction transaction = new Transaction(
                            retrievedIdTransaction, document, student, retrievedBorrowDate,
                            retrievedReturnDate, retrievedDueDate, retrievedReviewDate,
                            retrievedRating, retrievedReview);

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

    public static ObservableList<Transaction> searchTransByField(
            String idStudent, String idDocument, boolean careAboutReturning, boolean returned,
            boolean dueDate, String starRating, boolean reviewed) {
        StringBuilder query = new StringBuilder(
                "SELECT * FROM TransactionDocument WHERE 1=1");
        ObservableList<Transaction> transactionList = FXCollections.observableArrayList();

        if (idStudent != null && !idStudent.isEmpty()) {
            query.append(" AND id_student = ?");
        }
        if (idDocument != null && !idDocument.isEmpty()) {
            query.append(" AND id_document = ?");
        }
        if (careAboutReturning) {
            if (returned) {
                query.append(" AND return_date IS NOT NULL");
            } else {
                query.append(" AND return_date IS NULL");
            }
        }
        if (dueDate) {
            query.append(" AND due_date < ?");
        }
        if (starRating != null && !starRating.isEmpty()) {
            query.append(" AND rating = ?");
        }
        if (reviewed) {
            query.append(" AND review IS NOT NULL");
        }

        try (Connection conn = DatabaseManager.connect();
             PreparedStatement pstmt = conn.prepareStatement(query.toString())) {

            int paramIndex = 1;

            if (idStudent != null && !idStudent.isEmpty()) {
                pstmt.setString(paramIndex++, idStudent);
            }
            if (idDocument != null && !idDocument.isEmpty()) {
                pstmt.setString(paramIndex++, idDocument);
            }
            if (dueDate) {
                pstmt.setDate(paramIndex++, java.sql.Date.valueOf(LocalDate.now()));
            }
            if (starRating != null && !starRating.isEmpty()) {
                pstmt.setString(paramIndex++, starRating);
            }

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
                    String retrievedISBN = rs.getString("isbn");

                    String retrievedBorrowDate = rs.getString("borrow_date");
                    String retrievedReturnDate = rs.getString("return_date");
                    String retrievedDueDate = rs.getString("due_date");
                    String retrievedReviewDate = rs.getString("review_date");
                    String retrievedRating = rs.getString("rating");
                    String retrievedReview = rs.getString("review");

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
                            retrievedISBN
                    );

                    // Create Transaction object based on the type
                    Transaction transaction = new Transaction(
                            retrievedIdTransaction, document, student, retrievedBorrowDate,
                            retrievedReturnDate, retrievedDueDate, retrievedReviewDate,
                            retrievedRating, retrievedReview);

                    // Add transaction to list
                    transactionList.add(transaction);
                }

                if (transactionList.isEmpty()) {
                    System.out.println("No transactions found ");
                }
            }
        } catch (SQLException e) {
            System.err.println("An error occurred while searching transactions by Student ID: " + e.getMessage());
        }

        return transactionList;
    }

    public static void clearTransactionTable() throws SQLException {
        Connection conn = connect();

        if (conn == null || conn.isClosed()) {
            throw new SQLException("Cannot clear transaction table, connection is closed or invalid.");
        }

        try {

            String clearTableSQL = "DELETE FROM TransactionDocument";

            Statement stmt = conn.createStatement();
            stmt.executeUpdate(clearTableSQL);

            System.out.println("All data in the TransactionDocument table has been cleared successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error occurred while clearing the TransactionDocument table.");
            throw e;
        } finally {
            conn.close(); // Đóng kết nối sau khi thực hiện
        }
    }


    public static void main(String[] args) throws SQLException {
//        String id = "23020714";
//        String name = "Nguyen Dinh Van";
//        String birthday = "2005-11-03";
//        String phone = "123456789";
//        String email = "vanit@gmail.com";
//        Student student = new Student(id, name, birthday, phone, email, id);
//
//        Book book = new Book("0101000200310002", "After Long Silence", "Helen Fremont", Book.BookCategory.FICTION);
//
//        Transaction transaction = new Transaction(book, student, "2024-11-12", null, "2024-12-12");
////        insertTransaction(transaction);
////        System.out.println(transaction.getId());
////          searchTransByStudent_id("23020675")
//            createTransactionTable();
//            insertTransaction(transaction);
//            updateReturnDate("7", "15-11-2025");
        clearTransactionTable();

    }
}
