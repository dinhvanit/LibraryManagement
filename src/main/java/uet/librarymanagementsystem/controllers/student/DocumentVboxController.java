package uet.librarymanagementsystem.controllers.student;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.concurrent.Task;
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

        // Đặt tiêu đề và tác giả vào các label
        titleLabel.setText(document.getTitle());
        authorLabel.setText(document.getAuthor());

        // Tải ảnh thu nhỏ bất đồng bộ
        loadThumbnailImage();
    }

    private void loadThumbnailImage() {
        Task<Image> imageTask = new Task<>() {
            @Override
            protected Image call() throws Exception {
                if (document instanceof Book book) {
                    // Nếu là Book, tìm ảnh thu nhỏ từ ISBN
                    if (book.getIsbn() != null && !book.getIsbn().isEmpty()) {
                        BookLookupService lookupService = new BookLookupService(book.getIsbn());
                        String thumbnailUrl = lookupService.getThumbnailUrl();

                        if (!thumbnailUrl.equals("N/A")) {
                            return new Image(thumbnailUrl); // Trả về ảnh từ URL
                        }
                    }
                    // Nếu không tìm được ảnh thu nhỏ, sử dụng ảnh mặc định cho sách
                    return new Image(Objects.requireNonNull(
                            getClass().getResourceAsStream(ImagesOfLibrary.BOOK.getPath())));
                } else {
                    // Xử lý cho các loại tài liệu khác
                    if (document instanceof Journal) {
                        return new Image(Objects.requireNonNull(
                                getClass().getResourceAsStream(ImagesOfLibrary.JOURNAL.getPath())));
                    } else if (document instanceof Newspaper) {
                        return new Image(Objects.requireNonNull(
                                getClass().getResourceAsStream(ImagesOfLibrary.NEWSPAPER.getPath())));
                    } else if (document instanceof Thesis) {
                        return new Image(Objects.requireNonNull(
                                getClass().getResourceAsStream(ImagesOfLibrary.THESIS.getPath())));
                    } else {
                        return new Image(Objects.requireNonNull(
                                getClass().getResourceAsStream(ImagesOfLibrary.BOOK.getPath())));
                    }
                }
            }
        };

        // Khi tải ảnh hoàn tất, cập nhật lên giao diện người dùng
        imageTask.setOnSucceeded(event -> {
            imageView.setImage(imageTask.getValue());
        });

        // Bắt đầu tải ảnh trong một luồng nền
        new Thread(imageTask).start();
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
