package uet.librarymanagementsystem.controllers.info_document;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.stage.Stage;
import uet.librarymanagementsystem.entity.Page;
import uet.librarymanagementsystem.entity.documents.Document;
import uet.librarymanagementsystem.entity.documents.ImagesOfLibrary;
import uet.librarymanagementsystem.entity.documents.MaterialType;
import uet.librarymanagementsystem.entity.documents.materials.Book;
import uet.librarymanagementsystem.entity.transactions.Transaction;
import uet.librarymanagementsystem.services.documentServices.BookLookupService;
import uet.librarymanagementsystem.services.shareDataServers.ShareDataService;
import uet.librarymanagementsystem.services.transactionServices.SearchTransactionService;
import uet.librarymanagementsystem.util.WindowUtil;

import java.awt.*;
import java.net.URI;
import java.net.URL;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;

public class GetInfoDocumentController implements Initializable {

    private final int starRating = 5;
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
    private TableColumn<Transaction, String> reviewColumn;

    @FXML
    private TableColumn<Transaction, String> starRatingColumn;

    @FXML
    private TableView<Transaction> ratingTableView;

    private ObservableList<Transaction> ratingList;

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

    private void updateRatingBar(int[] ratingCounts, int totalRatings) {
        progressBar5.setProgress((double) ratingCounts[5] / totalRatings);  // 5 sao
        progressBar4.setProgress((double) ratingCounts[4] / totalRatings);  // 4 sao
        progressBar3.setProgress((double) ratingCounts[3] / totalRatings);  // 3 sao
        progressBar2.setProgress((double) ratingCounts[2] / totalRatings);  // 2 sao
        progressBar1.setProgress((double) ratingCounts[1] / totalRatings);  // 1 sao
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
                        getClass().getResourceAsStream(ImagesOfLibrary.BOOK.getPath())));
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
                    getClass().getResourceAsStream(ImagesOfLibrary.BOOK.getPath())));
            imageInformation.setImage(image);
        } else if (Objects.equals(document.getMaterial(), MaterialType.JOURNAL.name())) {
            Image image = new Image(Objects.requireNonNull(
                    getClass().getResourceAsStream(ImagesOfLibrary.JOURNAL.getPath())));
            imageInformation.setImage(image);
        } else if (Objects.equals(document.getMaterial(), MaterialType.NEWSPAPER.name())) {
            Image image = new Image(Objects.requireNonNull(
                    getClass().getResourceAsStream(ImagesOfLibrary.NEWSPAPER.getPath())));
            imageInformation.setImage(image);
        } else if (Objects.equals(document.getMaterial(), MaterialType.THESIS.name())) {
            Image image = new Image(Objects.requireNonNull(
                    getClass().getResourceAsStream(ImagesOfLibrary.THESIS.getPath())));
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

        Document documentInfo = ShareDataService.getDocumentShare();

        SearchTransactionService searchTransactionService = new SearchTransactionService();

        int[] ratingCounts = new int[6];

        try {
            ratingCounts = searchTransactionService.ratingOfIdDocument(documentInfo.getId());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        double averageRating = 0;
        int totalRatings = 0;
        for (int i = 1; i <= starRating; i++) {
            totalRatings += ratingCounts[i];
            averageRating += ratingCounts[i] * i;
        }
        if (totalRatings != 0) {
            averageRating /= totalRatings;
        }

        totalRatingsLabel.setText(String.valueOf(totalRatings));
        starLabel.setText(String.valueOf(averageRating));
        updateRatingBar(ratingCounts, totalRatings);
        updateStars(averageRating);  // Cập nhật các sao

        dateRatingColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getReviewDate()));
        nameUserRatingColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getStudent().getName()));
        dateRatingColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getReviewDate()));
        starRatingColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getRating()));
        reviewColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getReview()));

        ratingList = FXCollections.observableArrayList();
        try {
            ratingList = searchTransactionService.searchTransactionByIdDocumentAndReviewed(documentInfo.getId());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        ratingTableView.setItems(ratingList);

        if (documentInfo instanceof Book book) {
            if (book.getIsbn() != null) {
                setFieldLabelByISBN(book);
            } else {
                setFieldLabelNotByISBN(documentInfo);
            }
        } else {
            setFieldLabelNotByISBN(documentInfo);
        }
    }
}

