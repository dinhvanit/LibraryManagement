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

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class HomeStudentController implements Initializable {

    @FXML
    private FlowPane recentDocsFlowPane;

    @FXML
    private FlowPane recommendBooksFlowPane;


    private void loadRecentDocs() {
        System.out.println("load recentDocFlowPane");
        // Tạo một vài tài liệu mẫu để hiển thị
        Document book = new Book("1", "Java Programming", "John Doe", Book.BookCategory.HISTORY, "1234567890");
        Document journal = new Journal("2", "AI Research", "Jane Smith", Journal.JournalCategory.RESEARCH);
        Document newspaper = new Newspaper("3", "Daily News", "News Team", Newspaper.NewspaperCategory.WEATHER);
        Document thesis = new Thesis("4", "Thesis on AI", "Dr. Brown", Thesis.ThesisCategory.ENGINEERING);

        // Thêm vào FlowPane
        recentDocsFlowPane.getChildren().add(createDocumentVBox(book));
        recentDocsFlowPane.getChildren().add(createDocumentVBox(journal));
        recentDocsFlowPane.getChildren().add(createDocumentVBox(newspaper));
        recentDocsFlowPane.getChildren().add(createDocumentVBox(thesis));
        System.out.println("them vao recentFlowpane Ok");
    }

    private void loadRecommendBooks(String category) {
        System.out.println("load Recommmend Books");
        // Tạo các sách mẫu
        Document book1 = new Book("1", "Java Programming", "John Doe", Book.BookCategory.HISTORY, "1234567890");
        Document book2 = new Book("1", "Java Programming", "John Doe", Book.BookCategory.HISTORY, "1234567890");

        // Thêm vào FlowPane
        recommendBooksFlowPane.getChildren().add(createDocumentVBox(book1));
        recommendBooksFlowPane.getChildren().add(createDocumentVBox(book2));
        System.out.println("them vao pane oke");
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