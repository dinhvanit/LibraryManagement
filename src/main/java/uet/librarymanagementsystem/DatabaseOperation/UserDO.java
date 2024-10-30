package uet.librarymanagementsystem.DatabaseOperation;

import java.sql.*;

public class UserDO extends DatabaseManager{
    public static void createUserTable() throws SQLException, SQLException {
        Connection con = connect();
        Statement statement = con.createStatement();

        // SQL query to create the User table
        String createTableSQL = "CREATE TABLE IF NOT EXISTS User (" +
                "id INT AUTO_INCREMENT PRIMARY KEY, " +
                "name VARCHAR(255), " +
                "dateOfBirth DATE, " +  // Assuming date of birth is a date
                "phoneNumber VARCHAR(20), " +
                "email VARCHAR(255), " +
                "password VARCHAR(255)" +
                ")";

        statement.execute(createTableSQL);
        System.out.println("Table 'User' created successfully!");
        con.close();
    }

    public static void getStudentById(int id) {
        Connection con = connect();
        String query = "SELECT * FROM User WHERE id = ?";
        try {
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                System.out.println("ID: " + rs.getInt("id"));
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

    public static void getAllStudents() {
        Connection con = connect();
        String query = "SELECT * FROM User";
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id"));
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

    public static void deleteStudentById(int id) {
        Connection con = connect();
        String query = "DELETE FROM User WHERE id = ?";
        try {
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setInt(1, id);
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
