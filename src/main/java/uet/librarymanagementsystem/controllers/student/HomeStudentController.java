package uet.librarymanagementsystem.controllers.student;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.collections.ObservableList;
import uet.librarymanagementsystem.entity.documents.Document;
import uet.librarymanagementsystem.entity.documents.materials.Book;
import uet.librarymanagementsystem.services.TaskService;
import uet.librarymanagementsystem.services.documentServices.Get6LatestDoc;
import uet.librarymanagementsystem.services.documentServices.BookLookupService;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class HomeStudentController implements Initializable {

    @FXML
    private FlowPane recentDocsFlowPane;

    @FXML
    private FlowPane recommendBooksFlowPane;

    private TaskService taskService = new TaskService();

    // Method to load recent documents asynchronously
    private void loadRecentDocs() {
        System.out.println("Loading recent documents...");

        taskService.runTask(() -> {
            try {
                // Fetch the latest document list from the database
                ObservableList<Document> latestTitles = Get6LatestDoc.getLatestTitles();

                // Loop through the documents and add them to the FlowPane immediately
                javafx.application.Platform.runLater(() -> {
                    for (Document document : latestTitles) {
                        // Add each document's VBox immediately to the FlowPane
                        VBox documentVBox = createDocumentVBox(document);
                        recentDocsFlowPane.getChildren().add(documentVBox);
                    }
                    System.out.println("Successfully added documents to recentDocsFlowPane.");
                });

            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Error occurred while fetching the latest documents.");
            }
        });
    }


    // Asynchronous method to load recommended books (you can implement a similar async process for recommendations)
    private void loadRecommendBooks(String category) {
        // Implement your own method to fetch and display recommended books
    }

    private VBox createDocumentVBox(Document document) {
        try {
            System.out.println("Create Vbox");
            // Load FXML VBox and assign the controller
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/uet/librarymanagementsystem/fxml/student/document_vbox.fxml"));
            VBox documentBox = loader.load();

            // Truyền dữ liệu vào controller
            DocumentVboxController controller = loader.getController();
            controller.setDocument(document);

            // Store reference to controller in VBox properties for easy access later
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
            loadRecentDocs(); // Load recent documents asynchronously
            loadRecommendBooks("HISTORY"); // Load recommended books for a specific category asynchronously
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error occurred while initializing HomeStudentController.");
        }
    }
}
