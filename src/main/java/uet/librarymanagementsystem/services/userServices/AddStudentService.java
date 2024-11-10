package uet.librarymanagementsystem.services.userServices;

import uet.librarymanagementsystem.DatabaseOperation.UserDO;

import java.sql.SQLException;

public class AddStudentService {

    public static String addStudent(String id, String name, String dateOfBirth, String phoneNumber, String email) {
        String password = id;

        if (UserDO.isUserExists(id)) {
            return "Sinh viên với ID " + id + " đã tồn tại.";
        }

        try {
            // Thêm sinh viên vào cơ sở dữ liệu
            UserDO.insertStudent(id, name, dateOfBirth, phoneNumber, email, password);
            return "Sinh viên được thêm thành công.";
        } catch (SQLException e) {
            return "Lỗi khi thêm sinh viên: " + e.getMessage();
        }
    }
}
