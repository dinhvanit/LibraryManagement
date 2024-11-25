package uet.librarymanagementsystem.controllers.student;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.concurrent.Task;
import javafx.stage.Stage;
import uet.librarymanagementsystem.entity.Page;
import uet.librarymanagementsystem.entity.documents.Document;
import uet.librarymanagementsystem.entity.documents.ImagesOfLibrary;
import uet.librarymanagementsystem.entity.documents.materials.Book;
import uet.librarymanagementsystem.entity.documents.materials.Journal;
import uet.librarymanagementsystem.entity.documents.materials.Newspaper;
import uet.librarymanagementsystem.entity.documents.materials.Thesis;
import uet.librarymanagementsystem.services.documentServices.BookLookupService;
import uet.librarymanagementsystem.services.shareDataServers.ShareDataService;
import uet.librarymanagementsystem.util.WindowUtil;

import java.util.Objects;

/**
 * Controller class responsible for displaying a document's thumbnail image,
 * title, and author in the student interface.
 */
public class DocumentVboxController {

    @FXML
    private ImageView imageView;

    @FXML
    private Label titleLabel;

    @FXML
    private Label authorLabel;

    private Document document; // Stores the associated document with the VBox

    /**
     * Sets the document and updates the UI to display the document's title,
     * author, and thumbnail image.
     *
     * @param document the document to be displayed
     */
    public void setDocument(Document document) {
        this.document = document;

        // Set the title and author labels
        titleLabel.setText(document.getTitle());
        authorLabel.setText(document.getAuthor());

        // Load the thumbnail image asynchronously
        loadThumbnailImage();
    }

    /**
     * Asynchronously loads the thumbnail image for the document. The image is
     * determined based on the document type (e.g., Book, Journal, Newspaper, Thesis).
     * If the document is a Book, it tries to fetch the thumbnail image using the
     * book's ISBN. If no thumbnail is found, a default image is used.
     */
    private void loadThumbnailImage() {
        Task<Image> imageTask = new Task<>() {
            @Override
            protected Image call() throws Exception {
                if (document instanceof Book book) {
                    // If it's a Book, fetch the thumbnail from ISBN
                    if (book.getIsbn() != null && !book.getIsbn().isEmpty()) {
                        BookLookupService lookupService = new BookLookupService(book.getIsbn());
                        String thumbnailUrl = lookupService.getThumbnailUrl();

                        if (!thumbnailUrl.equals("N/A")) {
                            return new Image(thumbnailUrl); // Return image from URL
                        }
                    } else {
                        return new Image(Objects.requireNonNull(
                                getClass().getResourceAsStream(ImagesOfLibrary.BOOK.getPath())));
                    }
                    // If no thumbnail is found, use the default book image
                    return new Image(Objects.requireNonNull(
                            getClass().getResourceAsStream(ImagesOfLibrary.BOOK.getPath())));
                } else {
                    // Handle other document types
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

        // When the image loading is completed, update the UI
        imageTask.setOnSucceeded(event -> {
            imageView.setImage(imageTask.getValue());
        });

        // Start the image loading in a background thread
        new Thread(imageTask).start();
    }

    /**
     * Handles the click event for showing additional document information.
     * If the document is not null, the document information is shared and a new
     * window displaying the document's details is opened.
     */
    @FXML
    private void handleClick() {
        if (document != null) {
            // Share the document data and show information window
            ShareDataService.setDocumentShare(document);
            Stage currentStage = (Stage) imageView.getScene().getWindow();
            WindowUtil.showSecondaryWindowWithShowInfo(Page.SHOW_INFO_DOCUMENT, "Information Document", currentStage, false, false);
        } else {
            System.out.println("show info unsuccessful");
        }
    }
}
