package uet.librarymanagementsystem.controllers.info_document;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import uet.librarymanagementsystem.controllers.LoginController;
import uet.librarymanagementsystem.controllers.student.BorrowedDocumentsController;
import uet.librarymanagementsystem.controllers.student.SearchAndBorrowDocumentController;
import uet.librarymanagementsystem.controllers.student.TransactionDocumentsController;
import uet.librarymanagementsystem.entity.Page;
import uet.librarymanagementsystem.entity.documents.Document;
import uet.librarymanagementsystem.entity.documents.ImagesOfMaterial;
import uet.librarymanagementsystem.entity.documents.MaterialType;
import uet.librarymanagementsystem.entity.documents.materials.Book;
import uet.librarymanagementsystem.entity.transactions.Transaction;
import uet.librarymanagementsystem.services.documentServices.BookLookupService;
import uet.librarymanagementsystem.services.documentServices.SearchDocumentService;
import uet.librarymanagementsystem.services.shareData.ShareData;
import uet.librarymanagementsystem.services.userServices.SearchStudentService;
import uet.librarymanagementsystem.util.WindowUtil;

import javax.print.Doc;
import java.awt.*;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.net.URI;
import java.net.URL;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;

public class GetInfoDocumentController implements Initializable {

    private Page pageOwner;
    private double averageRating;
    private int[] ratingCounts;
    private int totalRatings;
    private Document documentInfo;

    private Image fullStar;
    private Image halfStar;
    private Image emptyStar;

    @FXML
    private Label authorLabel;

    @FXML
    private Label categoryLabel;

    @FXML
    private Label descriptionLabel;

    @FXML
    private Label idLabel;

    @FXML
    private ImageView imageInformation;

    @FXML
    private Label isbnLabel;

    @FXML
    private Label languageLabel;

    @FXML
    private Label materialLabel;

    @FXML
    private Hyperlink previewHyperlink;

    private String linkURL;

    @FXML
    private Label previewLabel;

    @FXML
    private Label publisherLabel;

    @FXML
    private Label titleLabel;

    @FXML
    private ImageView starImage1;

    @FXML
    private ImageView starImage2;

    @FXML
    private ImageView starImage3;

    @FXML
    private ImageView starImage4;

    @FXML
    private ImageView starImage5;

    @FXML
    private Label starLabel;

    @FXML
    private ProgressBar progressBar1;

    @FXML
    private ProgressBar progressBar2;

    @FXML
    private ProgressBar progressBar3;

    @FXML
    private ProgressBar progressBar4;

    @FXML
    private ProgressBar progressBar5;

    @FXML
    private Label totalRatingsLabel;

    @FXML
    private TableColumn<Transaction, String> dateRatingColumn;

    @FXML
    private TableColumn<Transaction, String> nameUserRatingColumn;

    @FXML
    private TableView<Transaction> ratingTableView;

    @FXML
    private TableColumn<Transaction, String> reviewColumn;

    @FXML
    private TableColumn<Transaction, String> starRatingColumn;

    @FXML
    private Button writeRatingAndReviewButton;

    @FXML
    private Button viewYourReviewButton;

    @FXML
    void previewLinkClick(MouseEvent event) {
        String url = linkURL;
        openWebPage(url);
    }

    public void setButtonWriteRatingAndReviewVisibility(boolean isVisible) {
        writeRatingAndReviewButton.setVisible(isVisible);
    }

    public void setButtonViewYourReviewVisibility(Boolean isVisible){
        viewYourReviewButton.setVisible(isVisible);
    }

    public void setPageOwner(Page page) {
        System.out.println(page.name());
        pageOwner = page;
    }
    private void updateStars(double rating) {
        updateStar(starImage1, 1, rating);
        updateStar(starImage2, 2, rating);
        updateStar(starImage3, 3, rating);
        updateStar(starImage4, 4, rating);
        updateStar(starImage5, 5, rating);
    }

    private void updateStar(ImageView star, int count, double rating) {
        if (rating >= count) {
            star.setImage(fullStar);
        } else if (rating - (int) rating > 0) {
            star.setImage(halfStar);
        } else {
            star.setImage(emptyStar);
        }
    }

    private void updateRatingBar() {
        progressBar5.setProgress((double) ratingCounts[0] / totalRatings);  // 5 sao
        progressBar4.setProgress((double) ratingCounts[1] / totalRatings);  // 4 sao
        progressBar3.setProgress((double) ratingCounts[2] / totalRatings);  // 3 sao
        progressBar2.setProgress((double) ratingCounts[3] / totalRatings);  // 2 sao
        progressBar1.setProgress((double) ratingCounts[4] / totalRatings);  // 1 sao
    }

