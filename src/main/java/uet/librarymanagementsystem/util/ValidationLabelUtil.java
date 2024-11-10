package uet.librarymanagementsystem.util;

import javafx.scene.control.Label;

public class ValidationLabelUtil {

    // Trường hợp chung
    // Kiểm tra nếu trường trống và hiển thị lỗi trong Label tương ứng
    public static boolean validateEmptyField(String textField, Label label) {
        if (textField.isEmpty()) {
            label.setText("Vui lòng nhập thông tin");
            return false;
        }
        label.setText("");
        return true;
    }

    // Trường hợp nhập label có định dạng cố định, thông báo ngay khi nhập
    // Kiểm tra định dạng email
    public static void validateEmailFormat(String emailText, Label label) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+@(gmail\\.com|vnu\\.edu\\.vn)$";
        if (!emailText.matches(emailRegex)) {
            label.setText("Cần định dạng đúng email @gmail.com hoặc @vnu.edu.vn");
        } else {
            label.setText("");
        }
    }

    // Kiểm tra định dạng số điện thoại
    public static void validatePhoneFormat(String phoneText, Label label) {
        String phoneRegex = "^\\d{10}$";
        if (!phoneText.matches(phoneRegex)) {
            label.setText("Cần định dạng đúng số điện thoại đủ 10 số");
        } else {
            label.setText("");
        }
    }

    // Kiểm tra định dạng ngày
    public static void validateBirthdayFormat(String dateText, Label label) {
        String birthdayRegex = "^\\d{4}/\\d{2}/\\d{2}$";
        if (!dateText.matches(birthdayRegex)) {
            label.setText("Cần định dạng đúng kiểu yyyy/mm/dd");
        } else {
            label.setText("");
        }
    }
}
