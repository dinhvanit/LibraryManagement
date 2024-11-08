package uet.librarymanagementsystem.DatabaseOperation;

import java.sql.*;

public class UserDO extends DatabaseManager {

    // Tạo bảng lưu thông tin sinh viên
    public static void createUserTable() throws SQLException {
        Connection con = connect();
        Statement statement = con.createStatement();

        String createTableSQL = "CREATE TABLE IF NOT EXISTS User (" +
                "id VARCHAR(50) PRIMARY KEY, " +
                "name VARCHAR(255), " +
                "dateOfBirth DATE, " +
                "phoneNumber VARCHAR(20), " +
                "email VARCHAR(255), " +
                "password VARCHAR(255)" +
                ")";

        statement.execute(createTableSQL);
        System.out.println("Table 'User' created successfully!");
        con.close();
    }

    // Phương thức kiểm tra xem người dùng đã tồn tại hay chưa
    public static boolean isUserExists(String id) {
        Connection con = connect();
        String query = "SELECT COUNT(*) FROM User WHERE id = ?";
        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setString(1, id);
            ResultSet rs = pstmt.executeQuery();
            rs.next();
            return rs.getInt(1) > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // Phương thức thêm sinh viên vào bảng User
    public static void insertStudent(String id, String name, String dateOfBirth, String phoneNumber, String email, String password) throws SQLException {
        Connection con = connect();

        String insertSQL = "INSERT INTO User (id, name, dateOfBirth, phoneNumber, email, password) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = con.prepareStatement(insertSQL)) {
            pstmt.setString(1, id);
            pstmt.setString(2, name);
            pstmt.setString(3, dateOfBirth);
            pstmt.setString(4, phoneNumber);
            pstmt.setString(5, email);
            pstmt.setString(6, password);
            pstmt.executeUpdate();
            System.out.println("Sinh viên được thêm thành công.");
        } finally {
            if (con != null) con.close();
        }
    }

    public static void updatePassword(String id, String newPassword) throws SQLException {
        Connection con = connect();
        String query = "UPDATE User SET password = ? WHERE id = ?";
        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setString(1, newPassword);
            pstmt.setString(2, id);
            pstmt.executeUpdate();
        } finally {
            if (con != null) con.close();
        }
    }

    // Lấy thông tin sinh viên theo ID
    public static void getStudentById(String id) {
        Connection con = connect();
        String query = "SELECT * FROM User WHERE id = ?";
        try {
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                System.out.println("ID: " + rs.getString("id"));
                System.out.println("Name: " + rs.getString("name"));
                System.out.println("Date of Birth: " + rs.getDate("dateOfBirth"));
                System.out.println("Phone Number: " + rs.getString("phoneNumber"));
                System.out.println("Email: " + rs.getString("email"));
                System.out.println("Password: " + rs.getString("password"));
            } else {
                System.out.println("Student with ID " + id + " not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // Lấy danh sách tất cả sinh viên
    public static void getAllStudents() {
        Connection con = connect();
        String query = "SELECT * FROM User";
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                System.out.println("ID: " + rs.getString("id"));
                System.out.println("Name: " + rs.getString("name"));
                System.out.println("Date of Birth: " + rs.getDate("dateOfBirth"));
                System.out.println("Phone Number: " + rs.getString("phoneNumber"));
                System.out.println("Email: " + rs.getString("email"));
                System.out.println("Password: " + rs.getString("password"));
                System.out.println("--------------------------");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void deleteStudentById(String id) {
        Connection con = connect();
        String query = "DELETE FROM User WHERE id = ?";
        try {
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, id);
            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Student with ID " + id + " deleted successfully.");
            } else {
                System.out.println("Student with ID " + id + " not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
