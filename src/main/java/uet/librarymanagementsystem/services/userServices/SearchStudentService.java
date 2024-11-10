package uet.librarymanagementsystem.services.userServices;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import uet.librarymanagementsystem.DatabaseOperation.DatabaseManager;
import uet.librarymanagementsystem.entity.users.Student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SearchStudentService {

    private Connection conn;

    public SearchStudentService() {
        conn = DatabaseManager.connect();
    }

    public ObservableList<Student> search(String id, String name) throws SQLException {
        ObservableList<Student> studentListSearchResult = FXCollections.observableArrayList();
        StringBuilder query = new StringBuilder("SELECT id, name, dateOfBirth, phoneNumber, email, password FROM User WHERE 1=1");

        if (id != null && !id.isEmpty()) {
            query.append(" AND id LIKE ?");
        }
        if (name != null && !name.isEmpty()) {
            query.append(" AND name LIKE ?");
        }

        try (PreparedStatement pstmt = conn.prepareStatement(query.toString())) {
            int paramIndex = 1;

            if (id != null && !id.isEmpty()) {
                pstmt.setString(paramIndex++, "%" + id + "%");
            }
            if (name != null && !name.isEmpty()) {
                pstmt.setString(paramIndex++, "%" + name + "%");
            }

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    String retrievedId = rs.getString("id");
                    String retrievedName = rs.getString("name");
                    String retrievedDateOfBirth = rs.getString("dateOfBirth");
                    String retrievedPhoneNumber = rs.getString("phoneNumber");
                    String retrievedEmail = rs.getString("email");
                    String retrievedPassword = rs.getString("password");

                    Student student = new Student(retrievedId, retrievedName, retrievedDateOfBirth, retrievedPhoneNumber, retrievedEmail, retrievedPassword);
                    studentListSearchResult.add(student);
                }
            }
        }

        return studentListSearchResult;
    }
}
