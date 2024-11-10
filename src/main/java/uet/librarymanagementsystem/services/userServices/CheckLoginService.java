package uet.librarymanagementsystem.services.userServices;

import uet.librarymanagementsystem.DatabaseOperation.UserDO;
import java.sql.SQLException;

public class CheckLoginService {

    // Phương thức kiểm tra đăng nhập
    public static boolean checkLogin(String userId, String password) {
        try {
            // kiểm tra có tồn tại id đó không
            if (UserDO.isUserExists(userId)) {
                return checkPassword(userId, password);
            } else {
                System.out.println("User does not exist.");
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Phương thức kiểm tra mật khẩu
    private static boolean checkPassword(String userId, String password) throws SQLException {
        String storedPassword = getUserPassword(userId);
        return storedPassword.equals(password);
    }

    private static String getUserPassword(String userId) throws SQLException {
        return UserDO.getPasswordById(userId);
    }
}
