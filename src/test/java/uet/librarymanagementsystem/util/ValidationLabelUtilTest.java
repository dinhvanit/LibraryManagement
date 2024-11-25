package uet.librarymanagementsystem.util;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ValidationLabelUtilTest {
    private final ValidationLabelUtil validator = new ValidationLabelUtil();

    @Test
    void testValidateEmptyField() {
        // Test for empty field
        assertEquals("This field cannot be empty", validator.validateEmptyField("   ", "This field cannot be empty"));

        // Test for non-empty field
        assertEquals("", validator.validateEmptyField("Test", "This field cannot be empty"));

        // Test for null field
        assertEquals("This field cannot be empty", validator.validateEmptyField(null, "This field cannot be empty"));
    }

    @Test
    void testValidateEmailFormat() {
        // Test for valid email
        assertEquals("", validator.validateEmailFormat("test@gmail.com"));

        // Test for invalid email
        assertEquals("Email must have the domain @gmail.com", validator.validateEmailFormat("user@example.com"));
    }

    @Test
    void testValidatePhoneNumberFormat() {
        // Test for valid phone number
        assertEquals("", validator.validatePhoneNumberFormat("0123456789"));

        // Test for invalid phone number (too short, non-numeric)
        assertEquals("Phone number must consist of 10 digits", validator.validatePhoneNumberFormat("12345"));
        assertEquals("Phone number must consist of 10 digits", validator.validatePhoneNumberFormat("abcdefghij"));
    }

    @Test
    void testValidateNumericField() {
        // Test for valid numeric field
        assertEquals("", validator.validateNumericField("123", "This field must be a number"));

        // Test for invalid numeric field (non-numeric input)
        assertEquals("This field must be a number", validator.validateNumericField("abc", "This field must be a number"));
        assertEquals("This field must be a number", validator.validateNumericField("12.3", "This field must be a number"));
    }

    @Test
    void testValidateDateFormat() {
        // Test for valid date format
        assertEquals("", validator.validateDateFormat("12/05/2024"));

        // Test for invalid date format (wrong delimiter, invalid date)
        assertEquals("Date format is invalid. It should be in dd/MM/yyyy format.", validator.validateDateFormat("2024/05/12"));
        assertEquals("Date format is invalid. It should be in dd/MM/yyyy format.", validator.validateDateFormat("32/13/2024"));
    }

    @Test
    void testValidateField() {
        // Test for various validation types
        assertEquals("This field cannot be empty", validator.validateField("   ", ValidationLabelUtil.ValidationType.EMPTY));
        assertEquals("", validator.validateField("test@gmail.com", ValidationLabelUtil.ValidationType.EMAIL));
        assertEquals("Phone number must consist of 10 digits", validator.validateField("12345", ValidationLabelUtil.ValidationType.PHONE));
        assertEquals("", validator.validateField("123", ValidationLabelUtil.ValidationType.NUMERIC));
        assertEquals("Date format is invalid. It should be in dd/MM/yyyy format.", validator.validateField("2024/05/12", ValidationLabelUtil.ValidationType.DATE));
    }

    @Test
    void testValidatePasswordFormat() {
        // Test for valid password
        assertEquals("", validator.validatePasswordFormat("vanDinh1@"));
        assertEquals("", validator.validatePasswordFormat("Dinhvan123!"));

        // Test for password without uppercase letter
        assertEquals("Password must contain at least one uppercase letter", validator.validatePasswordFormat("dinhvan1@"));

        // Test for password without number
        assertEquals("Password must contain at least one digit", validator.validatePasswordFormat("DinhVan@"));

        // Test for password without special character
        assertEquals("Password must contain at least one special character (!@#$%^&*)", validator.validatePasswordFormat("DinhVan123"));

        // Test for password too short
        assertEquals("Password must be at least 6 characters long", validator.validatePasswordFormat("Dv1!"));
    }
}
