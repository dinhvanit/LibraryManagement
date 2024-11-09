package uet.librarymanagementsystem.services.documentServices;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddDocument {
    private Connection conn;

    public AddDocument(Connection connection) {
        this.conn = connection;
    }

    public void add(String title, String author, String material, String category) {

        StringBuilder query = new StringBuilder("SELECT id, title, author, material, category FROM Document WHERE 1=1");
        try (PreparedStatement pstmt = conn.prepareStatement(query.toString())) {

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
