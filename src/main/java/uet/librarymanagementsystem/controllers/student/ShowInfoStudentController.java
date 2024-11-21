package uet.librarymanagementsystem.controllers.student;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;

public class ShowInfoStudentController {

    @FXML
    private PieChart pieChart;

    public void initialize() {
        // Dữ liệu cho biểu đồ PieChart
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                new PieChart.Data("Sách đã trả", 40), // Ví dụ: 40 cuốn sách đã trả
                new PieChart.Data("Sách đang mượn", 30), // Ví dụ: 30 cuốn sách đang mượn
                new PieChart.Data("Sách chưa trả", 20)  // Ví dụ: 20 cuốn sách chưa trả
        );

        // Gán dữ liệu vào PieChart
        pieChart.setData(pieChartData);

        // Thiết lập các thuộc tính biểu đồ
        pieChart.setTitle("Tình trạng mượn sách");
        pieChart.setLabelsVisible(true); // Hiển thị nhãn trên biểu đồ
        pieChart.setLegendVisible(true); // Hiển thị chú thích (legend)
    }
}