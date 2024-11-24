package uet.librarymanagementsystem.controllers.student;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.collections.ObservableList;
import uet.librarymanagementsystem.entity.documents.Document;
import uet.librarymanagementsystem.services.TaskService;
import uet.librarymanagementsystem.services.documentServices.GetFavouriteCategory;
import uet.librarymanagementsystem.services.documentServices.GetBooksByCategory;
import uet.librarymanagementsystem.services.documentServices.Get6LatestDoc;
import uet.librarymanagementsystem.services.shareDataServers.ShareDataService;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class HomeStudentController implements Initializable {

    @FXML
    private FlowPane recentDocsFlowPane;

    @FXML
    private FlowPane recommendBooksFlowPane;

    private final TaskService taskService = new TaskService();
    private final GetBooksByCategory getBooksByFavoriteCategory = new GetBooksByCategory();

    // Tải tài liệu gần đây
    private void loadRecentDocs() {
        taskService.runTask(() -> {
            try {
                ObservableList<Document> latestTitles = Get6LatestDoc.getLatestTitles();
                updateFlowPane(recentDocsFlowPane, latestTitles);
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Error occurred while fetching the latest documents.");
            }
        });
    }

    // Tải sách gợi ý dựa trên ID sinh viên
    private void loadRecommendBooks(String idStudent) {
        if (idStudent == null || idStudent.isEmpty()) {
            System.out.println("User ID is not available for recommendations.");
            return;
        }

        taskService.runTask(() -> {
            try {
                String favoriteCategory = GetFavouriteCategory.getMostFrequentCategory(idStudent);
                ObservableList<Document> recommendedBooks = getBooksByFavoriteCategory.getBooks(favoriteCategory);
                updateFlowPane(recommendBooksFlowPane, recommendedBooks);
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Error occurred while fetching recommended books.");
            }
        });
    }

    // Cập nhật FlowPane với danh sách tài liệu
    private void updateFlowPane(FlowPane flowPane, ObservableList<Document> documents) {
        javafx.application.Platform.runLater(() -> {
            flowPane.getChildren().clear(); // Xóa nội dung cũ
            for (Document document : documents) {
                VBox documentVBox = createDocumentVBox(document);
                flowPane.getChildren().add(documentVBox);
            }
        });
    }

    // Tạo VBox cho mỗi tài liệu
    private VBox createDocumentVBox(Document document) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/uet/librarymanagementsystem/fxml/student/document_vbox.fxml"));
            VBox documentBox = loader.load();
            DocumentVboxController controller = loader.getController();
            controller.setDocument(document);
            documentBox.getProperties().put("controller", controller);
            return documentBox;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to create Document VBox for: " + document.getTitle(), e);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            String userId = ShareDataService.getIdStudentShare();
            loadRecentDocs();
            loadRecommendBooks(userId);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error occurred while initializing HomeStudentController.");
        }
    }
}
