package uet.librarymanagementsystem.services.documentServices;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import uet.librarymanagementsystem.DatabaseOperation.DatabaseManager;
import uet.librarymanagementsystem.entity.documents.Document;
import uet.librarymanagementsystem.entity.documents.DocumentFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GetBooksByCategory {
    public ObservableList<Document> getBooks(String category) throws SQLException {
        Connection con = DatabaseManager.connect();

        ObservableList<Document> books = FXCollections.observableArrayList();

        // Truy vấn sách theo category được yêu cầu
        String queryPrimarySQL = """
            SELECT id, name, material, category, isbn
            FROM Title
            WHERE material = 'BOOK' AND category = ?
            LIMIT 12;
        """;

        try (PreparedStatement stmtPrimary = con.prepareStatement(queryPrimarySQL)) {
            stmtPrimary.setString(1, category);
            ResultSet rsPrimary = stmtPrimary.executeQuery();

            while (rsPrimary.next()) {
                books.add(DocumentFactory.createDocument(
                        rsPrimary.getString("id"),
                        rsPrimary.getString("name"),
                        null,
                        rsPrimary.getString("material"),
                        rsPrimary.getString("category"),
                        rsPrimary.getString("isbn")
                ));
            }
        }

        if (books.size() < 12) {
            String queryFallbackSQL = """
                SELECT id, name, material, category, isbn
                FROM Title
                WHERE material = 'BOOK' AND category != ?
                LIMIT ?;
            """;

            try (PreparedStatement stmtFallback = con.prepareStatement(queryFallbackSQL)) {
                stmtFallback.setString(1, category);
                stmtFallback.setInt(2, 12 - books.size()); // Số lượng còn thiếu
                ResultSet rsFallback = stmtFallback.executeQuery();

                while (rsFallback.next()) {
                    books.add(DocumentFactory.createDocument(
                            rsFallback.getString("id"),
                            rsFallback.getString("name"),
                            null,
                            rsFallback.getString("material"),
                            rsFallback.getString("category"),
                            rsFallback.getString("isbn")
                    ));
                }
            }
        }

        return books;
    }

    public static void main(String[] args) {
        GetBooksByCategory getBooksByCategory = new GetBooksByCategory();

        try {

            String category = "FICTION";
            ObservableList<Document> books = getBooksByCategory.getBooks(category);

            System.out.println("Books retrieved for category: " + category);
            books.forEach(System.out::println);

            String emptyCategory = "NonExistentCategory";
            ObservableList<Document> fallbackBooks = getBooksByCategory.getBooks(emptyCategory);
            System.out.println("\nBooks retrieved for fallback case (not enough books in category '" + emptyCategory + "'):");
            fallbackBooks.forEach(System.out::println);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
