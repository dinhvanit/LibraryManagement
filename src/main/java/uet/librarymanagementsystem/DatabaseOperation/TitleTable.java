package uet.librarymanagementsystem.DatabaseOperation;

import java.sql.*;
import static uet.librarymanagementsystem.DatabaseOperation.DatabaseManager.connect;

/**
 * This class provides operations for managing the "Title" table in the database.
 * It includes methods for creating, inserting, clearing, and dropping the Title table.
 */
public class TitleTable {

    /**
     * Creates the "Title" table in the database if it does not exist.
     * The table has two columns: id (auto-incremented primary key) and name (non-null string).
     * @throws SQLException If a database access error occurs.
     */
    public static void createTitleTable() throws SQLException {
        Connection con = connect();
        if (con == null || con.isClosed()) {
            throw new SQLException("Cannot create title, connection is closed or invalid.");
        }
        Statement statement = con.createStatement();

        String createTableSQL = "CREATE TABLE IF NOT EXISTS Title (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name VARCHAR(255) NOT NULL" +
                ")";

        statement.execute(createTableSQL);
        System.out.println("Table 'Title' created successfully.");
        con.close();
    }

    /**
     * Inserts a new title into the "Title" table if it does not already exist.
     * It first checks if a title with the same name is already present in the database.
     * If it exists, a message is displayed and no insertion occurs.
     * @param titleName The name of the title to be inserted.
     * @throws SQLException If a database access error occurs.
     */
    public static void insertTitle(String titleName) throws SQLException {
        Connection con = connect();
        if (con == null || con.isClosed()) {
            throw new SQLException("Cannot insert title, connection is closed or invalid.");
        }

        try {
            String checkTitleSQL = "SELECT COUNT(*) FROM Title WHERE name = ?";
            try (PreparedStatement checkStmt = con.prepareStatement(checkTitleSQL)) {
                checkStmt.setString(1, titleName);
                ResultSet rs = checkStmt.executeQuery();
                if (rs.next() && rs.getInt(1) > 0) {
                    System.out.println("TITLE with the same name already exists in the database.");
                    return;
                }
            }

            // Insert the new title into the Title table
            String insertSQL = "INSERT INTO Title (name) VALUES (?)";
            try (PreparedStatement insertStmt = con.prepareStatement(insertSQL)) {
                insertStmt.setString(1, titleName);
                insertStmt.executeUpdate();
                System.out.println("TITLE inserted successfully.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error occurred while inserting TITLE.");
        } finally {
            con.close();
        }
    }

    /**
     * Clears all data from the "Title" table in the database.
     * This operation deletes all records but keeps the table structure intact.
     * @throws SQLException If a database access error occurs.
     */
    public static void clearTitleTable() throws SQLException {
        Connection con = connect(); // Connect to the database

        if (con == null || con.isClosed()) {
            throw new SQLException("Cannot clear title table, connection is closed or invalid.");
        }

        try {
            // SQL command to delete all data in the Title table
            String clearTableSQL = "DELETE FROM Title";

            Statement statement = con.createStatement();
            statement.executeUpdate(clearTableSQL);

            System.out.println("All data in the Title table has been cleared successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error occurred while clearing the Title table.");
            throw e;
        } finally {
            con.close(); // Ensure the connection is closed after execution
        }
    }

    /**
     * Drops the "Title" table from the database if it exists.
     * This operation permanently deletes the table and its data.
     * @throws SQLException If a database access error occurs.
     */
    public static void dropTitleTable() throws SQLException {
        Connection con = connect();
        if (con == null || con.isClosed()) {
            throw new SQLException("Cannot drop table, connection is closed or invalid.");
        }
        Statement statement = con.createStatement();

        String dropTableSQL = "DROP TABLE IF EXISTS Title";

        statement.execute(dropTableSQL);
        System.out.println("Table 'Title' has been dropped successfully.");

        con.close();
    }
}


/*    public static void main(String []args) throws SQLException {
//        createTitleTable();
        clearTitleTable();
//          dropTitleTable();
}*/
