package uet.librarymanagementsystem.controllers.student;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import uet.librarymanagementsystem.controllers.LoginController;
import uet.librarymanagementsystem.services.shareDataServers.ShareDataService;
import uet.librarymanagementsystem.services.userServices.SearchStudentService;
import uet.librarymanagementsystem.entity.users.Student;
import uet.librarymanagementsystem.controllers.LoginController;
import uet.librarymanagementsystem.services.transactionServices.SearchTransactionService;

import java.sql.SQLException;

/**
 * Controller class for displaying student information.
 * This class retrieves and displays student personal details and
 * borrowing statistics, and visualizes them using a pie chart.
 */
public class ShowInfoStudentController {

    // UI components
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

    // Services for retrieving student and transaction data
    private final SearchStudentService searchStudentService;
    private final SearchTransactionService searchTransactionService = new SearchTransactionService();

    /**
     * Constructor for ShowInfoStudentController.
     * Initializes the service for searching student information.
     */
    public ShowInfoStudentController() {
        searchStudentService = new SearchStudentService();
    }

    /**
     * Initializes the controller.
     * This method retrieves student information and borrowing statistics,
     * updates the UI labels, and populates the pie chart.
     */
    public void initialize() {
        try {
            // Retrieve the shared student ID
            String userId = ShareDataService.getIdStudentShare();

            // Retrieve student information by ID
            Student student = searchStudentService.searchID(userId);
            if (student != null) {
                // Display student's personal information
                idStudentLabel.setText(student.getId());
                nameStudentLabel.setText(student.getName());
                phoneStudentLabel.setText(student.getPhoneNumber());
                datebirthStudentLabel.setText(student.getDateOfBirth());
                emailStudentLabel.setText(student.getEmail());
            }

            // Retrieve borrowing statistics
            int[] bookCounts = searchTransactionService.countBooksByStatus(userId);
            int returned = bookCounts[0]; // Number of returned books
            int withinDue = bookCounts[1]; // Number of books borrowed within due date
            int overdue = bookCounts[2]; // Number of overdue books
            int totalBorrowed = returned + withinDue + overdue;

            // Update labels with borrowing statistics
            totalBorrowedDocumentsLabel.setText(String.valueOf(totalBorrowed));
            returnedDocumentsLabel.setText(String.valueOf(returned));
            borrowedWithinDueLabel.setText(String.valueOf(withinDue));
            borrowedOverdueLabel.setText(String.valueOf(overdue));

            // Populate pie chart with borrowing statistics
            ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                    new PieChart.Data("Returned Documents", returned),
                    new PieChart.Data("Borrowed Documents (Within Due Date)", withinDue),
                    new PieChart.Data("Borrowed Documents (Overdue)", overdue)
            );

            pieChart.setData(pieChartData);
            pieChart.setTitle("Borrowed Book Overview");

        } catch (SQLException e) {
            e.printStackTrace();
            // Error handling: You can display an error message on the UI
        }
    }
}
