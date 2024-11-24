package uet.librarymanagementsystem.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;


public class ValidationLabelUtil {

    /**
     * Kiểm tra xem trường nhập liệu có trống không, nếu trống sẽ trả về thông báo lỗi
     * @param field: Trường cần kiểm tra
     * @param errorMessage: Thông báo lỗi khi trường trống
     * @return Thông báo lỗi nếu có, trả về chuỗi rỗng nếu không có lỗi
     */
    public String validateEmptyField(String field, String errorMessage) {
        if (field == null || field.trim().isEmpty()) {
            return errorMessage;
        }
        return "";
    }

    /**
     * Kiểm tra định dạng email hợp lệ
     * @param field: Trường email cần kiểm tra
     * @return Thông báo lỗi nếu có, trả về chuỗi rỗng nếu không có lỗi
     */
    public String validateEmailFormat(String field) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(gmail\\.com)$";;
        if (!field.matches(emailRegex)) {
            return "Email cần có đuôi @gmail.com";
        }
        return "";
    }

    // Kiểm tra định dạng số điện thoại
    public String validatePhoneNumberFormat(String field) {
        String phoneRegex = "^([0-9]{10})$";
        if (!field.matches(phoneRegex)) {
            return "Số điện thoại phải gồm 10 chữ số";
        }
        return "";
    }

    /*// Kiểm tra định dạng ngày sinh
    public static void validateBirthdayFormat(String dateText, Label label) {
        String birthdayRegex = "^(0[1-9]|1[0-9]|2[0-9]|3[01])/(0[1-9]|1[0-2])/(\\d{4})$";
        label.setText(dateText.matches(birthdayRegex) ? "" : "Cần định dạng đúng kiểu dd/mm/yyyy");
    }*/

    /**
     * Kiểm tra trường thông tin là một số hợp lệ
     * @param field: Trường số cần kiểm tra
     * @param errorMessage: Thông báo lỗi khi không phải là số hợp lệ
     * @return Thông báo lỗi nếu có, trả về chuỗi rỗng nếu không có lỗi
     */
    public String validateNumericField(String field, String errorMessage) {
        try {
            Integer.parseInt(field.trim());
        } catch (NumberFormatException e) {
            return errorMessage;
        }
        return "";
    }

    /**
     * Kiểm tra định dạng ngày tháng hợp lệ (dd/MM/yyyy)
     * @param field: Trường ngày tháng cần kiểm tra
     * @return Thông báo lỗi nếu có, trả về chuỗi rỗng nếu không có lỗi
     */
    public String validateDateFormat(String field) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        try {
            LocalDate date = LocalDate.parse(field.trim(), formatter);
        } catch (DateTimeParseException e) {
            return "Định dạng ngày tháng không hợp lệ (dd/MM/yyyy)";
        }
        return "";
    }

    public String validatePasswordFormat(String field) {
        if (field.length() < 6) {
            return "Mật khẩu phải có ít nhất 6 ký tự";
        }
        if (!field.matches(".*[A-Z].*")) {
            return "Mật khẩu cần ít nhất một ký tự viết hoa";
        }
        if (!field.matches(".*\\d.*")) {
            return "Mật khẩu cần ít nhất một chữ số";
        }
        if (!field.matches(".*[!@#$%^&*(),.?\":{}|<>].*")) {
            return "Mật khẩu cần ít nhất một ký tự đặc biệt (!@#$%^&*)";
        }
        return "";
    }

    // Cập nhật phương thức validateField để hỗ trợ kiểm tra ngày tháng
    public String validateField(String field, ValidationType validationType) {
        switch (validationType) {
            case EMPTY:
                return validateEmptyField(field, "Trường này không được bỏ trống");
            case EMAIL:
                return validateEmailFormat(field);
            case PHONE:
                return validatePhoneNumberFormat(field);
            case NUMERIC:
                return validateNumericField(field, "Trường này phải là số");
            case DATE:
                return validateDateFormat(field);
            case PASSWORD:
                return validatePasswordFormat(field);
            default:
                return "";
        }
    }

    // Enum định nghĩa các loại kiểm tra
    public enum ValidationType {
        EMPTY, EMAIL, PHONE, NUMERIC, DATE, PASSWORD
    }
}
