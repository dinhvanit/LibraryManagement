package uet.librarymanagementsystem.controllers.student;

import javafx.concurrent.Task;
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

/**
 * Controller for the Home Student page in the library management system.
 * It handles displaying the most recent documents and recommended books based on a student's preferences.
 */
public class HomeStudentController implements Initializable {

    @FXML
    private FlowPane recentDocsFlowPane;

    @FXML
    private FlowPane recommendBooksFlowPane;

    private final TaskService taskService = TaskService.getInstance();
    private final GetBooksByCategory getBooksByFavoriteCategory = new GetBooksByCategory();

    /**
     * Loads the most recent documents into the FlowPane.
     * This method fetches the 6 latest documents from the database and updates the FlowPane UI.
     */
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

    /**
     * Loads the recommended books based on the student's ID.
     * This method fetches the student's most frequent category and recommends books in that category.
     *
     * @param idStudent the student's ID, used to fetch their favorite category
     */
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

    /**
     * Updates a FlowPane with a list of documents.
     * Clears the current content of the FlowPane and populates it with new document VBox elements.
     *
     * @param flowPane  the FlowPane to update
     * @param documents the list of documents to display
     */
    private void updateFlowPane(FlowPane flowPane, ObservableList<Document> documents) {
        javafx.application.Platform.runLater(() -> {
            flowPane.getChildren().clear(); // Clear the existing content
            for (Document document : documents) {
                VBox documentVBox = createDocumentVBox(document);
                flowPane.getChildren().add(documentVBox);
            }
        });
    }

    /**
     * Creates a VBox for each document, which will be displayed in the FlowPane.
     * This method loads a FXML template and initializes the controller for each document.
     *
     * @param document the document for which the VBox is created
     * @return a VBox containing the document's information
     */
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

    /**
     * Initializes the controller. This method is called after the FXML file is loaded.
     * It loads the recent documents and recommended books based on the student's ID.
     *
     * @param url            the location used to resolve relative paths for the root object, or null
     * @param resourceBundle the resources used to localize the root object, or null
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            String userId = ShareDataService.getIdStudentShare();
            loadRecentDocs();  // Load recent documents
            loadRecommendBooks(userId);  // Load recommended books based on the student's ID
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error occurred while initializing HomeStudentController.");
        }
    }
}
