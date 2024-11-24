package uet.librarymanagementsystem.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ValidationLabelUtilTest {
    private final ValidationLabelUtil validator = new ValidationLabelUtil();

    @Test
    void testValidateEmptyField() {
        // Kiểm tra trường hợp trống
        assertEquals("Trường này không được bỏ trống", validator.validateEmptyField("   ", "Trường này không được bỏ trống"));

        // Kiểm tra trường hợp không trống
        assertEquals("", validator.validateEmptyField("Test", "Trường này không được bỏ trống"));

        // Kiểm tra trường hợp null
        assertEquals("Trường này không được bỏ trống", validator.validateEmptyField(null, "Trường này không được bỏ trống"));
    }

    @Test
    void testValidateEmailFormat() {
        // Kiểm tra email hợp lệ
        assertEquals("", validator.validateEmailFormat("test@gmail.com"));

        // Kiểm tra email không hợp lệ
        assertEquals("Email cần có đuôi @gmail.com", validator.validateEmailFormat("user@example.com"));
    }

    @Test
    void testValidatePhoneNumberFormat() {
        // Kiểm tra số điện thoại hợp lệ
        assertEquals("", validator.validatePhoneNumberFormat("0123456789"));

        // Kiểm tra số điện thoại không hợp lệ
        assertEquals("Số điện thoại phải gồm 10 chữ số", validator.validatePhoneNumberFormat("12345"));
        assertEquals("Số điện thoại phải gồm 10 chữ số", validator.validatePhoneNumberFormat("abcdefghij"));
    }

    @Test
    void testValidateNumericField() {
        // Kiểm tra số hợp lệ
        assertEquals("", validator.validateNumericField("123", "Trường này phải là số"));

        // Kiểm tra không phải số
        assertEquals("Trường này phải là số", validator.validateNumericField("abc", "Trường này phải là số"));
        assertEquals("Trường này phải là số", validator.validateNumericField("12.3", "Trường này phải là số"));
    }

    @Test
    void testValidateDateFormat() {
        // Kiểm tra định dạng ngày hợp lệ
        assertEquals("", validator.validateDateFormat("12/05/2024"));

        // Kiểm tra định dạng ngày không hợp lệ
        assertEquals("Định dạng ngày tháng không hợp lệ (dd/MM/yyyy)", validator.validateDateFormat("2024/05/12"));
        assertEquals("Định dạng ngày tháng không hợp lệ (dd/MM/yyyy)", validator.validateDateFormat("32/13/2024"));
    }

    @Test
    void testValidateField() {
        // Kiểm tra các loại kiểm tra
        assertEquals("Trường này không được bỏ trống", validator.validateField("   ", ValidationLabelUtil.ValidationType.EMPTY));
        assertEquals("", validator.validateField("test@gmail.com", ValidationLabelUtil.ValidationType.EMAIL));
        assertEquals("Số điện thoại phải gồm 10 chữ số", validator.validateField("12345", ValidationLabelUtil.ValidationType.PHONE));
        assertEquals("", validator.validateField("123", ValidationLabelUtil.ValidationType.NUMERIC));
        assertEquals("Định dạng ngày tháng không hợp lệ (dd/MM/yyyy)", validator.validateField("2024/05/12", ValidationLabelUtil.ValidationType.DATE));
    }

    // Thêm test kiểm tra mật khẩu mạnh
    @Test
    void testValidatePasswordFormat() {
        // mật khẩu hợp lệ
        assertEquals("", validator.validatePasswordFormat("vanDinh1@"));
        assertEquals("", validator.validatePasswordFormat("Dinhvan123!"));

        // Kiểm tra mật khẩu dài hơn 6 ký tự nhưng thiếu chữ hoa
        assertEquals("Mật khẩu cần ít nhất một ký tự viết hoa", validator.validatePasswordFormat("dinhvan1@"));

        // Kiểm tra mật khẩu dài hơn 6 ký tự nhưng thiếu số
        assertEquals("Mật khẩu cần ít nhất một chữ số", validator.validatePasswordFormat("DinhVan@"));

        // Kiểm tra mật khẩu dài hơn 6 ký tự nhưng thiếu ký tự đặc biệt
        assertEquals("Mật khẩu cần ít nhất một ký tự đặc biệt (!@#$%^&*)", validator.validatePasswordFormat("DinhVan123"));

        // Kiểm tra mật khẩu quá ngắn
        assertEquals("Mật khẩu phải có ít nhất 6 ký tự", validator.validatePasswordFormat("Dv1!"));

        // Có thể viết thêm test nếu thích
    }
}