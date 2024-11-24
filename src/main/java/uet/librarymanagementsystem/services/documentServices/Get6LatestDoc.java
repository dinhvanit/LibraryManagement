package uet.librarymanagementsystem.services.documentServices;

import javafx.collections.ObservableList;
import uet.librarymanagementsystem.DatabaseOperation.DatabaseManager;
import uet.librarymanagementsystem.entity.documents.Document;
import javafx.collections.FXCollections;
import uet.librarymanagementsystem.entity.documents.DocumentFactory;
import uet.librarymanagementsystem.entity.documents.materials.Book;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Get6LatestDoc {
    public static ObservableList<Document> getLatestTitles() throws SQLException {
        Connection con = DatabaseManager.connect();
        ObservableList<Document> latestTitles = FXCollections.observableArrayList();

        String querySQL = """
        SELECT id, title, author, material, category, isbn
        FROM Document
        WHERE ROWID IN (
            SELECT MAX(ROWID)
            FROM Document
            WHERE material = 'BOOK'
            GROUP BY SUBSTRING(id, 1, 12), title, author, material, category, isbn
        )
        ORDER BY ROWID DESC
        LIMIT 6;
    """;

        try (PreparedStatement stmt = con.prepareStatement(querySQL)) {
            ResultSet rs = stmt.executeQuery();

            // Duyệt qua các kết quả trả về
            while (rs.next()) {
                String id = rs.getString("id");
                String title = rs.getString("title");
                String author = rs.getString("author");
                String material = rs.getString("material");
                String category = rs.getString("category");
                String isbn = rs.getString("isbn");

                // Tạo đối tượng Document từ DocumentFactory
                Document document = DocumentFactory.createDocument(id, title, author, material, category, isbn);
                latestTitles.add(document);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error occurred while fetching the latest titles.");
            throw e;
        } finally {
            con.close(); // Đảm bảo kết nối được đóng
        }

        return latestTitles;
    }

}
