package uet.librarymanagementsystem.services.userServices;

import uet.librarymanagementsystem.DatabaseOperation.UserDO;
import java.sql.SQLException;

public class ChangePasswordService {

    public static boolean changePassword(String id, String newPassword) {
        try {
            // Kiểm tra xem sinh viên có tồn tại không
            if (!UserDO.isUserExists(id)) {
                System.out.println("Sinh viên với ID " + id + " không tồn tại.");
                return false;
            }

            // Thực hiện thay đổi mật khẩu
            UserDO.updatePassword(id, newPassword);
            System.out.println("Mật khẩu đã được thay đổi thành công.");
            return true;
        } catch (SQLException e) {
            System.err.println("Lỗi khi thay đổi mật khẩu: " + e.getMessage());
            return false;
        }
    }
}
