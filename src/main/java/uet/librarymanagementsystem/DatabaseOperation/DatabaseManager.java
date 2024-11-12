package uet.librarymanagementsystem.DatabaseOperation;

import java.sql.*;

public class DatabaseManager {

    private static final String url = "jdbc:sqlite:" + System.getProperty("user.dir") + "\\src\\main\\resources\\uet\\librarymanagementsystem\\Database\\library.db";

    //private static final String url = "Database/library.db";
    public static Connection connect() {
        Connection conn = null;
        try {      // relative path
            conn = DriverManager.getConnection(url);
            System.out.println("Connection to SQLite has been established.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    // ham nay dung de in ra cac cot co trong 1 bang
    public static void printTableColumns(String tableName) throws SQLException {
        Connection con = connect();
        if (con == null || con.isClosed()) {
            throw new SQLException("Cannot retrieve columns, connection is closed or invalid.");
        }

        DatabaseMetaData metaData = con.getMetaData();

        // Kiểm tra xem bảng có tồn tại không
        ResultSet tables = metaData.getTables(null, null, tableName, null);
        if (!tables.next()) {
            System.out.println("Table " + tableName + " does not exist.");
            tables.close();
            con.close();
            return;
        }

        // Lấy thông tin cột của bảng
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

    public static void dropTableBorrowedDocuments() throws SQLException {
        Connection con = connect();

        try (con; Statement statement = con.createStatement()) {
            if (con == null || con.isClosed()) {
                throw new SQLException("Cannot drop table, connection is closed or invalid.");
            }
            String dropSQL = "DROP TABLE IF EXISTS BorrowedDocuments";
            statement.executeUpdate(dropSQL);
            System.out.println("Table BorrowedDocuments has been dropped successfully.");
        } catch (SQLException e) {
            System.err.println("Error dropping table: " + e.getMessage());
        }
    }

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

    public static void main(String[] args) throws SQLException {
//        connect(); // Kết nối tới CSDL
////        dropTableMaterial();
//        dropTableTitle();
//            doitencot();
//        dropTableBorrowedDocuments();
            dropTableTransaction();
//        dropTableCategory();
    }
}