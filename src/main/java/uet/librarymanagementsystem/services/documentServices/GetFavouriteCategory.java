package uet.librarymanagementsystem.services.documentServices;

import uet.librarymanagementsystem.DatabaseOperation.DatabaseManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GetFavouriteCategory {
    public static String getMostFrequentCategory(String id_student) throws SQLException {
        Connection con = DatabaseManager.connect();

        String querySQL = """
            SELECT category, COUNT(category) AS count
            FROM TransactionDocument
            WHERE id_student = ? AND material = 'BOOK'
            GROUP BY category
            ORDER BY count DESC
            LIMIT 1;
        """;

        try (PreparedStatement stmt = con.prepareStatement(querySQL)) {
            stmt.setString(1, id_student);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getString("category");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }

        return "FICTION"; //day la the loai nhieu book nhat
    }

    public static void main(String[] args) throws SQLException {
        System.out.println(getMostFrequentCategory("123"));
    }
}

