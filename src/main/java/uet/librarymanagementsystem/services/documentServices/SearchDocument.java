package uet.librarymanagementsystem.services.documentServices;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import uet.librarymanagementsystem.DatabaseOperation.DatabaseManager;
import uet.librarymanagementsystem.DatabaseOperation.DocumentDO;
import uet.librarymanagementsystem.entity.documents.Document;
import uet.librarymanagementsystem.entity.documents.DocumentFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SearchDocument {

    private Connection conn;

    public SearchDocument() {
        conn = DatabaseManager.connect();
    }

    public ObservableList<Document> search(String title, String author, String material, String category) throws SQLException {
        System.out.println("lan luot cac chi so can tim là " + title);
        System.out.println(author);
        System.out.println(material);
        System.out.println(category);

        ObservableList<Document> documentListSearchResult = FXCollections.observableArrayList();
        StringBuilder query = new StringBuilder("SELECT id, title, author, material, category FROM Document WHERE 1=1");

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
                    String retrievedTitle = rs.getString("title");
                    String retrievedAuthor = rs.getString("author");
                    String retrievedMaterial = rs.getString("material");
                    String retrievedCategory = rs.getString("category");

                    System.out.println("Title: " + retrievedTitle);
                    System.out.println("Author: " + retrievedAuthor);
                    System.out.println("Material: " + retrievedMaterial);
                    System.out.println("Category: " + retrievedCategory);

                    Document document = DocumentFactory.createDocument(
                            retrievedTitle,
                            retrievedAuthor,
                            retrievedMaterial,
                            retrievedCategory
                    );
                    documentListSearchResult.add(document);
                }
            }
        }

        return documentListSearchResult;
    }
}
