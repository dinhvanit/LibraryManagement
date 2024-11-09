package uet.librarymanagementsystem.controllers.admin;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import uet.librarymanagementsystem.DatabaseOperation.DatabaseManager;
import uet.librarymanagementsystem.entity.users.Student;
import uet.librarymanagementsystem.services.documentServices.SearchDocumentService;
import uet.librarymanagementsystem.services.userServices.AddStudentService;

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
    private TableView<Student> studentListTableView;
    private ObservableList<Student> studentList;

    @FXML
    void addStudentButtonOnClick(MouseEvent event) {
        // Lấy dữ liệu từ các TextField
        String id = fieldIDStudent.getText();
        String name = fieldNameStudent.getText();
        String birthday = fieldBirthdayStudent.getText();
        String phone = fieldPhoneStudent.getText();
        String email = fieldEmailStudent.getText();

        // Kiểm tra các trường có rỗng không
        if (id.isEmpty() || name.isEmpty() || birthday.isEmpty() || phone.isEmpty() || email.isEmpty()) {
            //statusLabel.setText("Vui lòng điền đầy đủ thông tin");
            return;
        }

        // Tạo đối tượng Student từ dữ liệu đã nhập
        Student student = new Student(id, name, birthday, phone, email, id);

        // Tạo một đối tượng của AddStudentService
        AddStudentService addStudentService = new AddStudentService();

        // Gọi phương thức addStudent trong AddStudentService để thêm vào cơ sở dữ liệu
        boolean success = addStudentService.addStudent(id, name, birthday, phone, email);

        // Kiểm tra kết quả và cập nhật giao diện
        if (success) {
            statusLabel.setText("Thêm sinh viên thành công");
            studentList.add(student); // Cập nhật TableView với sinh viên mới

            clearFields(); // Xóa các trường sau khi thêm thành công
        } else {
            statusLabel.setText("Lỗi khi thêm sinh viên");
            System.out.println("Them that bai");
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
        searchStudent = new SearchDocumentService(); // Khởi tạo searchDocumentService cho adminPage

        // Cấu hình các cột của bảng kết quả tìm kiếm trong adminPage
        idColumnSearchResults.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getId()));
        nameColumnSearchResults.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        birthdayColumnSearchResults.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDateOfBirth()));
        phoneColumnSearchResults.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPhoneNumber()));
        emailColumnSearchResults.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmail()));
        passwordColumnSearchResults.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPassword()));

    }
}
