package uet.librarymanagementsystem.controllers.student;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.collections.ObservableList;
import uet.librarymanagementsystem.entity.documents.Document;
import uet.librarymanagementsystem.entity.documents.ImagesOfLibrary;
import uet.librarymanagementsystem.services.TaskService;
import uet.librarymanagementsystem.services.documentServices.BookLookupService;
//import uet.librarymanagementsystem.services.documentServices.Get6LatestDoc;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class HomeStudentController implements Initializable {

    @FXML
    private Label authorLabelNewDoc1;

    @FXML
    private Label authorLabelNewDoc2;

    @FXML
    private Label authorLabelNewDoc3;

    @FXML
    private Label authorLabelNewDoc4;

    @FXML
    private Label authorLabelNewDoc5;

    @FXML
    private Label authorLabelNewDoc6;

    @FXML
    private ImageView imageNewDoc1;

    @FXML
    private ImageView imageNewDoc2;

    @FXML
    private ImageView imageNewDoc3;

    @FXML
    private ImageView imageNewDoc4;

    @FXML
    private ImageView imageNewDoc5;

    @FXML
    private ImageView imageNewDoc6;

    @FXML
    private Label titleLabelNewDoc1;

    @FXML
    private Label titleLabelNewDoc2;

    @FXML
    private Label titleLabelNewDoc3;

    @FXML
    private Label titleLabelNewDoc4;

    @FXML
    private Label titleLabelNewDoc5;

    @FXML
    private Label titleLabelNewDoc6;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            // Lấy danh sách 6 tài liệu mới nhất từ database
//            ObservableList<Document> latestDocuments = Get6LatestDoc.getLatestTitles();
            updateDocumentInfo();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error occurred while initializing HomeStudentController.");
        }
    }

    private void updateDocumentInfo() {
        Label[] titleLabels = {
                titleLabelNewDoc1, titleLabelNewDoc2, titleLabelNewDoc3,
                titleLabelNewDoc4, titleLabelNewDoc5, titleLabelNewDoc6
        };

        Label[] authorLabels = {
                authorLabelNewDoc1, authorLabelNewDoc2, authorLabelNewDoc3,
                authorLabelNewDoc4, authorLabelNewDoc5, authorLabelNewDoc6
        };

        ImageView[] imageViews = {
                imageNewDoc1, imageNewDoc2, imageNewDoc3,
                imageNewDoc4, imageNewDoc5, imageNewDoc6
        };

        String[] isbns = { // Danh sách ISBN (cần điều chỉnh nếu lấy từ DB)
                "9781449358068", "9781551640273", "9781492078005",
                "9780590353427", "9780261103573", "9780135166307"
        };

        TaskService taskService = new TaskService();

        for (int i = 0; i < isbns.length; i++) {
            final int index = i; // Lưu chỉ mục để cập nhật đúng phần tử
            taskService.runTask(() -> {
                BookLookupService bookLookupService = new BookLookupService(isbns[index]);
                String thumbnailUrl = bookLookupService.getThumbnailUrl();
                Image image = new Image(thumbnailUrl, true); // true: tải không đồng bộ

                javafx.application.Platform.runLater(() -> {
                    // Cập nhật giao diện khi ảnh sẵn sàng
                    imageViews[index].setImage(image);
                    titleLabels[index].setText(bookLookupService.getTitleBook());
                    authorLabels[index].setText(bookLookupService.getTheFirstAuthor());
                });
                return null;
            });
        }
    }
}