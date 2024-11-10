package uet.librarymanagementsystem.controllers.admin;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import uet.librarymanagementsystem.entity.users.Student;
import uet.librarymanagementsystem.services.userServices.SearchStudentService;

import java.net.URL;
import java.util.ResourceBundle;

public class SearchAndRemoveStudentController implements Initializable {
    @FXML
    private TableColumn<Student, String> birthdayColumnSearchResults;

    @FXML
    private TableView<Student> deleteStudentTableView;

    @FXML
    private TableColumn<Student, String> emailColumnSearchResults;

    @FXML
    private TextField fieldIDStudent;

    @FXML
    private TextField fieldNameStudent;

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

    private SearchStudentService searchStudentService;

    @FXML
    void deleteAllStudentButtonOnClick(MouseEvent event) {

    }

    @FXML
    void deleteStudentButtonOnClick(MouseEvent event) {

    }

    @FXML
    void removeStudentButtonOnClick(MouseEvent event) {

    }

    @FXML
    void searchStudentButtonOnClick(MouseEvent event) {
        String id = fieldIDStudent.getText();
        String name = fieldNameStudent.getText();

        try {
            ObservableList<Student> searchResults = searchStudentService.search(id, name);
            searchStudentTableView.setItems(searchResults); // Cập nhật kết quả tìm kiếm vào TableView
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Lỗi khi tìm kiếm sinh viên: " + e.getMessage());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Khởi tạo các thành phần cần thiết, ví dụ như khởi tạo dịch vụ tìm kiếm
        searchStudentService = new SearchStudentService();

        // Cấu hình TableView, nếu cần
        idColumnSearchResults.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getId()));
        nameColumnSearchResults.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        birthdayColumnSearchResults.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDateOfBirth()));
        phoneColumnSearchResults.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPhoneNumber()));
        emailColumnSearchResults.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmail()));
        passwordColumnSearchResults.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPassword()));
    }
}
