package uet.librarymanagementsystem.DatabaseOperation;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class DatabaseManager {
    public static Connection connect() {
        Connection conn = null;
        try {      // relative path
            String url = "jdbc:sqlite:LibraryManagement/src/main/resources/uet/librarymanagementsystem/Database/library.db";
            conn = DriverManager.getConnection(url);
            System.out.println("Connection to SQLite has been established.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }
    public static void main(String[] args) {
        connect(); // Kết nối tới CSDL
    }
}