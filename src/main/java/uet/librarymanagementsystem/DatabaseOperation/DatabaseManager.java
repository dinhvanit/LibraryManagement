package uet.librarymanagementsystem.DatabaseOperation;

import java.sql.*;

/**
 * This class provides utility methods to manage database operations, including connecting to the SQLite database,
 * printing table columns, and dropping specific tables.
 */
public class DatabaseManager {

    // Database connection URL
    private static final String url = "jdbc:sqlite:" + System.getProperty("user.dir") + "\\src\\main\\resources\\uet\\librarymanagementsystem\\Database\\library.db";

    /**
     * Establishes a connection to the SQLite database.
     *
     * @return A {@link Connection} object representing the connection to the database.
     */
    public static Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
            //System.out.println("QLite has been established.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    /**
     * Prints the columns of a specified table in the database.
     * This method retrieves metadata about the table and its columns and displays column names, types, and sizes.
     *
     * @param tableName The name of the table whose columns need to be printed.
     * @throws SQLException If a database access error occurs or the SQL statement fails.
     */
    public static void printTableColumns(String tableName) throws SQLException {
        Connection con = connect();
        if (con == null || con.isClosed()) {
            throw new SQLException("Cannot retrieve columns, connection is closed or invalid.");
        }

        DatabaseMetaData metaData = con.getMetaData();

        // Check if the table exists
        ResultSet tables = metaData.getTables(null, null, tableName, null);
        if (!tables.next()) {
            System.out.println("Table " + tableName + " does not exist.");
            tables.close();
            con.close();
            return;
        }

        // Retrieve and print column information
        ResultSet columns = metaData.getColumns(null, null, tableName, null);
        System.out.println("Columns in table " + tableName + ":");
        while (columns.next()) {
            String columnName = columns.getString("COLUMN_NAME");
            String columnType = columns.getString("TYPE_NAME");
            int columnSize = columns.getInt("COLUMN_SIZE");

            System.out.println("Column: " + columnName + " | Type: " + columnType + " | Size: " + columnSize);
        }

        columns.close();
        con.close();
    }

    /**
     * Drops the "Material" table from the database if it exists.
     *
     * @throws SQLException If a database access error occurs or the SQL statement fails.
     */
    public static void dropTableMaterial() throws SQLException {
        Connection con = connect();

        try (con; Statement statement = con.createStatement()) {
            if (con == null || con.isClosed()) {
                throw new SQLException("Cannot drop table, connection is closed or invalid.");
            }
            String dropSQL = "DROP TABLE IF EXISTS Material";
            statement.executeUpdate(dropSQL);
            System.out.println("Table Material has been dropped successfully.");
        } catch (SQLException e) {
            System.err.println("Error dropping table: " + e.getMessage());
        }
    }

    /**
     * Drops the "Title" table from the database if it exists.
     *
     * @throws SQLException If a database access error occurs or the SQL statement fails.
     */
    public static void dropTableTitle() throws SQLException {
        Connection con = connect();

        try (con; Statement statement = con.createStatement()) {
            if (con == null || con.isClosed()) {
                throw new SQLException("Cannot drop table, connection is closed or invalid.");
            }
            String dropSQL = "DROP TABLE IF EXISTS Title";
            statement.executeUpdate(dropSQL);
            System.out.println("Table Title has been dropped successfully.");
        } catch (SQLException e) {
            System.err.println("Error dropping table: " + e.getMessage());
        }
    }

    /**
     * Drops the "Category" table from the database if it exists.
     *
     * @throws SQLException If a database access error occurs or the SQL statement fails.
     */
    public static void dropTableCategory() throws SQLException {
        Connection con = connect();

        try (con; Statement statement = con.createStatement()) {
            if (con == null || con.isClosed()) {
                throw new SQLException("Cannot drop table, connection is closed or invalid.");
            }
            String dropSQL = "DROP TABLE IF EXISTS Category";
            statement.executeUpdate(dropSQL);
            System.out.println("Table Category has been dropped successfully.");
        } catch (SQLException e) {
            System.err.println("Error dropping table: " + e.getMessage());
        }
    }

    /**
     * Drops the "TransactionDocument" table from the database if it exists.
     *
     * @throws SQLException If a database access error occurs or the SQL statement fails.
     */
    public static void dropTableTransaction() throws SQLException {
        Connection con = connect();

        try (con; Statement statement = con.createStatement()) {
            if (con == null || con.isClosed()) {
                throw new SQLException("Cannot drop table, connection is closed or invalid.");
            }
            String dropSQL = "DROP TABLE IF EXISTS TransactionDocument";
            statement.executeUpdate(dropSQL);
            System.out.println("Table Transaction has been dropped successfully.");
        } catch (SQLException e) {
            System.err.println("Error dropping table: " + e.getMessage());
        }
    }

    /**
     * Renames the column "ID" to "id" in the "Author" table.
     *
     * @throws SQLException If a database access error occurs or the SQL statement fails.
     */
    public static void doitencot() {
        Connection con = connect();

        try (con; Statement statement = con.createStatement()) {
            if (con == null || con.isClosed()) {
                throw new SQLException("Cannot change column in table, connection is closed or invalid.");
            }
            String changeSQL = "ALTER TABLE Author RENAME COLUMN ID TO id";
            statement.executeUpdate(changeSQL);
            System.out.println("Table Author has been changed name successfully.");
        } catch (SQLException e) {
            System.err.println("Error changing table: " + e.getMessage());
        }
    }
}