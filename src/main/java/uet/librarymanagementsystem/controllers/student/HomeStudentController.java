package uet.librarymanagementsystem.controllers.student;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.collections.ObservableList;
import uet.librarymanagementsystem.entity.documents.Document;
import uet.librarymanagementsystem.services.TaskService;
import uet.librarymanagementsystem.services.documentServices.Get6LatestDoc;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class HomeStudentController implements Initializable {

    @FXML
    private FlowPane recentDocsFlowPane;

    @FXML
    private FlowPane recommendBooksFlowPane;

    // Sử dụng TaskService từ Singleton Pattern
    private final TaskService taskService = TaskService.getInstance();

    // Method to load recent documents asynchronously
    private void loadRecentDocs() {
        System.out.println("Loading recent documents...");

        // Sử dụng TaskService để chạy tác vụ nền
        taskService.runTask(() -> {
            try {
                // Fetch the latest document list from the database
                ObservableList<Document> latestTitles = Get6LatestDoc.getLatestTitles();

                // Cập nhật giao diện trên JavaFX Application Thread
                javafx.application.Platform.runLater(() -> {
                    for (Document document : latestTitles) {
                        // Tạo VBox cho mỗi tài liệu và thêm vào FlowPane
                        VBox documentVBox = createDocumentVBox(document);
                        recentDocsFlowPane.getChildren().add(documentVBox);
                    }
                    System.out.println("Successfully added documents to recentDocsFlowPane.");
                });

            } catch (SQLException e) {
                System.err.println("Error occurred while fetching the latest documents: " + e.getMessage());
                e.printStackTrace();
            }
        });
    }

    // Asynchronous method to load recommended books
    private void loadRecommendBooks(String category) {
        // Implement your logic to fetch and display recommended books here
        System.out.println("Loading recommended books for category: " + category);
        taskService.runTask(() -> {
            // Placeholder logic for recommended books
            System.out.println("Recommended books loaded for category: " + category);
        });
    }

    // Method to create a VBox for a document
    private VBox createDocumentVBox(Document document) {
        try {
            System.out.println("Creating VBox for document: " + document.getTitle());
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/uet/librarymanagementsystem/fxml/student/document_vbox.fxml"));
            VBox documentBox = loader.load();

            // Gán dữ liệu cho controller của VBox
            DocumentVboxController controller = loader.getController();
            controller.setDocument(document);

            return documentBox;
        } catch (Exception e) {
            System.err.println("Failed to create VBox for document: " + document.getTitle());
            e.printStackTrace();
            throw new RuntimeException("Failed to create VBox: " + e.getMessage());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            // Load documents và recommended books khi khởi tạo controller
            loadRecentDocs();
            loadRecommendBooks("HISTORY");
        } catch (Exception e) {
            System.err.println("Error occurred during initialization: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
