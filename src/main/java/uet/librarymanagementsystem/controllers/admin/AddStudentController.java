package uet.librarymanagementsystem.controllers.admin;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import uet.librarymanagementsystem.DatabaseOperation.DatabaseManager;
import uet.librarymanagementsystem.entity.users.Student;
import uet.librarymanagementsystem.services.documentServices.SearchDocumentService;
import uet.librarymanagementsystem.services.userServices.AddStudentService;
import uet.librarymanagementsystem.util.ValidationLabelUtil;

import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class AddStudentController implements Initializable {

    private SearchDocumentService searchStudent;

    private String idStudent;
    private String nameStudent;
    private String birthdayStudent;
    private String phoneStudent;
    private String emailStudent;
    private String passwordStudent;

    private boolean isAdded = false; // Biến trạng thái để kiểm tra xem đã thêm thông tin hay chưa


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
    private Label statusLabel;  // Label để hiển thị thông báo

    @FXML
    private Label idValidLabel;

    @FXML
    private Label nameValidLabel;

    @FXML
    private Label birthValidLabel;

    @FXML
    private Label phoneValidLabel;

    @FXML
    private Label emailValidLabel;

    @FXML
    private Label checkInforId, checkInforName, checkInforBirthday, checkInforPhone, checkInforEmail, checkInforPassword;

    @FXML
    void addStudentButtonOnClick(MouseEvent event) {
        if (checkInput()) {
            // Cập nhật thông tin vào các checkInforLabel
            // Lấy giá trị từ DatePicker và định dạng lại
            LocalDate dob = fieldBirthdayStudent.getValue();
            String formattedDob = null;

            // Kiểm tra nếu dob không null, chuyển đổi sang định dạng yyyy/MM/dd
            if (dob != null) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
                formattedDob = dob.format(formatter);
            }

            checkInforId.setText(fieldIDStudent.getText());
            checkInforName.setText(fieldNameStudent.getText());
            checkInforBirthday.setText(formattedDob);
            checkInforPhone.setText(fieldPhoneStudent.getText());
            checkInforEmail.setText(fieldEmailStudent.getText());
            checkInforPassword.setText(fieldIDStudent.getText()); // Mật khẩu mặc định là ID

            statusLabel.setStyle("-fx-text-fill: green;");
            statusLabel.setText("Đã tải thông tin. Vui lòng kiểm tra nhấn 'Save' để lưu");
            isAdded = true;
            clearFields();
        } else {
            isAdded = false;
            statusLabel.setStyle("-fx-text-fill: red;");
            statusLabel.setText("Vui lòng điền đúng thông tin vào tất cả các trường.");
        }
    }

    private void clearFields() {
        fieldIDStudent.clear();
        fieldNameStudent.clear();
        fieldBirthdayStudent.setValue(null);
        fieldPhoneStudent.clear();
        fieldEmailStudent.clear();
        /*idValidLabel.setText("");
        nameValidLabel.setText("");
        birthValidLabel.setText("");
        phoneValidLabel.setText("");
        emailValidLabel.setText("");*/
    }

    private boolean checkInput() {
        boolean isValid = true;

        // Validate empty fields
        if (!ValidationLabelUtil.validateEmptyField(fieldIDStudent, idValidLabel)) isValid = false;
        if (!ValidationLabelUtil.validateEmptyField(fieldNameStudent, nameValidLabel)) isValid = false;
        if (!ValidationLabelUtil.validateEmptyField(fieldBirthdayStudent, birthValidLabel)) isValid = false;
        if (!ValidationLabelUtil.validateEmptyField(fieldPhoneStudent, phoneValidLabel)) isValid = false;
        if (!ValidationLabelUtil.validateEmptyField(fieldEmailStudent, emailValidLabel)) isValid = false;

        // Validate email and phone format if not empty
        if (!fieldEmailStudent.getText().isEmpty()) {
            ValidationLabelUtil.validateEmailFormat(fieldEmailStudent.getText(), emailValidLabel);
            if (!emailValidLabel.getText().isEmpty()) isValid = false;
        }
        if (!fieldPhoneStudent.getText().isEmpty()) {
            ValidationLabelUtil.validatePhoneFormat(fieldPhoneStudent.getText(), phoneValidLabel);
            if (!phoneValidLabel.getText().isEmpty()) isValid = false;
        }

        // Validate birthday format if not empty
        if (!fieldBirthdayStudent.getEditor().getText().isEmpty()) {
            ValidationLabelUtil.validateBirthdayFormat(fieldBirthdayStudent.getEditor().getText(), birthValidLabel);
            if (!birthValidLabel.getText().isEmpty()) isValid = false;
        }

        return isValid;
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
        // Kiểm tra nếu thông tin hợp lệ
        if (isAdded) {
            // Lấy dữ liệu từ các Label kiểm tra thông tin
            String id = checkInforId.getText();
            String name = checkInforName.getText();
            String phoneNumber = checkInforPhone.getText();
            String email = checkInforEmail.getText();

            // Lấy giá trị từ DatePicker (ngày sinh) và định dạng lại
            String formattedDob = checkInforBirthday.getText(); // Dữ liệu ngày sinh từ checkInforBirthday

            // Gọi service để thêm sinh viên và nhận thông báo
            String result = AddStudentService.addStudent(id, name, formattedDob, phoneNumber, email);

            // Hiển thị thông báo trên statusLabel
            statusLabel.setText(result);

            // Đổi màu Label dựa trên kết quả
            if (result.equals("Sinh viên được thêm thành công.")) {
                statusLabel.setStyle("-fx-text-fill: green;");
            } else {
                statusLabel.setStyle("-fx-text-fill: red;");
            }
        } else {
            // Nếu thông tin chưa hợp lệ
            statusLabel.setText("Vui lòng điền đầy đủ thông tin hợp lệ.");
            statusLabel.setStyle("-fx-text-fill: red;");
        }
    }

    // Hàm để xóa các nhãn kiểm tra thông tin
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
        ValidationLabelUtil validationLabelUtil = new ValidationLabelUtil();
        // kiểm tra định dạng khi nhập liệu
        setupValidationListeners();
    }

    //kiểm tra khi nhập
    private void setupValidationListeners() {
        fieldIDStudent.textProperty().addListener((obs, oldVal, newVal) ->
                ValidationLabelUtil.validateEmptyField(fieldIDStudent, idValidLabel));

        fieldNameStudent.textProperty().addListener((obs, oldVal, newVal) ->
                ValidationLabelUtil.validateEmptyField(fieldNameStudent, nameValidLabel));

        fieldEmailStudent.textProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal.trim().isEmpty()) {
                emailValidLabel.setText("Vui lòng điền vào trường này");
            } else {
                ValidationLabelUtil.validateEmailFormat(newVal, emailValidLabel);
            }
        });

        fieldPhoneStudent.textProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal.trim().isEmpty()) {
                phoneValidLabel.setText("Vui lòng điền vào trường này");
            } else {
                ValidationLabelUtil.validatePhoneFormat(newVal, phoneValidLabel);
            }
        });

        fieldBirthdayStudent.getEditor().textProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal.trim().isEmpty()) {
                birthValidLabel.setText("Vui lòng chọn ngày sinh");
            } else {
                ValidationLabelUtil.validateBirthdayFormat(newVal, birthValidLabel);
            }
        });
    }
}
