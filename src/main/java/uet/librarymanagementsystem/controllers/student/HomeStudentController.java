package uet.librarymanagementsystem.controllers.student;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.collections.ObservableList;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import uet.librarymanagementsystem.entity.documents.Document;
import uet.librarymanagementsystem.entity.documents.ImagesOfLibrary;
import uet.librarymanagementsystem.entity.documents.materials.Book;
import uet.librarymanagementsystem.entity.documents.materials.Journal;
import uet.librarymanagementsystem.entity.documents.materials.Newspaper;
import uet.librarymanagementsystem.entity.documents.materials.Thesis;
import uet.librarymanagementsystem.services.TaskService;
import uet.librarymanagementsystem.services.documentServices.BookLookupService;
import uet.librarymanagementsystem.services.documentServices.Get6LatestDoc;

import java.net.URL;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;

public class HomeStudentController implements Initializable {

    @FXML
    private FlowPane recentDocsFlowPane;

    @FXML
    private FlowPane recommendBooksFlowPane;


    private void loadRecentDocs() {
        System.out.println("Loading recent documents...");
        try {
            // Lấy danh sách tài liệu mới nhất từ cơ sở dữ liệu
            ObservableList<Document> latestTitles = Get6LatestDoc.getLatestTitles();

            // Duyệt qua danh sách tài liệu và thêm mỗi tài liệu vào FlowPane
            for (Document document : latestTitles) {
                recentDocsFlowPane.getChildren().add(createDocumentVBox(document));
            }
            System.out.println("Successfully added documents to recentDocsFlowPane.");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error occurred while fetching the latest documents.");
        }
    }

    private void loadRecommendBooks(String category) {
    }

    private VBox createDocumentVBox(Document document) {
        try {
            System.out.println("Create Vbox");
            // Tải FXML VBox và gán controller
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/uet/librarymanagementsystem/fxml/student/document_vbox.fxml"));
            VBox documentBox = loader.load();

            // Truyền dữ liệu vào controller
            DocumentVboxController controller = loader.getController();
            controller.setDocument(document);

            return documentBox;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to create Document VBox for: " + document.getTitle(), e);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            loadRecentDocs();
            loadRecommendBooks("HISTORY");


        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error occurred while initializing HomeStudentController.");
        }
    }
}