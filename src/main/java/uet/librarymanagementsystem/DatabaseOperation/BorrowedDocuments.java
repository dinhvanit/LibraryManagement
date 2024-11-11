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

    public static void main(String[] args) throws SQLException {
        createBorrowedDocumentsTable(); // Tạo bảng BorrowedDocuments

        try {
            // Mẫu giao dịch 1: Sinh viên mượn tài liệu với các thông tin giả định
            insertBorrowedDocument("23020675", "D001", "2024-01-15", "2024-01-22");

            // Mẫu giao dịch 2: Sinh viên khác mượn tài liệu
            insertBorrowedDocument("23020674", "D002", "2024-02-01", "2024-02-08");

            // Mẫu giao dịch 3: Sinh viên khác mượn tài liệu và trả muộn
            insertBorrowedDocument("23020000", "D003", "2024-02-05", "2024-02-20");

            // Mẫu giao dịch 4: Sinh viên mượn tài liệu nhưng chưa trả
            insertBorrowedDocument("23020781", "D004", "2024-03-01", null); // Giá trị null cho ngày trả

            // Mẫu giao dịch 5: Một sinh viên khác mượn lại cùng tài liệu
            insertBorrowedDocument("23020981", "D001", "2024-03-10", "2024-03-17");

        } catch (SQLException e) {
            System.err.println("Error during sample transactions: " + e.getMessage());
        }

    }
}
