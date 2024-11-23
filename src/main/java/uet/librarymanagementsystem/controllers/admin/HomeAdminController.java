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


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        totalCategoriesLabel.setText("31");
        SearchDocumentService searchDocumentService = new SearchDocumentService();
        try {
            totalDocumentsLabel.setText(String.valueOf(
                    searchDocumentService.searchAll(null, null, null, null).size()));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        SearchStudentService searchStudentService = new SearchStudentService();
        try {
            totalUsersLabel.setText(String.valueOf(searchStudentService.search(null, null).size()));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        SearchTransactionService searchTransactionService = new SearchTransactionService();
        try {
            borrowingTotalLabel.setText(String.valueOf(searchTransactionService.searchTransactionBorrowing().size()));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        barChartTopCategories.setTitle("Top 5 most borrowed categories");
        nameOfCategoryX.setLabel("Categories");
        countOfCategoryY.setLabel("Borrow count");

        // Tạo dữ liệu cho BarChart
        XYChart.Series<String, Integer> seriesBar = new XYChart.Series<>();
        seriesBar.setName("Data");
        ObservableList<String> topCategories = TransactionsTable.getTop5Categories();
        // Thêm các dữ liệu vào Series từ topCategories
        for (String categoryData : topCategories) {
            String[] parts = categoryData.split(" ");
            String category = parts[0];
            int count = Integer.parseInt(parts[1]);
            seriesBar.getData().add(new XYChart.Data<>(category, count));
        }

        // Thêm Series vào BarChart
        barChartTopCategories.getData().add(seriesBar);


        // Thiết lập Label cho trục X và Y của LineChart
        timeX.setLabel("Date");
        countOfDocumentY.setLabel("Number of Documents Borrowed");
        lineChartStatusBorrowing.setTitle("Documents Borrowed Over the Last 7 Days");

        // Lấy dữ liệu từ database và thêm vào LineChart
        ObservableList<String> borrowData = TransactionsTable.getBooksBorrowedLastWeek();
        XYChart.Series<String, Number> seriesLine = new XYChart.Series<>();
        seriesLine.setName("Documents Borrowed");

        // Thêm dữ liệu vào series
        for (String data : borrowData) {
            String[] parts = data.split(" ");  // Phân tách ngày và số lượng mượn
            String borrowDate = parts[0];      // Ngày mượn
            int borrowCount = Integer.parseInt(parts[1]);  // Số sách mượn trong ngày

            // Thêm vào LineChart: Trục X là ngày (borrowDate), trục Y là số sách mượn (borrowCount)
            seriesLine.getData().add(new XYChart.Data<>(borrowDate, borrowCount));
        }

        // Thêm series vào LineChart
        lineChartStatusBorrowing.getData().add(seriesLine);

    }
}
