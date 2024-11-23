package uet.librarymanagementsystem.controllers.student;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import uet.librarymanagementsystem.entity.documents.Document;

public class DocumentVboxController {
    @FXML
    private ImageView imageView;

    @FXML
    private Label titleLabel;

    @FXML
    private Label authorLabel;

    private Document document; // Lưu trữ tài liệu liên kết với VBox

    public void setDocument(Document document) {
        this.document = document;

        // Gán dữ liệu vào các thành phần
        titleLabel.setText(document.getTitle());
        authorLabel.setText(document.getAuthor());
    }

    @FXML
    private void handleClick() {
        // Xử lý sự kiện bấm vào VBox
        System.out.println("Thông tin tài liệu:");
        System.out.println("ID: " + document.getId());
        System.out.println("Tiêu đề: " + document.getTitle());
        System.out.println("Tác giả: " + document.getAuthor());
        System.out.println("Thể loại: " + document.getCategory());
    }
}
