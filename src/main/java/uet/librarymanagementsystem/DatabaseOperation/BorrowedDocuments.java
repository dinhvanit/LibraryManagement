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
                student_id INTEGER NOT NULL,
                isbn INTEGER NOT NULL,
                borrow_date DATE,
                return_date DATE,
                FOREIGN KEY (student_id) REFERENCES Student (student_id) ON DELETE CASCADE ON UPDATE CASCADE,
                FOREIGN KEY (isbn) REFERENCES Document (isbn) ON DELETE CASCADE ON UPDATE CASCADE
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
    public static void insertBorrowedDocument(int studentId, int isbn, String borrowDate, String returnDate) throws SQLException {
        Connection con = connect();
        if (con == null || con.isClosed()) {
            throw new SQLException("Cannot create author, connection is closed or invalid.");
        }
        String insertSQL = "INSERT INTO BorrowedDocuments (student_id, isbn, borrow_date, return_date) VALUES (?, ?, ?, ?)";

        PreparedStatement pstmt = con.prepareStatement(insertSQL);
        try {
            pstmt.setInt(1, studentId);
            pstmt.setInt(2, isbn);
            pstmt.setString(3, borrowDate);
            pstmt.setString(4, returnDate);
            pstmt.executeUpdate();
            System.out.println("Borrowed document added successfully.");
        } catch (SQLException e) {
            System.err.println("Error inserting borrowed document: " + e.getMessage());
        }
    }

    public static void main(String[] args) throws SQLException {
        createBorrowedDocumentsTable(); // Tạo bảng BorrowedDocuments

        // 1 vài giao dịch mẫu, sau sẽ thêm từ bảng student và doc
        insertBorrowedDocument(1, 101, "2024-12-01", "2024-15-1");
        insertBorrowedDocument(2, 102, null, null);
        insertBorrowedDocument(1, 103, "2024-11-15", "2025-1-1");
    }
}
