package uet.librarymanagementsystem.controllers.admin;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import uet.librarymanagementsystem.services.userServices.AddStudentService;
import uet.librarymanagementsystem.util.ValidationLabelUtil;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class AddStudentController implements Initializable {

    private boolean isAdded = false;
    private final ValidationLabelUtil validationUtil = new ValidationLabelUtil();

    @FXML
    private DatePicker fieldBirthdayStudent;

    @FXML
    private TextField fieldEmailStudent, fieldIDStudent, fieldNameStudent, fieldPhoneStudent;

    @FXML
    private Label statusLabel, idValidLabel, nameValidLabel, birthValidLabel, phoneValidLabel, emailValidLabel;

    @FXML
    private Label checkInforId, checkInforName, checkInforBirthday, checkInforPhone, checkInforEmail, checkInforPassword;

    @FXML
    void addStudentButtonOnClick(MouseEvent event) {
        if (validateInputs()) {
            populateCheckInforLabels();
            updateStatus("Đã tải thông tin. Vui lòng kiểm tra và nhấn 'Save' để lưu.", "green");
            isAdded = true;
            clearFields();
        } else {
            updateStatus("Vui lòng điền đúng thông tin vào tất cả các trường.", "red");
            isAdded = false;
        }
    }

    private boolean validateInputs() {
        return validateField(fieldIDStudent, idValidLabel, ValidationLabelUtil.ValidationType.EMPTY)
                & validateField(fieldNameStudent, nameValidLabel, ValidationLabelUtil.ValidationType.EMPTY)
                & validateDateField(fieldBirthdayStudent, birthValidLabel)
                & validateField(fieldPhoneStudent, phoneValidLabel, ValidationLabelUtil.ValidationType.PHONE)
                & validateField(fieldEmailStudent, emailValidLabel, ValidationLabelUtil.ValidationType.EMAIL);
    }

    private boolean validateField(TextField field, Label label, ValidationLabelUtil.ValidationType type) {
        String errorMessage = validationUtil.validateField(field.getText(), type);
        updateValidationLabel(label, errorMessage);
        return errorMessage.isEmpty();
    }

    private boolean validateDateField(DatePicker datePicker, Label label) {
        LocalDate date = datePicker.getValue();
        String errorMessage = (date == null) ? "Ngày tháng không được để trống." : "";
        updateValidationLabel(label, errorMessage);
        return errorMessage.isEmpty();
    }

    private void updateValidationLabel(Label label, String errorMessage) {
        label.setText(errorMessage);
        label.setVisible(!errorMessage.isEmpty());
    }

    private void populateCheckInforLabels() {
        String formattedDob = fieldBirthdayStudent.getValue() != null
                ? fieldBirthdayStudent.getValue().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"))
                : "";

        checkInforId.setText(fieldIDStudent.getText());
        checkInforName.setText(fieldNameStudent.getText());
        checkInforBirthday.setText(formattedDob);
        checkInforPhone.setText(fieldPhoneStudent.getText());
        checkInforEmail.setText(fieldEmailStudent.getText());
        checkInforPassword.setText(fieldIDStudent.getText()); // Mật khẩu mặc định là ID
    }

    @FXML
    private void saveStudentButtonOnClick() {
        if (isAdded) {
            String result = AddStudentService.addStudent(
                    checkInforId.getText(),
                    checkInforName.getText(),
                    checkInforBirthday.getText(),
                    checkInforPhone.getText(),
                    checkInforEmail.getText()
            );
            updateStatus(result, result.contains("thành công") ? "green" : "red");
        } else {
            updateStatus("Vui lòng điền đầy đủ thông tin hợp lệ.", "red");
        }
    }

    @FXML
    void removeStudentButtonOnClick(MouseEvent event) {
        isAdded = false;
        clearFields();
        clearCheckInforLabels();
        updateStatus("Thông tin đã được xóa.", "red");
    }

    private void updateStatus(String message, String color) {
        statusLabel.setText(message);
        statusLabel.setStyle("-fx-text-fill: " + color + ";");
    }

    private void clearFields() {
        fieldIDStudent.clear();
        fieldNameStudent.clear();
        fieldBirthdayStudent.setValue(null);
        fieldPhoneStudent.clear();
        fieldEmailStudent.clear();
    }

    private void clearCheckInforLabels() {
        String emptyMessage = "chưa có thông tin gì";
        checkInforId.setText(emptyMessage);
        checkInforName.setText(emptyMessage);
        checkInforBirthday.setText(emptyMessage);
        checkInforPhone.setText(emptyMessage);
        checkInforEmail.setText(emptyMessage);
        checkInforPassword.setText(emptyMessage);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setupValidationListeners();
    }

    private void setupValidationListeners() {
        fieldIDStudent.textProperty().addListener((obs, oldVal, newVal) ->
                validateField(fieldIDStudent, idValidLabel, ValidationLabelUtil.ValidationType.EMPTY));

        fieldNameStudent.textProperty().addListener((obs, oldVal, newVal) ->
                validateField(fieldNameStudent, nameValidLabel, ValidationLabelUtil.ValidationType.EMPTY));

        fieldBirthdayStudent.getEditor().textProperty().addListener((obs, oldVal, newVal) -> {
            String error = validationUtil.validateDateFormat(newVal);
            updateValidationLabel(birthValidLabel, error);
        });

        fieldPhoneStudent.textProperty().addListener((obs, oldVal, newVal) ->
                validateField(fieldPhoneStudent, phoneValidLabel, ValidationLabelUtil.ValidationType.PHONE));

        fieldEmailStudent.textProperty().addListener((obs, oldVal, newVal) ->
                validateField(fieldEmailStudent, emailValidLabel, ValidationLabelUtil.ValidationType.EMAIL));
    }
}
