package uet.librarymanagementsystem.services.documentServices;

import javafx.collections.ObservableList;
import uet.librarymanagementsystem.DatabaseOperation.DatabaseManager;
import uet.librarymanagementsystem.entity.documents.Document;
import javafx.collections.FXCollections;
import uet.librarymanagementsystem.entity.documents.DocumentFactory;
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

}
