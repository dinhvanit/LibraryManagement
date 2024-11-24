package uet.librarymanagementsystem.controllers.admin;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.*;
import javafx.scene.control.Label;
import uet.librarymanagementsystem.DatabaseOperation.TransactionsTable;
import uet.librarymanagementsystem.services.documentServices.SearchDocumentService;
import uet.librarymanagementsystem.services.transactionServices.SearchTransactionService;
import uet.librarymanagementsystem.services.userServices.SearchStudentService;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class HomeAdminController implements Initializable {

    @FXML
    private NumberAxis countOfCategoryY;

    @FXML
    private CategoryAxis nameOfCategoryX;

    @FXML
    private BarChart<String, Integer> barChartTopCategories;

    @FXML
    private CategoryAxis timeX;

    @FXML
    private NumberAxis countOfDocumentY;

    @FXML
    private LineChart<String, Number> lineChartStatusBorrowing;

    @FXML
    private Label borrowingTotalLabel;

    @FXML
    private Label totalCategoriesLabel;
    @FXML
    private Label totalDocumentsLabel;

    @FXML
    private Label totalUsersLabel;

    /**
     * Initializes the admin home page.
     * @param url the location used to resolve relative paths for the root object
     * @param resourceBundle the resources used to localize the root object
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Set the total number of categories (static value in this example).
        totalCategoriesLabel.setText("31");

        // Retrieve the total number of documents from the database.
        SearchDocumentService searchDocumentService = new SearchDocumentService();
        try {
            totalDocumentsLabel.setText(String.valueOf(
                    searchDocumentService.searchAll(null, null, null, null).size()));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // Retrieve the total number of users from the database.
        SearchStudentService searchStudentService = new SearchStudentService();
        try {
            totalUsersLabel.setText(String.valueOf(searchStudentService.search(null, null).size()));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // Retrieve the total number of borrowed documents from the database.
        SearchTransactionService searchTransactionService = new SearchTransactionService();
        try {
            borrowingTotalLabel.setText(String.valueOf(searchTransactionService.searchTransactionBorrowing().size()));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // Configure BarChart for top 5 most borrowed categories.
        barChartTopCategories.setTitle("Top 5 most borrowed categories");
        nameOfCategoryX.setLabel("Categories");
        countOfCategoryY.setLabel("Borrow count");

        // Create data series for the BarChart.
        XYChart.Series<String, Integer> seriesBar = new XYChart.Series<>();
        seriesBar.setName("Data");
        ObservableList<String> topCategories = TransactionsTable.getTop5Categories();

        // Add data to the BarChart series.
        for (String categoryData : topCategories) {
            String[] parts = categoryData.split(" ");
            String category = parts[0];
            int count = Integer.parseInt(parts[1]);
            seriesBar.getData().add(new XYChart.Data<>(category, count));
        }

        // Add the series to the BarChart.
        barChartTopCategories.getData().add(seriesBar);

        // Configure LineChart for documents borrowed over the last 7 days.
        timeX.setLabel("Date");
        countOfDocumentY.setLabel("Number of Documents Borrowed");
        lineChartStatusBorrowing.setTitle("Documents Borrowed Over the Last 7 Days");

        // Retrieve data for the LineChart from the database.
        ObservableList<String> borrowData = TransactionsTable.getBooksBorrowedLastWeek();
        XYChart.Series<String, Number> seriesLine = new XYChart.Series<>();
        seriesLine.setName("Documents Borrowed");

        // Add data to the LineChart series.
        for (String data : borrowData) {
            String[] parts = data.split(" "); // Split the date and count.
            String borrowDate = parts[0]; // Date of borrowing.
            int borrowCount = Integer.parseInt(parts[1]); // Number of documents borrowed on that date.

            // Add the data point to the LineChart.
            seriesLine.getData().add(new XYChart.Data<>(borrowDate, borrowCount));
        }

        // Add the series to the LineChart.
        lineChartStatusBorrowing.getData().add(seriesLine);
    }
}
