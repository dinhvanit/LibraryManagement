package uet.librarymanagementsystem.DatabaseOperation;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

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
}
