package uet.librarymanagementsystem.util;

import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class ValidationLabelUtil {

    // Kiểm tra xem trường có bị trống không và cập nhật errorLabel
    public static boolean validateEmptyField(Object field, Label errorLabel) {
        errorLabel.setStyle("-fx-text-fill: red;");
        boolean isValid = true;

        if (field instanceof TextField) {
            TextField textField = (TextField) field;
            if (textField.getText().trim().isEmpty()) {
                errorLabel.setText("Vui lòng điền vào trường này");
                isValid = false;
            } else {
                errorLabel.setText("");
            }
        } else if (field instanceof DatePicker) {
            DatePicker datePicker = (DatePicker) field;
            if (datePicker.getValue() == null) {
                errorLabel.setText("Vui lòng chọn ngày sinh");
                isValid = false;
            } else {
                errorLabel.setText("");
            }
        }

        return isValid;
    }

    // Kiểm tra định dạng email
    public static void validateEmailFormat(String emailText, Label label) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(gmail\\.com|vnu\\.edu\\.vn)$";
        label.setText(emailText.matches(emailRegex) ? "" : "Email cần có đuôi @gmail.com hoặc @vnu.edu.vn");
    }

    // Kiểm tra định dạng số điện thoại
    public static void validatePhoneFormat(String phoneText, Label label) {
        String phoneRegex = "^([0-9]{10})$";
        label.setText(phoneText.matches(phoneRegex) ? "" : "Số điện thoại cần 10 chữ số");
    }

    // Kiểm tra định dạng ngày sinh
    public static void validateBirthdayFormat(String dateText, Label label) {
        String birthdayRegex = "^(0[1-9]|1[0-9]|2[0-9]|3[01])/(0[1-9]|1[0-2])/(\\d{4})$";
        label.setText(dateText.matches(birthdayRegex) ? "" : "Cần định dạng đúng kiểu dd/mm/yyyy");
    }
}
