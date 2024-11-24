package uet.librarymanagementsystem.DatabaseOperation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import static uet.librarymanagementsystem.DatabaseOperation.DatabaseManager.connect;

public class BorrowedDocuments {
    public static void createBorrowedDocumentsTable() throws SQLException {
        Connection con = connect();
        if (con == null || con.isClosed()) {
            throw new SQLException("Cannot create author, connection is closed or invalid.");
        }
        Statement statement = con.createStatement();
        String createTableSQL = """
            CREATE TABLE IF NOT EXISTS BorrowedDocuments (
                borrowed_id INTEGER PRIMARY KEY AUTOINCREMENT,
                student_id VARCHAR(255) NOT NULL,
                document_id VARCHAR(255) NOT NULL,
                borrow_date DATE,
                return_date DATE,
                FOREIGN KEY (student_id) REFERENCES Student (student_id) ON DELETE CASCADE ON UPDATE CASCADE,
                FOREIGN KEY (document_id) REFERENCES Document (document_id) ON DELETE CASCADE ON UPDATE CASCADE
            );
            """;
        try {
            statement.execute(createTableSQL);
            System.out.println("Table BorrowedDocuments has been created successfully.");
        } catch (SQLException e) {
            System.err.println("Error creating table: " + e.getMessage());
        }
        con.close();
    }

    public static void insertBorrowedDocument(String student_id, String document_id, String borrowDate, String returnDate) throws SQLException {
        Connection con = connect();
        if (con == null || con.isClosed()) {
            throw new SQLException("Cannot create author, connection is closed or invalid.");
        }
        String insertSQL = "INSERT INTO BorrowedDocuments (student_id, document_id, borrow_date, return_date) VALUES (?, ?, ?, ?)";

        PreparedStatement pstmt = con.prepareStatement(insertSQL);
        try {
            pstmt.setString(1, student_id);
            pstmt.setString(2, document_id);
            pstmt.setString(3, borrowDate);
            pstmt.setString(4, returnDate);
            pstmt.executeUpdate();
            System.out.println("Borrowed document added successfully.");
        } catch (SQLException e) {
            System.err.println("Error inserting borrowed document: " + e.getMessage());
        }
    }

//    public static void main(String[] args) throws SQLException {
//        createBorrowedDocumentsTable(); // Tạo bảng BorrowedDocuments
//
//        try {
//
//
//        } catch (SQLException e) {
//            System.err.println("Error during sample transactions: " + e.getMessage());
//        }
//
//    }
}
