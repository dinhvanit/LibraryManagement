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
            SELECT id, title, author, material, category, isbn
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

        con.close();
        return books;
    }

    public static void main(String[] args) {
        GetBooksByCategory getBooksByCategory = new GetBooksByCategory();

        try {
            // Lấy sách thuộc danh mục FICTION
            String category = "BIOGRAPHY";
            ObservableList<Document> books = getBooksByCategory.getBooks(category);

            System.out.println("Books retrieved for category: " + category);
            for (Document doc : books) {
                // Chuyển đổi sang đối tượng Book
                Book.BookCategory bookCategory = Book.BookCategory.valueOf(doc.getCategory().toUpperCase());
                Book book = new Book(doc.getId(), doc.getTitle(), doc.getAuthor(), bookCategory, doc instanceof Book ? ((Book) doc).getIsbn() : null);
                book.getInfo(book);
            }

//
//            String emptyCategory = "NonExistentCategory";
//            ObservableList<Document> fallbackBooks = getBooksByCategory.getBooks(emptyCategory);
//            System.out.println("\nBooks retrieved for fallback case (not enough books in category '" + emptyCategory + "'):");
//            for (Document doc : fallbackBooks) {
//                // Chuyển đổi sang đối tượng Book
//                Book.BookCategory bookCategory = Book.BookCategory.valueOf(doc.getCategory().toUpperCase());
//                Book book = new Book(doc.getId(), doc.getTitle(), doc.getAuthor(), bookCategory, doc instanceof Book ? ((Book) doc).getIsbn() : null);
//                book.getInfo(book);
//            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
