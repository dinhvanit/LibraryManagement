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
import uet.librarymanagementsystem.services.userServices.ChangePasswordService;
import uet.librarymanagementsystem.services.userServices.DeleteStudentService;
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
    private TableColumn<Student, String> idColumnStudentsToDelete;

    @FXML
    private TableColumn<Student, String> nameColumnStudentsToDelete;

    @FXML
    private TableColumn<Student, String> passwordColumnStudentsToDelete;

    @FXML
    private TableColumn<Student, String> phoneColumnStudentsToDelete;

    @FXML
    private TableColumn<Student, String> birthdayColumnStudentsToDelete;

    @FXML
    private TableColumn<Student, String> emailColumnStudentsToDelete;

    @FXML
    private TableView<Student> searchStudentTableView;

    private SearchStudentService searchStudentService;

    @FXML
    private void addStudentButtonOnClick(MouseEvent event) {
        Student selectedStudent = searchStudentTableView.getSelectionModel().getSelectedItem();

        if (selectedStudent != null) {
            deleteStudentTableView.getItems().add(selectedStudent);

            searchStudentTableView.getItems().remove(selectedStudent);

            System.out.println("Sinh viên đã được thêm vào bảng Students to Delete.");
        } else {
            System.out.println("Vui lòng chọn một sinh viên từ bảng List Of Students.");
        }
    }


    @FXML
    private void deleteAllStudentButtonOnClick(MouseEvent event) {
        // Iterate over each student in the TableView
        for (Student student : deleteStudentTableView.getItems()) {
            // Delete each student from the database using deleteStudentByID method
            deleteStudentService.deleteStudentByID(student.getId());
            System.out.println("Student with ID " + student.getId() + " deleted from database.");
        }

        // Clear all students from the TableView
        deleteStudentTableView.getItems().clear();
        System.out.println("All students removed from the 'Students to Delete' table.");
    }

    private DeleteStudentService deleteStudentService = new DeleteStudentService();

    @FXML
    private void deleteStudentButtonOnClick(MouseEvent event) {
        Student selectedStudent = deleteStudentTableView.getSelectionModel().getSelectedItem();

        if (selectedStudent != null) {
            // Delete student from the database using deleteStudentByID method
            deleteStudentService.deleteStudentByID(selectedStudent.getId());

            // Remove student from the TableView
            deleteStudentTableView.getItems().remove(selectedStudent);
            System.out.println("Student with ID " + selectedStudent.getId() + " removed from UI.");
        } else {
            System.out.println("No student selected for deletion.");
        }
    }

    @FXML
    void removeStudentButtonOnClick(MouseEvent event) {
        Student selectedStudent = deleteStudentTableView.getSelectionModel().getSelectedItem();

        if (selectedStudent != null) {
            deleteStudentTableView.getItems().remove(selectedStudent);

            searchStudentTableView.getItems().add(selectedStudent);

            System.out.println("Sinh viên đã được xóa khỏi bảng Students to Delete và chuyển lại vào bảng List Of Students.");
        } else {
            System.out.println("Vui lòng chọn một sinh viên từ bảng Students to Delete để xóa.");
        }
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

    @FXML
    void modifyStudentButtonOnClick(MouseEvent event) {
        // Lấy sinh viên được chọn trong bảng "List Of Students"
        Student selectedStudent = searchStudentTableView.getSelectionModel().getSelectedItem();

        if (selectedStudent != null) {
            // Lấy ID của sinh viên
            String studentId = selectedStudent.getId();

            // Đặt lại mật khẩu thành ID của sinh viên
            ChangePasswordService changePasswordService = new ChangePasswordService();
            String resultMessage = changePasswordService.changePassword(studentId, studentId);

            // Cập nhật lại mật khẩu trong giao diện
            selectedStudent.setPassword(studentId); // Cập nhật trên UI
            searchStudentTableView.refresh(); // Làm mới lại TableView

            // Hiển thị thông báo kết quả
            System.out.println(resultMessage);
        } else {
            System.out.println("Vui lòng chọn một sinh viên để thay đổi mật khẩu.");
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


        idColumnStudentsToDelete.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getId()));
        nameColumnStudentsToDelete.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        birthdayColumnStudentsToDelete.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDateOfBirth()));
        phoneColumnStudentsToDelete.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPhoneNumber()));
        emailColumnStudentsToDelete.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmail()));
        passwordColumnStudentsToDelete.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPassword()));


    }
}
