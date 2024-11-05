package uet.librarymanagementsystem.services.documentServices;

import uet.librarymanagementsystem.entity.documents.Document;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BorrowDocument {

    private Connection conn;

    public BorrowDocument(Connection connection) {
        this.conn = connection;
    }

    public void borrow(Document document) throws SQLException {
        // Giả sử bạn có bảng Borrow để lưu thông tin mượn
        // Tạo bảng liên kết với danh sách user để biết học sinh này đang mượn những quyển nào

        String query = "INSERT INTO Borrow (user_id, document_id) VALUES (?, ?)"; // Thay thế thông tin theo thực tế
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, "USER_ID");
            pstmt.setString(2, document.getId()); // Thay thế USER_ID bằng ID người dùng thực tế
            pstmt.executeUpdate();
        }
    }
}
