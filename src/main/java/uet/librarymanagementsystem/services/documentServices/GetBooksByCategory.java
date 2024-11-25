package uet.librarymanagementsystem.services.documentServices;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import uet.librarymanagementsystem.DatabaseOperation.DatabaseManager;
import uet.librarymanagementsystem.entity.documents.Document;
import uet.librarymanagementsystem.entity.documents.DocumentFactory;
import uet.librarymanagementsystem.entity.documents.materials.Book;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GetBooksByCategory {
    public ObservableList<Document> getBooks(String category) throws SQLException {
        Connection con = DatabaseManager.connect();
        ObservableList<Document> books = FXCollections.observableArrayList();

        String queryPrimarySQL = """
        SELECT MIN(id) AS id, title, author, material, category, isbn
        FROM Document
        WHERE material = 'BOOK' AND category = ?
        GROUP BY SUBSTRING(id, 1, 12), title, author, material, category, isbn
        LIMIT 18;
    """;

        try (PreparedStatement stmtPrimary = con.prepareStatement(queryPrimarySQL)) {
            stmtPrimary.setString(1, category);
            ResultSet rsPrimary = stmtPrimary.executeQuery();

            while (rsPrimary.next()) {
                books.add(DocumentFactory.createDocument(
                        rsPrimary.getString("id"),
                        rsPrimary.getString("title"),
                        rsPrimary.getString("author"),
                        rsPrimary.getString("material"),
                        rsPrimary.getString("category"),
                        rsPrimary.getString("isbn")
                ));
            }
        }

        int currentCount = books.size();
        if (currentCount < 18) {
            int additionalCount = 18 - currentCount;
            String queryFallbackSQL = """
            SELECT MIN(id) AS id, title, author, material, category, isbn
            FROM Document
            WHERE material = 'BOOK'
            GROUP BY SUBSTRING(id, 1, 12), title, author, material, category, isbn
            HAVING SUBSTRING(id, 1, 12) NOT IN (
                SELECT DISTINCT SUBSTRING(id, 1, 12)
                FROM Document
                WHERE material = 'BOOK' AND category = ?
            )
            LIMIT ?;
        """;

            try (PreparedStatement stmtFallback = con.prepareStatement(queryFallbackSQL)) {
                stmtFallback.setString(1, category);
                stmtFallback.setInt(2, additionalCount);
                ResultSet rsFallback = stmtFallback.executeQuery();

                while (rsFallback.next()) {
                    books.add(DocumentFactory.createDocument(
                            rsFallback.getString("id"),
                            rsFallback.getString("title"),
                            rsFallback.getString("author"),
                            rsFallback.getString("material"),
                            rsFallback.getString("category"),
                            rsFallback.getString("isbn")
                    ));
                }
            }
        }

        con.close();
        return books;
    }

}
