package uet.librarymanagementsystem.services.documentServices;

import uet.librarymanagementsystem.DatabaseOperation.DatabaseManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeleteDocumentService {

    private Connection conn;

    public DeleteDocumentService() {
        conn = DatabaseManager.connect();
    }

    public void deleteDocument(String id, String material, String category, String title, String author) {
        if (id == null || id.isEmpty()) {
            System.out.println("ID không được để trống.");
            return;
        }

        StringBuilder query = new StringBuilder("DELETE FROM Document WHERE id = ?");

        if (material != null && !material.isEmpty()) {
            query.append(" AND material = ?");
        }
        if (category != null && !category.isEmpty()) {
            query.append(" AND category = ?");
        }
        if (title != null && !title.isEmpty()) {
            query.append(" AND title LIKE ?");
        }
        if (author != null && !author.isEmpty()) {
            query.append(" AND author LIKE ?");
        }

        try (PreparedStatement pstmt = conn.prepareStatement(query.toString())) {
            int paramIndex = 1;

            // Thiết lập tham số cho câu lệnh xóa
            pstmt.setString(paramIndex++, id);

            if (material != null && !material.isEmpty()) {
                pstmt.setString(paramIndex++, material);
            }
            if (category != null && !category.isEmpty()) {
                pstmt.setString(paramIndex++, category);
            }
            if (title != null && !title.isEmpty()) {
                pstmt.setString(paramIndex++, "%" + title + "%");
            }
            if (author != null && !author.isEmpty()) {
                pstmt.setString(paramIndex++, "%" + author + "%");
            }

            // Thực thi câu lệnh xóa
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Xóa tài liệu thành công.");
            } else {
                System.out.println("Không tìm thấy tài liệu phù hợp để xóa.");
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi xóa tài liệu: " + e.getMessage());
        }
    }
}