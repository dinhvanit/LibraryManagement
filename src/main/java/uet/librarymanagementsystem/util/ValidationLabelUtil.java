package uet.librarymanagementsystem.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class ValidationLabelUtil {

    /**
     * Checks if the input field is empty. If it is empty, returns the error message.
     * @param field: The field to check
     * @param errorMessage: The error message to return if the field is empty
     * @return Error message if the field is empty, otherwise returns an empty string
     */
    public String validateEmptyField(String field, String errorMessage) {
        if (field == null || field.trim().isEmpty()) {
            return errorMessage;
        }
        return "";
    }

    /**
     * Validates the email format.
     * @param field: The email field to check
     * @return Error message if the email is not valid, otherwise returns an empty string
     */
    public String validateEmailFormat(String field) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(gmail\\.com)$";
        if (!field.matches(emailRegex)) {
            return "Email must have the domain @gmail.com";
        }
        return "";
    }

    /**
     * Validates the phone number format.
     * @param field: The phone number field to check
     * @return Error message if the phone number is not valid, otherwise returns an empty string
     */
    public String validatePhoneNumberFormat(String field) {
        String phoneRegex = "^([0-9]{10})$";
        if (!field.matches(phoneRegex)) {
            return "Phone number must consist of 10 digits";
        }
        return "";
    }

    /**
     * Validates if the field contains a valid number.
     * @param field: The number field to check
     * @param errorMessage: The error message to return if the field is not a valid number
     * @return Error message if the field is not a valid number, otherwise returns an empty string
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
     * Validates the date format (dd/MM/yyyy).
     * @param field: The date field to check
     * @return Error message if the date is not valid, otherwise returns an empty string
     */
    public String validateDateFormat(String field) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        try {
            LocalDate date = LocalDate.parse(field.trim(), formatter);
        } catch (DateTimeParseException e) {
            return "Date format is invalid. It should be in dd/MM/yyyy format.";
        }
        return "";
    }

    /**
     * Validates the password format.
     * @param field: The password field to check
     * @return Error message if the password is not valid, otherwise returns an empty string
     */
    public String validatePasswordFormat(String field) {
        if (field.length() < 6) {
            return "Password must be at least 6 characters long";
        }
        if (!field.matches(".*[A-Z].*")) {
            return "Password must contain at least one uppercase letter";
        }
        if (!field.matches(".*\\d.*")) {
            return "Password must contain at least one digit";
        }
        if (!field.matches(".*[!@#$%^&*(),.?\":{}|<>].*")) {
            return "Password must contain at least one special character (!@#$%^&*)";
        }
        return "";
    }

    /**
     * Validates the input field based on the specified validation type.
     * @param field: The field to validate
     * @param validationType: The type of validation to perform
     * @return Error message based on the validation type, otherwise returns an empty string
     */
    public String validateField(String field, ValidationType validationType) {
        switch (validationType) {
            case EMPTY:
                return validateEmptyField(field, "This field cannot be empty");
            case EMAIL:
                return validateEmailFormat(field);
            case PHONE:
                return validatePhoneNumberFormat(field);
            case NUMERIC:
                return validateNumericField(field, "This field must be a number");
            case DATE:
                return validateDateFormat(field);
            case PASSWORD:
                return validatePasswordFormat(field);
            default:
                return "";
        }
    }

    // Enum defining the types of validation
    public enum ValidationType {
        EMPTY, EMAIL, PHONE, NUMERIC, DATE, PASSWORD
    }
}
