package uet.librarymanagementsystem.controllers.admin;

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import uet.librarymanagementsystem.DatabaseOperation.DatabaseManager;
import uet.librarymanagementsystem.entity.users.Student;
import uet.librarymanagementsystem.services.documentServices.SearchDocument;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class AddStudentController implements Initializable{

    private SearchDocument searchStudent;

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
    void addStudentButtonOnClick(MouseEvent event) {

    }

    @FXML
    void removeStudentButtonOnClick(MouseEvent event) {

    }

    @FXML
    void saveAllStudentButtonOnClick(MouseEvent event) {

    }

    @FXML
    void searchStudentButtonOnClick(MouseEvent event) {

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        conn = DatabaseManager.connect();
        searchStudent = new SearchDocument(); // Khởi tạo searchDocumentService cho adminPage

        // Cấu hình các cột của bảng kết quả tìm kiếm trong adminPage
        //idColumnSearchResults.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getId()));
        nameColumnSearchResults.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        birthdayColumnSearchResults.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDateOfBirth()));
        phoneColumnSearchResults.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPhoneNumber()));
        emailColumnSearchResults.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmail()));
        passwordColumnSearchResults.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPassword()));

    }
}
