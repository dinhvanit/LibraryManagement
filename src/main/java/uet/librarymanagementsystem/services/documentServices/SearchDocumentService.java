package uet.librarymanagementsystem.services.documentServices;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import uet.librarymanagementsystem.DatabaseOperation.DatabaseManager;
import uet.librarymanagementsystem.entity.documents.Document;
import uet.librarymanagementsystem.entity.documents.DocumentFactory;

import java.sql.*;

public class SearchDocumentService {

    private Connection conn;

    public SearchDocumentService() {
        conn = DatabaseManager.connect();
    }

    public ObservableList<Document> searchAll(
            String title, String author, String material, String category) throws SQLException {

        ObservableList<Document> documentListSearchResult = FXCollections.observableArrayList();
        StringBuilder query = new StringBuilder(
                "SELECT id, title, author, material, category, isbn FROM Document WHERE 1=1");

        if (title != null && !title.isEmpty()) {
            query.append(" AND title LIKE ?");
        }
        if (author != null && !author.isEmpty()) {
            query.append(" AND author LIKE ?");
        }
        if (material != null && !material.isEmpty()) {
            query.append(" AND material = ?");
        }
        if (category != null && !category.isEmpty()) {
            query.append(" AND category = ?");
        }

        try (PreparedStatement pstmt = conn.prepareStatement(query.toString())) {
            int paramIndex = 1;

            if (title != null && !title.isEmpty()) {
                pstmt.setString(paramIndex++, "%" + title + "%");
            }
            if (author != null && !author.isEmpty()) {
                pstmt.setString(paramIndex++, "%" + author + "%");
            }
            if (material != null && !material.isEmpty()) {
                pstmt.setString(paramIndex++, material);
            }
            if (category != null && !category.isEmpty()) {
                pstmt.setString(paramIndex++, category);
            }

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    String retrievedId = rs.getString("id");
                    String retrievedTitle = rs.getString("title");
                    String retrievedAuthor = rs.getString("author");
                    String retrievedMaterial = rs.getString("material");
                    String retrievedCategory = rs.getString("category");
                    String retrievedISBN = rs.getString("isbn");
                    Document document = DocumentFactory.createDocument(
                            retrievedId,
                            retrievedTitle,
                            retrievedAuthor,
                            retrievedMaterial,
                            retrievedCategory,
                            retrievedISBN
                    );
                    documentListSearchResult.add(document);
                }
            }
        }
        return documentListSearchResult;
    }

    // search nhung sach chua muon
    public ObservableList<Document> searchByNotNull(
            String title, String author, String material, String category) throws SQLException {

        ObservableList<Document> documentListSearchResult = FXCollections.observableArrayList();
        // Câu truy vấn SQL
        StringBuilder query = new StringBuilder(
                "SELECT DISTINCT document.id, document.title, document.author, document.material, document.category, document.isbn " +
                        "FROM Document document " +
                        "LEFT JOIN TransactionDocument t ON document.id = t.id_document " +
                        "WHERE 1=1");

        // Điều kiện loại bỏ tài liệu đã được mượn
        query.append(" AND (NOT EXISTS (" +
                "SELECT 1 FROM TransactionDocument t2 " +
                "WHERE t2.id_document = document.id AND t2.return_date IS NULL))");
        if (title != null && !title.isEmpty()) {
            query.append(" AND title LIKE ?");
        }
        if (author != null && !author.isEmpty()) {
            query.append(" AND author LIKE ?");
        }
        if (material != null && !material.isEmpty()) {
            query.append(" AND material = ?");
        }
        if (category != null && !category.isEmpty()) {
            query.append(" AND category = ?");
        }

        try (PreparedStatement pstmt = conn.prepareStatement(query.toString())) {
            int paramIndex = 1;

            if (title != null && !title.isEmpty()) {
                pstmt.setString(paramIndex++, "%" + title + "%");
            }
            if (author != null && !author.isEmpty()) {
                pstmt.setString(paramIndex++, "%" + author + "%");
            }
            if (material != null && !material.isEmpty()) {
                pstmt.setString(paramIndex++, material);
            }
            if (category != null && !category.isEmpty()) {
                pstmt.setString(paramIndex++, category);
            }

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    String retrievedId = rs.getString("id");
                    String retrievedTitle = rs.getString("title");
                    String retrievedAuthor = rs.getString("author");
                    String retrievedMaterial = rs.getString("material");
                    String retrievedCategory = rs.getString("category");
                    String retrievedISBN = rs.getString("isbn");
                    Document document = DocumentFactory.createDocument(
                            retrievedId,
                            retrievedTitle,
                            retrievedAuthor,
                            retrievedMaterial,
                            retrievedCategory,
                            retrievedISBN
                    );
                    documentListSearchResult.add(document);
                }
            }
        }
        return documentListSearchResult;
    }
}
