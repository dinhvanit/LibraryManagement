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

    private boolean isAdded = false; // Trạng thái kiểm tra đã thêm thông tin hay chưa
    private ValidationLabelUtil validationUtil = new ValidationLabelUtil();

    @FXML
    private DatePicker fieldBirthdayStudent;

    @FXML
    private TextField fieldEmailStudent;

    @FXML
    private TextField fieldIDStudent;

    @FXML
    private TextField fieldNameStudent;

    @FXML
    private TextField fieldPhoneStudent;

    @FXML
    private Label statusLabel; // Label hiển thị trạng thái

    @FXML
    private Label idValidLabel, nameValidLabel, birthValidLabel, phoneValidLabel, emailValidLabel;

    @FXML
    private Label checkInforId, checkInforName, checkInforBirthday, checkInforPhone, checkInforEmail, checkInforPassword;

    @FXML
    void addStudentButtonOnClick(MouseEvent event) {
        if (checkInput()) {
            LocalDate dob = fieldBirthdayStudent.getValue();
            String formattedDob = (dob != null) ? dob.format(DateTimeFormatter.ofPattern("yyyy/MM/dd")) : null;

            checkInforId.setText(fieldIDStudent.getText());
            checkInforName.setText(fieldNameStudent.getText());
            checkInforBirthday.setText(formattedDob);
            checkInforPhone.setText(fieldPhoneStudent.getText());
            checkInforEmail.setText(fieldEmailStudent.getText());
            checkInforPassword.setText(fieldIDStudent.getText()); // Mật khẩu mặc định là ID

            statusLabel.setStyle("-fx-text-fill: green;");

            statusLabel.setText("Đã tải thông tin. Vui lòng kiểm tra nhấn 'Save' để lưu.");
            isAdded = true;
            clearFields();
        } else {
            statusLabel.setStyle("-fx-text-fill: red;");
            statusLabel.setText("Vui lòng điền đúng thông tin vào tất cả các trường.");
            isAdded = false;
        }
    }

    private boolean checkInput() {
        boolean isValid = true;

        isValid &= validateField(fieldIDStudent, idValidLabel, ValidationLabelUtil.ValidationType.EMPTY);
        isValid &= validateField(fieldNameStudent, nameValidLabel, ValidationLabelUtil.ValidationType.EMPTY);
        isValid &= validateDateField(fieldBirthdayStudent, birthValidLabel);
        isValid &= validateField(fieldPhoneStudent, phoneValidLabel, ValidationLabelUtil.ValidationType.PHONE);
        isValid &= validateField(fieldEmailStudent, emailValidLabel, ValidationLabelUtil.ValidationType.EMAIL);

        return isValid;
    }

    private boolean validateField(TextField field, Label label, ValidationLabelUtil.ValidationType type) {
        String errorMessage = validationUtil.validateField(field.getText(), type);
        label.setText(errorMessage);
        label.setVisible(!errorMessage.isEmpty());
        return errorMessage.isEmpty();
    }

    private boolean validateDateField(DatePicker datePicker, Label label) {
        LocalDate date = datePicker.getValue();
        if (date == null) {
            label.setText("Ngày tháng không được để trống.");
            label.setVisible(true);
            return false;
        }
        label.setVisible(false);
        return true;
    }

    @FXML
    void removeStudentButtonOnClick(MouseEvent event) {
        isAdded = false;
        clearFields();
        clearCheckInforLabels();
        statusLabel.setStyle("-fx-text-fill: red;");
        statusLabel.setText("Thông tin đã được xóa.");
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
            statusLabel.setText(result);
            statusLabel.setStyle(result.equals("Sinh viên được thêm thành công.") ? "-fx-text-fill: green;" : "-fx-text-fill: red;");
        } else {
            statusLabel.setText("Vui lòng điền đầy đủ thông tin hợp lệ.");
            statusLabel.setStyle("-fx-text-fill: red;");
        }
    }

    private void clearFields() {
        fieldIDStudent.clear();
        fieldNameStudent.clear();
        fieldBirthdayStudent.setValue(null);
        fieldPhoneStudent.clear();
        fieldEmailStudent.clear();
    }

    private void clearCheckInforLabels() {
        checkInforId.setText("chưa có thông tin gì");
        checkInforName.setText("chưa có thông tin gì");
        checkInforBirthday.setText("chưa có thông tin gì");
        checkInforPhone.setText("chưa có thông tin gì");
        checkInforEmail.setText("chưa có thông tin gì");
        checkInforPassword.setText("chưa có thông tin gì");
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setupValidationListeners();
    }

    private void setupValidationListeners() {
        fieldIDStudent.textProperty().addListener((obs, oldVal, newVal) -> validateField(fieldIDStudent, idValidLabel, ValidationLabelUtil.ValidationType.EMPTY));
        fieldNameStudent.textProperty().addListener((obs, oldVal, newVal) -> validateField(fieldNameStudent, nameValidLabel, ValidationLabelUtil.ValidationType.EMPTY));
        // Lắng nghe thay đổi từ DatePicker
        fieldBirthdayStudent.getEditor().textProperty().addListener((obs, oldVal, newVal) -> {
            String error = validationUtil.validateDateFormat(newVal);
            birthValidLabel.setText(error);
        });
        fieldPhoneStudent.textProperty().addListener((obs, oldVal, newVal) -> validateField(fieldPhoneStudent, phoneValidLabel, ValidationLabelUtil.ValidationType.PHONE));
        fieldEmailStudent.textProperty().addListener((obs, oldVal, newVal) -> validateField(fieldEmailStudent, emailValidLabel, ValidationLabelUtil.ValidationType.EMAIL));
    }

    /*private boolean validateDocumentInput() {
        boolean isValid = true;

        // Kiểm tra trường Title
        if (fieldTitleAddDoc.getText().trim().isEmpty()) {
            titleValidLabel.setText("Title cannot be empty.");
            titleValidLabel.setVisible(true);
            isValid = false;
        } else {
            titleValidLabel.setVisible(false);
        }

        // Kiểm tra trường Author
        if (fieldAuthorAddDoc.getText().trim().isEmpty()) {
            authorValidLabel.setText("Author cannot be empty.");
            authorValidLabel.setVisible(true);
            isValid = false;
        } else {
            authorValidLabel.setVisible(false);
        }

        // Kiểm tra trường MaterialType
        if (choiceMaterialAddDoc.getValue() == null) {
            statusLabel.setText("Material Type must be selected.");
            statusLabel.setStyle("-fx-text-fill: red;");
            isValid = false;
        } else {
            statusLabel.setText("");
        }

        // Kiểm tra trường Category
        if (choiceCategoryAddDoc.getValue() == null) {
            statusLabel.setText("Category must be selected.");
            statusLabel.setStyle("-fx-text-fill: red;");
            isValid = false;
        } else {
            statusLabel.setText("");
        }

        // Kiểm tra ISBN nếu MaterialType là BOOK
        if (choiceMaterialAddDoc.getValue() == MaterialType.BOOK && fieldISBN.getText().trim().isEmpty()) {
            isbnValidLabel.setText("ISBN is required for books.");
            isbnValidLabel.setVisible(true);
            isValid = false;
        } else {
            isbnValidLabel.setVisible(false);
        }

        // Kiểm tra trường Quantity
        if (spinerQuantityAddDoc.getValue() == null || spinerQuantityAddDoc.getValue() <= 0) {
            quantityValidLabel.setText("Quantity must be greater than 0.");
            quantityValidLabel.setVisible(true);
            isValid = false;
        } else {
            quantityValidLabel.setVisible(false);
        }

        return isValid;
    }*/

}
