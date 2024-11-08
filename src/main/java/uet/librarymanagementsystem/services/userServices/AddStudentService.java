package uet.librarymanagementsystem.services.userServices;

import uet.librarymanagementsystem.DatabaseOperation.UserDO;

import java.sql.SQLException;

public class AddStudentService {

    // Phương thức thêm sinh viên mới vào cơ sở dữ liệu
    public static boolean addStudent(String id, String name, String dateOfBirth, String phoneNumber, String email) {
        // khởi tạo password = id (giống mã sinh viên, sinh viên lần đầu đăng nhập có thể đổi mật khẩu)
        String password = id;

        // kiểm tra tính hợp lệ của email
        if (!isEmailValid(email)) {
            System.out.println("Email không hợp lệ.");
            return false;
        }

        // Kiểm tra xem sinh viên đã tồn tại chưa
        if (UserDO.isUserExists(id)) {
            System.out.println("Sinh viên với ID " + id + " đã tồn tại.");
            return false;
        }

        // Thêm sinh viên vào cơ sở dữ liệu
        try {
            UserDO.insertStudent(id, name, dateOfBirth, phoneNumber, email, password);
            System.out.println("Sinh viên được thêm thành công.");
            return true;
        } catch (SQLException e) {
            System.err.println("Lỗi khi thêm sinh viên: " + e.getMessage());
            return false;
        }
    }

    // Phương thức kiểm tra tính hợp lệ của email
    private static boolean isEmailValid(String email) {
        return email != null && email.contains("@") && email.contains(".");
    }
}
