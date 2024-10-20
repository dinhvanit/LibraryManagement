package uet.librarymanagementsystem.DatabaseOperation;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseManager {

    public static Connection connect() {
        Connection conn = null;
        try {
            String url = "jdbc:sqlite:E:/LENOVO LEGION/Tai lieu hoc tap/OOP (TH)/OOPproject/LibraryManagement/src/main/java/uet/librarymanagementsystem/Database/library.db";
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
