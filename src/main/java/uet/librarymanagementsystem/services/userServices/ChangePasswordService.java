package uet.librarymanagementsystem.services.userServices;

import uet.librarymanagementsystem.DatabaseOperation.UserDO;
import java.sql.SQLException;

public class ChangePasswordService {

    public String changePassword(String id, String newPassword) {
        try {
            // Kiểm tra xem sinh viên có tồn tại không
            if (!UserDO.isUserExists(id)) {
                return "Sinh viên với ID " + id + " không tồn tại.";
            }

            // Thực hiện thay đổi mật khẩu
            UserDO.updatePassword(id, newPassword);
            return "Mật khẩu đã được thay đổi thành công.";
        } catch (SQLException e) {
            System.err.println("Lỗi khi thay đổi mật khẩu: " + e.getMessage());
            return "Thay đổi mật khẩu thất bại. Vui lòng thử lại.";
        }
    }
}
