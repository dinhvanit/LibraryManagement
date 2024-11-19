package uet.librarymanagementsystem.controllers.student;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import uet.librarymanagementsystem.services.userServices.ChangePasswordService;
import uet.librarymanagementsystem.util.ValidationLabelUtil;

public class ChangePasswordController {

    @FXML
    private PasswordField currentPassField;

    @FXML
    private PasswordField newPassField;

    @FXML
    private PasswordField repeatPassField;

    @FXML
    private Label currentPassErrorLabel;

    @FXML
    private Label newPassErrorLabel;

    @FXML
    private Label repeatPassErrorLabel;

    @FXML
    private Label successLabel;

    @FXML
    private Button visibleRepeatPassButton;

    @FXML
    private Button visibleNewPassButton;

    @FXML
    private Button visibleCurrentPassButton;

    @FXML
    private TextField visibleCurrentPassField;

    @FXML
    private TextField visibleRepeatPassField;

    @FXML
    private TextField visibleNewPassField;

    private final ChangePasswordService changePasswordService = new ChangePasswordService();
    private final ValidationLabelUtil validationUtil = new ValidationLabelUtil();

    @FXML
    public void initialize() {
        resetErrorMessages();

        currentPassField.textProperty().addListener((observable, oldValue, newValue) -> validatePasswordField(currentPassField, currentPassErrorLabel, ValidationLabelUtil.ValidationType.EMPTY));
        newPassField.textProperty().addListener((observable, oldValue, newValue) -> validateNewPassword());
        repeatPassField.textProperty().addListener((observable, oldValue, newValue) -> validateRepeatPassword());
    }

    private void resetErrorMessages() {
        currentPassErrorLabel.setVisible(false);
        newPassErrorLabel.setVisible(false);
        repeatPassErrorLabel.setVisible(false);
        successLabel.setVisible(false);
    }

    private void togglePasswordVisibility(PasswordField passwordField, TextField visibleTextField, Button toggleButton) {
        if (toggleButton.getText().equals("Hiện")) {
            visibleTextField.setText(passwordField.getText());
            passwordField.setVisible(false);
            visibleTextField.setVisible(true);
            toggleButton.setText("Ẩn");
        } else {
            passwordField.setText(visibleTextField.getText());
            visibleTextField.setVisible(false);
            passwordField.setVisible(true);
            toggleButton.setText("Hiện");
        }
    }

    @FXML
    private void toggleCurrentPasswordVisibility() {
        togglePasswordVisibility(currentPassField, visibleCurrentPassField, visibleCurrentPassButton);
    }

    @FXML
    private void toggleNewPasswordVisibility() {
        togglePasswordVisibility(newPassField, visibleNewPassField, visibleNewPassButton);
    }

    @FXML
    private void toggleRepeatPasswordVisibility() {
        togglePasswordVisibility(repeatPassField, visibleRepeatPassField, visibleRepeatPassButton);
    }

    @FXML
    private void onUpdatePassword() {
        String currentPassword = currentPassField.getText();
        String newPassword = newPassField.getText();
        String repeatPassword = repeatPassField.getText();

        boolean isValid = validateFields(currentPassword, newPassword, repeatPassword);

        if (!isValid) {
            successLabel.setVisible(false);
            return;
        }

        String userId = "admin"; // Thay bằng ID người dùng hiện tại (truy xuất từ session hoặc context)
        String resultMessage = changePasswordService.changePassword(userId, newPassword);

        successLabel.setText(resultMessage);
        if (resultMessage.contains("thành công")) {
            successLabel.setTextFill(javafx.scene.paint.Color.GREEN);
        } else {
            successLabel.setTextFill(javafx.scene.paint.Color.RED);
        }
        successLabel.setVisible(true);
    }

    private boolean validateFields(String currentPassword, String newPassword, String repeatPassword) {
        boolean isValid = true;

        String currentPassError = validationUtil.validateField(currentPassword, ValidationLabelUtil.ValidationType.EMPTY);
        isValid &= handleValidationError(currentPassError, currentPassErrorLabel);

        String newPassError = validationUtil.validateField(newPassword, ValidationLabelUtil.ValidationType.EMPTY);
        isValid &= handleValidationError(newPassError, newPassErrorLabel);

        String passwordFormatError = validationUtil.validateField(newPassword, ValidationLabelUtil.ValidationType.PASSWORD);
        if (!passwordFormatError.isEmpty()) {
            newPassErrorLabel.setText(passwordFormatError);
            newPassErrorLabel.setVisible(true);
            isValid = false;
        }

        if (!newPassword.equals(repeatPassword)) {
            repeatPassErrorLabel.setText("Mật khẩu nhập lại không khớp.");
            repeatPassErrorLabel.setVisible(true);
            isValid = false;
        } else {
            repeatPassErrorLabel.setVisible(false);
        }

        return isValid;
    }

    private boolean handleValidationError(String error, Label errorLabel) {
        if (!error.isEmpty()) {
            errorLabel.setText(error);
            errorLabel.setVisible(true);
            return false;
        }
        errorLabel.setVisible(false);
        return true;
    }

    private void showSuccessMessage(String message, javafx.scene.paint.Color color) {
        successLabel.setText(message);
        successLabel.setTextFill(color);
        successLabel.setVisible(true);
    }

    private void validatePasswordField(PasswordField passwordField, Label errorLabel, ValidationLabelUtil.ValidationType validationType) {
        String password = passwordField.getText();
        String error = validationUtil.validateField(password, validationType);
        if (!error.isEmpty()) {
            errorLabel.setText(error);
            errorLabel.setVisible(true);
        } else {
            errorLabel.setVisible(false);
        }
    }

    private void validateNewPassword() {
        String newPassword = newPassField.getText();
        String newPassError = validationUtil.validateField(newPassword, ValidationLabelUtil.ValidationType.EMPTY);
        if (!newPassError.isEmpty()) {
            newPassErrorLabel.setText(newPassError);
            newPassErrorLabel.setVisible(true);
        } else {
            newPassErrorLabel.setVisible(false);
        }

        String passwordFormatError = validationUtil.validateField(newPassword, ValidationLabelUtil.ValidationType.PASSWORD);
        if (!passwordFormatError.isEmpty()) {
            newPassErrorLabel.setText(passwordFormatError);
            newPassErrorLabel.setVisible(true);
        } else {
            newPassErrorLabel.setVisible(false);
        }
    }

    private void validateRepeatPassword() {
        String newPassword = newPassField.getText();
        String repeatPassword = repeatPassField.getText();
        if (!newPassword.equals(repeatPassword)) {
            repeatPassErrorLabel.setText("Mật khẩu nhập lại không khớp.");
            repeatPassErrorLabel.setVisible(true);
        } else {
            repeatPassErrorLabel.setVisible(false);
        }
    }
}
