package uet.librarymanagementsystem.DatabaseOperation;
import java.sql.*;
import java.util.Scanner;

import static uet.librarymanagementsystem.DatabaseOperation.DatabaseManager.connect;

public class AuthorTable {
    public static void createAuthorTable() throws SQLException {
        Connection con = connect();
        if (con == null || con.isClosed()) {
            throw new SQLException("Cannot create author, connection is closed or invalid.");
        }
        Statement statement = con.createStatement();

        String createTableSQL = "CREATE TABLE IF NOT EXISTS Author (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name VARCHAR(50) NOT NULL" +
                ")";

        statement.execute(createTableSQL);
        con.close();
    }

    public static void insertAuthorFromKeyboard() throws SQLException {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter author name: ");
        String authorName = scanner.nextLine();

        Connection con = connect();
        if (con == null || con.isClosed()) {
            throw new SQLException("Cannot insert author, connection is closed or invalid.");
        }

        String insertSQL = "INSERT INTO Author (name) VALUES (?)";
        PreparedStatement preparedStatement = con.prepareStatement(insertSQL);

        preparedStatement.setString(1, authorName);
        preparedStatement.executeUpdate();

        System.out.println("Author inserted successfully.");
        con.close();
    }

    public static void main(String []args) throws SQLException {
//        try {
//            for (int i = 1; i <= 5; i++) {
//                System.out.println("Enter details for author " + i + ":");
//                AuthorTable.insertAuthorFromKeyboard();
//            }
//            System.out.println("Successfully inserted 5 authors.");
//        } catch (SQLException e) {
//            System.err.println("Error inserting author: " + e.getMessage());
//        }

        DatabaseManager.printTableColumns("Author");
    }
}