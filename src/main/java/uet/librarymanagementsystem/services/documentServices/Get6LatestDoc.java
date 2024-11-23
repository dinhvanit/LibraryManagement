/*
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
        if (con == null || con.isClosed()) {
            throw new SQLException("Cannot fetch titles, connection is closed or invalid.");
        }

        String querySQL = "SELECT id, name, isbn FROM Title ORDER BY id DESC LIMIT 6";
        ObservableList<Document> latestTitles = FXCollections.observableArrayList();

        try (PreparedStatement stmt = con.prepareStatement(querySQL)) {
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String id = rs.getString("id");
                String name = rs.getString("name");
                String isbn = rs.getString("isbn");

                Document document = DocumentFactory.createDocument(id, name, null, null, null, isbn);
                latestTitles.add(document);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error occurred while fetching the latest titles.");
            throw e;
        } finally {
            con.close();
        }

        return latestTitles;
    }

    public static void main(String[] args) {
        try {
            ObservableList<Document> latestTitles = Get6LatestDoc.getLatestTitles();

            System.out.println("6 Latest Titles:");
            for (Document doc : latestTitles) {
                System.out.println("ID: " + doc.getId() + ", Name: " + doc.getTitle());
                if (doc instanceof Book book) {
                    if (book.getIsbn() != null) {
                        System.out.println(book.getIsbn());
                    } else {
                        System.out.println("khong co isbn");
                    }
                } else {
                    System.out.println("khong phai sach");
                }
            }
        } catch (Exception e) {
            System.err.println("An error occurred while fetching the latest titles:");
            e.printStackTrace();
        }
    }
}
*/
