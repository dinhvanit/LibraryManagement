package uet.librarymanagementsystem.controllers.student;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import uet.librarymanagementsystem.entity.documents.Document;
import uet.librarymanagementsystem.entity.documents.ImagesOfLibrary;
import uet.librarymanagementsystem.entity.documents.materials.Book;
import uet.librarymanagementsystem.entity.documents.materials.Journal;
import uet.librarymanagementsystem.entity.documents.materials.Newspaper;
import uet.librarymanagementsystem.entity.documents.materials.Thesis;
import uet.librarymanagementsystem.services.documentServices.BookLookupService;

import java.util.Objects;

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

        // Check if the document is a Book
        if (document instanceof Book book) {
            if (book.getIsbn() != null && !book.getIsbn().isEmpty()) {
                // Use BookLookupService to fetch book info by ISBN
                BookLookupService lookupService = new BookLookupService(book.getIsbn());
                String thumbnailUrl = lookupService.getThumbnailUrl();

                if (!thumbnailUrl.equals("N/A")) {
                    // If the ISBN returns a valid thumbnail URL, set the image
                    Image image = new Image(thumbnailUrl);
                    imageView.setImage(image);
                } else {
                    // If no image is found for ISBN, use default BOOK image
                    Image image = new Image(Objects.requireNonNull(
                            getClass().getResourceAsStream(ImagesOfLibrary.BOOK.getPath())));
                    imageView.setImage(image);
                }
            } else {
                // If no ISBN is available, set the default BOOK image
                Image image = new Image(Objects.requireNonNull(
                        getClass().getResourceAsStream(ImagesOfLibrary.BOOK.getPath())));
                imageView.setImage(image);
            }
        } else {
            if (document instanceof Journal) {
                Image image = new Image(Objects.requireNonNull(
                        getClass().getResourceAsStream(ImagesOfLibrary.BOOK.getPath())));
                imageView.setImage(image);
            } else if (document instanceof Newspaper) {
                Image image = new Image(Objects.requireNonNull(
                        getClass().getResourceAsStream(ImagesOfLibrary.NEWSPAPER.getPath())));
                imageView.setImage(image);
            } else if (document instanceof Thesis) {
                Image image = new Image(Objects.requireNonNull(
                        getClass().getResourceAsStream(ImagesOfLibrary.THESIS.getPath())));
                imageView.setImage(image);
            } else {
                Image image = new Image(Objects.requireNonNull(
                        getClass().getResourceAsStream(ImagesOfLibrary.BOOK.getPath())));
                imageView.setImage(image);
            }
        }
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
