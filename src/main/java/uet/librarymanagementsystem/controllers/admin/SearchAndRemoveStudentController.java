package uet.librarymanagementsystem.controllers.admin;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import uet.librarymanagementsystem.entity.users.Student;

public class SearchAndRemoveStudentController {
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

    @FXML
    void addStudentButtonOnClick(MouseEvent event) {

    }

    @FXML
    void deleteAllStudentButtonOnClick(MouseEvent event) {

    }

    @FXML
    void deleteStudentButtonOnClick(MouseEvent event) {

    }

    @FXML
    void removeStudentButtonOnClick(MouseEvent event) {

    }

}