    private void setFieldLabelByISBN(Book book) {
        BookLookupService bookLookupService = new BookLookupService(book.getIsbn());
        if (bookLookupService.checkBookInfoByISBN()) {
            idLabel.setText(book.getId());
            isbnLabel.setText(book.getIsbn());
            titleLabel.setText(bookLookupService.getTitleBook());
            authorLabel.setText(bookLookupService.getAllAuthors());
            materialLabel.setText("BOOK");
            categoryLabel.setText(bookLookupService.getAllCategories());
            publisherLabel.setText(bookLookupService.getPublisher());
            languageLabel.setText(bookLookupService.getLanguage());
            if (Objects.equals(bookLookupService.getPreviewLink(), "N/A")) {
                previewLabel.setText(bookLookupService.getPreviewLink());
                previewLabel.setVisible(true);
                previewHyperlink.setVisible(false);
            } else {
                linkURL = bookLookupService.getPreviewLink();
                previewLabel.setVisible(false);
                previewHyperlink.setVisible(true);
            }
            descriptionLabel.setText(bookLookupService.getDescription());

            if (!Objects.equals(bookLookupService.getThumbnailUrl(), "N/A")) {
                System.out.println(bookLookupService.getThumbnailUrl());
                Image image = new Image(bookLookupService.getThumbnailUrl());
                imageInformation.setImage(image);
            } else {
                Image image = new Image(Objects.requireNonNull(
                        getClass().getResourceAsStream(ImagesOfMaterial.BOOK.getPath())));
                imageInformation.setImage(image);
            }
        } else {
            setFieldLabelNotByISBN(book);
        }
    }

    private void setFieldLabelNotByISBN(Document document) {
        idLabel.setText(document.getId());
        titleLabel.setText(document.getTitle());
        authorLabel.setText(document.getAuthor());
        materialLabel.setText(document.getMaterial());
        categoryLabel.setText(document.getCategory());
        previewLabel.setVisible(true);
        previewHyperlink.setVisible(false);
        if (Objects.equals(document.getMaterial(), MaterialType.BOOK.name())) {
            Image image = new Image(Objects.requireNonNull(
                    getClass().getResourceAsStream(ImagesOfMaterial.BOOK.getPath())));
            imageInformation.setImage(image);
        } else if (Objects.equals(document.getMaterial(), MaterialType.JOURNAL.name())) {
            Image image = new Image(Objects.requireNonNull(
                    getClass().getResourceAsStream(ImagesOfMaterial.JOURNAL.getPath())));
            imageInformation.setImage(image);
        } else if (Objects.equals(document.getMaterial(), MaterialType.NEWSPAPER.name())) {
            Image image = new Image(Objects.requireNonNull(
                    getClass().getResourceAsStream(ImagesOfMaterial.NEWSPAPER.getPath())));
            imageInformation.setImage(image);
        } else if (Objects.equals(document.getMaterial(), MaterialType.THESIS.name())) {
            Image image = new Image(Objects.requireNonNull(
                    getClass().getResourceAsStream(ImagesOfMaterial.THESIS.getPath())));
            imageInformation.setImage(image);
        }
    }

    private void openWebPage(String url) {
        try {
            // Sử dụng Desktop API để mở trình duyệt
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().browse(new URI(url));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void writeRatingAndReviewClick(MouseEvent event) {
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        WindowUtil.showSecondaryWindow(Page.SHOW_WRITE_RATING_AND_REVIEW, "Write rating and review", currentStage);
    }

    @FXML
    void viewYourReviewClick(MouseEvent event) {
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        WindowUtil.showSecondaryWindow(Page.SHOW_VIEW_YOUR_REVIEW, "View your review", currentStage);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        fullStar = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/uet/librarymanagementsystem/image/Star_full.png")));
        halfStar = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/uet/librarymanagementsystem/image/Star_half.png")));
        emptyStar = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/uet/librarymanagementsystem/image/Star_empty.png")));
        averageRating = 4.7;  // Điểm xếp hạng trung bình
        ratingCounts = new int[]{50, 30, 10, 5, 3};
        totalRatings = 98;
        totalRatingsLabel.setText(String.valueOf(totalRatings));
        starLabel.setText(String.valueOf(averageRating));
        updateRatingBar();
        updateStars(averageRating);  // Cập nhật các sao

        documentInfo = ShareData.getDocumentShare();
        //setDocumentInfo();
        System.out.println("T ne" + pageOwner);

        if (documentInfo instanceof Book book) {
            System.out.println("BOOOOOOOOOOOK");
            if (book.getIsbn() != null) {
                setFieldLabelByISBN(book);
            } else {
                setFieldLabelNotByISBN(documentInfo);
            }
        } else {
            System.out.println("111111");
            setFieldLabelNotByISBN(documentInfo);
        }
    }
}

