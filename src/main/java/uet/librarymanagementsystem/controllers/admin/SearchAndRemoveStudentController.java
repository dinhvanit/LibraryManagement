package uet.librarymanagementsystem.controllers.admin;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import uet.librarymanagementsystem.entity.Page;
import uet.librarymanagementsystem.entity.users.Student;
import uet.librarymanagementsystem.services.PDFServices.ExportTransactionToPDF;
import uet.librarymanagementsystem.services.shareDataServers.ShareDataService;
import uet.librarymanagementsystem.services.userServices.ChangePasswordService;
import uet.librarymanagementsystem.services.userServices.DeleteStudentService;
import uet.librarymanagementsystem.services.userServices.SearchStudentService;
import uet.librarymanagementsystem.util.WindowUtil;

import java.io.IOException;
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
    private Label notionChoiceAddLabel;

    @FXML
    private Label notionChoiceDeleteLabel;

    private final DeleteStudentService deleteStudentService = new DeleteStudentService();

    /**
     * Adds a selected student from the search results table to the "Students to Delete" table.=
     * @param event the mouse event triggered when the button is clicked
     */
    @FXML
    private void addStudentButtonOnClick(MouseEvent event) {
        Student selectedStudent = searchStudentTableView.getSelectionModel().getSelectedItem();

        if (selectedStudent != null) {
            deleteStudentTableView.getItems().add(selectedStudent);
            searchStudentTableView.getItems().remove(selectedStudent);
            notionChoiceAddLabel.setVisible(false);
            System.out.println("The student has been added to the 'Students to Delete' table.");
        } else {
            System.out.println("Please select a student from the 'List of Students' table.");
            notionChoiceAddLabel.setVisible(true);
        }
    }

    /**
     * Deletes all students in the "Students to Delete" table from the database.=
     * @param event the mouse event triggered when the button is clicked
     */
    @FXML
    private void deleteAllStudentButtonOnClick(MouseEvent event) {
        for (Student student : deleteStudentTableView.getItems()) {
            deleteStudentService.deleteStudentByID(student.getId());
            System.out.println("Student with ID " + student.getId() + " deleted from the database.");
        }

        deleteStudentTableView.getItems().clear();
        System.out.println("All students removed from the 'Students to Delete' table.");
    }

    /**
     * Deletes the selected student in the "Students to Delete" table from the database.=
     * @param event the mouse event triggered when the button is clicked
     */
    @FXML
    private void deleteStudentButtonOnClick(MouseEvent event) {
        Student selectedStudent = deleteStudentTableView.getSelectionModel().getSelectedItem();

        if (selectedStudent != null) {
            notionChoiceDeleteLabel.setVisible(false);
            deleteStudentService.deleteStudentByID(selectedStudent.getId());
            deleteStudentTableView.getItems().remove(selectedStudent);
            System.out.println("Student with ID " + selectedStudent.getId() + " removed from the UI.");
        } else {
            notionChoiceDeleteLabel.setVisible(true);
            System.out.println("No student selected for deletion.");
        }
    }

    /**
     * Removes a selected student from the "Students to Delete" table and adds them back to the search results table.=
     * @param event the mouse event triggered when the button is clicked
     */
    @FXML
    void removeStudentButtonOnClick(MouseEvent event) {
        Student selectedStudent = deleteStudentTableView.getSelectionModel().getSelectedItem();

        if (selectedStudent != null) {
            notionChoiceDeleteLabel.setVisible(false);
            deleteStudentTableView.getItems().remove(selectedStudent);
            searchStudentTableView.getItems().add(selectedStudent);
            System.out.println("The student has been removed from the 'Students to Delete' table and added back to the 'List of Students' table.");
        } else {
            notionChoiceDeleteLabel.setVisible(true);
            System.out.println("Please select a student from the 'Students to Delete' table to remove.");
        }
    }

    /**
     * Searches for students based on user input and updates the search results table.=
     * @param event the mouse event triggered when the button is clicked
     */
    @FXML
    void searchStudentButtonOnClick(MouseEvent event) {
        String id = fieldIDStudent.getText();
        String name = fieldNameStudent.getText();

        try {
            ObservableList<Student> searchResults = searchStudentService.search(id, name);
            searchStudentTableView.setItems(searchResults);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error occurred while searching for students: " + e.getMessage());
        }
    }

    /**
     * Resets the password of the selected student in the "List of Students" table to their ID.=
     * @param event the mouse event triggered when the button is clicked
     */
    @FXML
    void modifyStudentButtonOnClick(MouseEvent event) {
        Student selectedStudent = searchStudentTableView.getSelectionModel().getSelectedItem();

        if (selectedStudent != null) {
            String studentId = selectedStudent.getId();
            ChangePasswordService changePasswordService = new ChangePasswordService();
            String resultMessage = changePasswordService.changePassword(studentId, studentId);

            selectedStudent.setPassword(studentId);
            searchStudentTableView.refresh();

            System.out.println(resultMessage);
        } else {
            System.out.println("Please select a student to change their password.");
        }
    }

    /**
     * Opens a new window displaying detailed information about the selected student.=
     * @param event the mouse event triggered when the button is clicked
     */
    @FXML
    void infoStudentButtonClick(MouseEvent event) {
        Student selectedStudent = searchStudentTableView.getSelectionModel().getSelectedItem();

        if (selectedStudent != null) {
            ShareDataService.setIdStudentShare(selectedStudent.getId());
            notionChoiceAddLabel.setVisible(false);
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            WindowUtil.showSecondaryWindow(Page.SHOW_INFO, "Information Student", currentStage);
            System.out.println("Student information is being displayed.");
        } else {
            System.out.println("Please select a student from the 'List of Students' table.");
            notionChoiceAddLabel.setVisible(true);
        }
    }

    /**
     * Exports transaction details of the selected student to a PDF file.=
     * @param event the mouse event triggered when the button is clicked
     * @throws IOException if an error occurs during the export process
     */
    @FXML
    void exportPDFClick(MouseEvent event) throws IOException {
        Student selectedStudent = searchStudentTableView.getSelectionModel().getSelectedItem();
        if (selectedStudent != null) {
            ShareDataService.setIdStudentShare(selectedStudent.getId());
            ExportTransactionToPDF.exportTransactionToPDF(ShareDataService.getIdStudentShare());
            notionChoiceAddLabel.setVisible(false);
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            WindowUtil.showSecondaryWindow(Page.NOTION_SUCCESS, "Export PDF", currentStage);
        } else {
            notionChoiceAddLabel.setVisible(true);
        }
    }

    /**
     * Initializes the controller.
     * @param url the location used to resolve relative paths for the root object
     * @param resourceBundle the resources used to localize the root object
     */
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
