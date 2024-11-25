package uet.librarymanagementsystem.DatabaseOperation;

import java.sql.*;
import static uet.librarymanagementsystem.DatabaseOperation.DatabaseManager.connect;

/**
 * This class handles operations related to the Author table in the database.
 * It includes methods to create the table, insert an author, and clear all data from the table.
 */
public class AuthorTable {

    /**
     * Creates the Author table in the database if it doesn't already exist.
     * The table consists of an auto-incrementing id and a name field for the author's name.
     *
     * @throws SQLException If a database access error occurs or the SQL statement fails.
     */
    public static void createAuthorTable() throws SQLException {
        Connection con = connect();
        if (con == null || con.isClosed()) {
            throw new SQLException("Cannot create author, connection is closed or invalid.");
        }
        Statement statement = con.createStatement();

        String createTableSQL = "CREATE TABLE IF NOT EXISTS Author (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name VARCHAR(255) NOT NULL" +
                ")";

        statement.execute(createTableSQL);
        con.close();
    }

    /**
     * Inserts a new author into the Author table.
     * Before inserting, it checks if the author already exists in the database.
     * If the author already exists, it will not insert and prints a message.
     *
     * @param authorName The name of the author to be inserted.
     * @throws SQLException If a database access error occurs or the SQL statement fails.
     */
    public static void insertAuthor(String authorName) throws SQLException {
        Connection con = connect();
        if (con == null || con.isClosed()) {
            throw new SQLException("Cannot insert author, connection is closed or invalid.");
        }

        // Check if the author already exists in the database
        String checkSQL = "SELECT COUNT(*) FROM Author WHERE name = ?";
        try (PreparedStatement checkStmt = con.prepareStatement(checkSQL)) {
            checkStmt.setString(1, authorName);
            ResultSet rs = checkStmt.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                System.out.println("Author already exists in the database.");
                con.close();
                return;
            }
        }

        // Insert the new author into the Author table
        String insertSQL = "INSERT INTO Author (name) VALUES (?)";
        try (PreparedStatement insertStmt = con.prepareStatement(insertSQL)) {
            insertStmt.setString(1, authorName);
            insertStmt.executeUpdate();
            System.out.println("Author inserted successfully.");
        }
        con.close();
    }

    /**
     * Clears all data from the Author table.
     * This method will delete all records in the Author table.
     *
     * @throws SQLException If a database access error occurs or the SQL statement fails.
     */
    public static void clearAuthorTable() throws SQLException {
        Connection con = connect();

        if (con == null || con.isClosed()) {
            throw new SQLException("Cannot clear author table, connection is closed or invalid.");
        }

        try {
            String clearTableSQL = "DELETE FROM Author";

            Statement statement = con.createStatement();
            statement.executeUpdate(clearTableSQL);

            System.out.println("All data in the Author table has been cleared successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error occurred while clearing the Author table.");
            throw e;
        } finally {
            con.close();
        }
    }

//    public static void main(String []args) throws SQLException {
//        clearAuthorTable();
//    }
}
