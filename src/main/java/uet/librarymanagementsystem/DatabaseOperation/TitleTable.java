package uet.librarymanagementsystem.DatabaseOperation;

import java.sql.*;
import java.util.Scanner;

import static uet.librarymanagementsystem.DatabaseOperation.DatabaseManager.connect;

public class TitleTable {
    public static void createTitleTable() throws SQLException {
        Connection con = connect();
        if (con == null || con.isClosed()) {
            throw new SQLException("Cannot create title, connection is closed or invalid.");
        }
        Statement statement = con.createStatement();

        String createTableSQL = "CREATE TABLE IF NOT EXISTS Title (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name VARCHAR(255) NOT NULL, " +
                "isbn VARCHAR(255)" + // khong de unique cho no co the nhan null
                ")";

        statement.execute(createTableSQL);
        con.close();
    }

//    public static void insertTitleFromKeyboard() throws SQLException {
//        Scanner scanner = new Scanner(System.in);
//
//        System.out.println("Enter title name: ");
//        String titleName = scanner.nextLine();
//
//        Connection con = connect();
//        if (con == null || con.isClosed()) {
//            throw new SQLException("Cannot insert title, connection is closed or invalid.");
//        }
//
//        // Kiểm tra xem tiêu đề đã tồn tại trong cơ sở dữ liệu chưa
//        String checkSQL = "SELECT COUNT(*) FROM Title WHERE name = ?";
//        try (PreparedStatement checkStmt = con.prepareStatement(checkSQL)) {
//            checkStmt.setString(1, titleName);
//            ResultSet rs = checkStmt.executeQuery();
//            if (rs.next() && rs.getInt(1) > 0) {
//                System.out.println("TITLE already exists in the database.");
//                con.close();
//                return;
//            }
//        }
//
//        // Thêm tiêu đề mới nếu nó chưa tồn tại
//        String insertSQL = "INSERT INTO Title (name) VALUES (?)";
//        try (PreparedStatement insertStmt = con.prepareStatement(insertSQL)) {
//            insertStmt.setString(1, titleName);
//            insertStmt.executeUpdate();
//            System.out.println("TITLE inserted successfully.");
//        }
//
//        con.close();
//    }

    public static void insertTitle(String titleName, String isbn) throws SQLException {
        Connection con = connect();
        if (con == null || con.isClosed()) {
            throw new SQLException("Cannot insert title, connection is closed or invalid.");
        }

        if (isbn == null) {
            String checkTitleSQL = "SELECT COUNT(*) FROM Title WHERE name = ?";
            try (PreparedStatement checkStmt = con.prepareStatement(checkTitleSQL)) {
                checkStmt.setString(1, titleName);
                ResultSet rs = checkStmt.executeQuery();
                if (rs.next() && rs.getInt(1) > 0) {
                    System.out.println("TITLE already exists in the database.");
                    con.close();
                    return;
                }
            }
        } else {
            String checkIsbnSQL = "SELECT COUNT(*) FROM Title WHERE isbn = ?";
            try (PreparedStatement checkStmt = con.prepareStatement(checkIsbnSQL)) {
                checkStmt.setString(1, isbn);
                ResultSet rs = checkStmt.executeQuery();
                if (rs.next() && rs.getInt(1) > 0) {
                    System.out.println("ISBN already exists in the database.");
                    con.close();
                    return;
                }
            }
        }

        String insertSQL = "INSERT INTO Title (name, isbn) VALUES (?, ?)";
        try (PreparedStatement insertStmt = con.prepareStatement(insertSQL)) {
            insertStmt.setString(1, titleName);
            insertStmt.setString(2, isbn);
            insertStmt.executeUpdate();
            System.out.println("TITLE inserted successfully.");
        }

        con.close();
    }

    public static void clearTitleTable() throws SQLException {
        Connection con = connect(); // Kết nối tới cơ sở dữ liệu

        if (con == null || con.isClosed()) {
            throw new SQLException("Cannot clear title table, connection is closed or invalid.");
        }

        try {
            // Lệnh SQL để xóa tất cả dữ liệu trong bảng
            String clearTableSQL = "DELETE FROM Title";

            Statement statement = con.createStatement();
            statement.executeUpdate(clearTableSQL);

            System.out.println("All data in the Title table has been cleared successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error occurred while clearing the Title table.");
            throw e;
        } finally {
            con.close(); // Đảm bảo kết nối được đóng sau khi thực hiện
        }
    }

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

    public static void main(String []args) throws SQLException {
//        createTitleTable();
//        clearTitleTable();
//          dropTitleTable();
    }
}
