package uet.librarymanagementsystem.controllers.student;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import uet.librarymanagementsystem.controllers.LoginController;
import uet.librarymanagementsystem.services.userServices.SearchStudentService;
import uet.librarymanagementsystem.entity.users.Student;
import uet.librarymanagementsystem.controllers.LoginController;
import uet.librarymanagementsystem.services.transactionServices.SearchTransactionService;

import java.sql.SQLException;

public class ShowInfoStudentController {

    @FXML
    private PieChart pieChart;

    @FXML
    private Label idStudentLabel;
    @FXML
    private Label nameStudentLabel;
    @FXML
    private Label phoneStudentLabel;
    @FXML
    private Label datebirthStudentLabel;
    @FXML
    private Label emailStudentLabel;

    @FXML
    private Label totalBorrowedDocumentsLabel;
    @FXML
    private Label returnedDocumentsLabel;
    @FXML
    private Label borrowedWithinDueLabel;
    @FXML
    private Label borrowedOverdueLabel;

    private final SearchStudentService searchStudentService;

    private final SearchTransactionService searchTransactionService = new SearchTransactionService();

    public ShowInfoStudentController() {
        searchStudentService = new SearchStudentService();
    }

    public void initialize() {
        try {

            String userId = LoginController.getIdCurrentStudent();
            // Tìm thông tin sinh viên dựa trên ID
            Student student = searchStudentService.searchID(userId);
            if (student != null) {
                // Hiển thị thông tin cá nhân của sinh viên
                idStudentLabel.setText(student.getId());
                nameStudentLabel.setText(student.getName());
                phoneStudentLabel.setText(student.getPhoneNumber());
                datebirthStudentLabel.setText(student.getDateOfBirth());
                emailStudentLabel.setText(student.getEmail());
            }

            int[] bookCounts = searchTransactionService.countBooksByStatus(userId);
            int returned = bookCounts[0];
            int withinDue = bookCounts[1];
            int overdue = bookCounts[2];
            int totalBorrowed = returned + withinDue + overdue;

            // Cập nhật dữ liệu cho các label
            totalBorrowedDocumentsLabel.setText(String.valueOf(totalBorrowed));
            returnedDocumentsLabel.setText(String.valueOf(returned));
            borrowedWithinDueLabel.setText(String.valueOf(withinDue));
            borrowedOverdueLabel.setText(String.valueOf(overdue));

            // Thêm dữ liệu vào biểu đồ
            ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                    new PieChart.Data("Returned Documents", returned),
                    new PieChart.Data("Borrowed Documents (Within Due Date)", withinDue),
                    new PieChart.Data("Borrowed Documents (Overdue)", overdue)
            );

            pieChart.setData(pieChartData);
            pieChart.setTitle("Borrowed Book Overview");

        } catch (SQLException e) {
            e.printStackTrace();
            // có thể hiển thị label lỗi
        }
    }
}
