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
import java.util.ResourceBundle;

public class AddStudentController implements Initializable{

    private SearchDocumentService searchStudent;

    private Connection conn;
    private String idStudent;
    private String nameStudent;
    private String birthdayStudent;
    private String phoneStudent;
    private String emailStudent;
    private String passwordStudent;

    @FXML
    private TableColumn<Student, String> birthdayColumnSearchResults;

    @FXML
    private TableColumn<Student, String> emailColumnSearchResults;

    @FXML
    private TextField fieldBirthdayStudent;

    @FXML
    private TextField fieldEmailStudent;

    @FXML
    private TextField fieldIDStudent;

    @FXML
    private TextField fieldNameStudent;

    @FXML
    private TextField fieldPasswordStudent;

    @FXML
    private TextField fieldPhoneStudent;

    @FXML
    private TableColumn<Student, String> idColumnSearchResults;

    @FXML
    private TableColumn<Student, String> nameColumnSearchResults;

    @FXML
    private TableColumn<Student, String> passwordColumnSearchResults;

    @FXML
    private TableColumn<Student, String> phoneColumnSearchResults;

    @FXML
    private TableView<Student> searchStudentTableView;

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
    private TableView<Student> studentListTableView;
    private ObservableList<Student> studentList;

    @FXML
    void addStudentButtonOnClick(MouseEvent event) {
        if (checkInput()) {
            // Thực hiện logic thêm sinh viên ở đây nếu tất cả các trường hợp lệ
            statusLabel.setText("Thêm sinh viên thành công");
            clearFields();
        } else {
            statusLabel.setText("Vui lòng điền đúng thông tin vào tất cả các trường.");
        }
    }

    // Hàm xóa các trường sau khi thêm
    private void clearFields() {
        fieldIDStudent.clear();
        fieldNameStudent.clear();
        fieldBirthdayStudent.clear();
        fieldPhoneStudent.clear();
        fieldEmailStudent.clear();

    }

    private boolean checkInput() {
        boolean isValid = true;

        // Check for empty fields and return false immediately if any are empty
        if (!ValidationLabelUtil.validateEmptyField(fieldIDStudent.getText(), idValidLabel)) isValid = false;
        if (!ValidationLabelUtil.validateEmptyField(fieldNameStudent.getText(), nameValidLabel)) isValid = false;
        if (!ValidationLabelUtil.validateEmptyField(fieldBirthdayStudent.getText(), birthValidLabel)) isValid = false;
        if (!ValidationLabelUtil.validateEmptyField(fieldPhoneStudent.getText(), phoneValidLabel)) isValid = false;
        if (!ValidationLabelUtil.validateEmptyField(fieldEmailStudent.getText(), emailValidLabel)) isValid = false;

        // Perform additional format checks and return false if any format is incorrect
        if (!fieldEmailStudent.getText().isEmpty()) {
            ValidationLabelUtil.validateEmailFormat(fieldEmailStudent.getText(), emailValidLabel);
            if (!emailValidLabel.getText().isEmpty()) isValid = false;
        }
        if (!fieldPhoneStudent.getText().isEmpty()) {
            ValidationLabelUtil.validatePhoneFormat(fieldPhoneStudent.getText(), phoneValidLabel);
            if (!phoneValidLabel.getText().isEmpty()) isValid = false;
        }
        if (!fieldBirthdayStudent.getText().isEmpty()) {
            ValidationLabelUtil.validateBirthdayFormat(fieldBirthdayStudent.getText(), birthValidLabel);
            if (!birthValidLabel.getText().isEmpty()) isValid = false;
        }

        return isValid;
    }

    @FXML
    void removeStudentButtonOnClick(MouseEvent event) {

    }

    @FXML
    void removeAllStudentButtonOnClick(MouseEvent event) {

    }

    @FXML
    void saveAllStudentButtonOnClick(MouseEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        conn = DatabaseManager.connect();
        ValidationLabelUtil validationLabelUtil = new ValidationLabelUtil();
        // Thêm các sự kiện kiểm tra định dạng khi nhập liệu
        fieldEmailStudent.textProperty().addListener((observable, oldValue, newValue) ->
                ValidationLabelUtil.validateEmailFormat(newValue, emailValidLabel));

        fieldPhoneStudent.textProperty().addListener((observable, oldValue, newValue) ->
                ValidationLabelUtil.validatePhoneFormat(newValue, phoneValidLabel));

        fieldBirthdayStudent.textProperty().addListener((observable, oldValue, newValue) ->
                ValidationLabelUtil.validateBirthdayFormat(newValue, birthValidLabel));

    }
}
