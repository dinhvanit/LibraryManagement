package uet.librarymanagementsystem.controllers.info_document;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import uet.librarymanagementsystem.controllers.LoginController;
import uet.librarymanagementsystem.services.documentServices.SearchDocumentService;
import uet.librarymanagementsystem.services.userServices.SearchStudentService;

import java.awt.*;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.net.URI;
import java.net.URL;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;

public class GetInfoDocumentController implements Initializable {

    private double averageRating;
    private int[] ratingCounts;
    private int totalRatings;

    private Image fullStar;
    private Image halfStar;
    private Image emptyStar;

    @FXML
    private Label authorLabel;

    @FXML
    private HBox categoryLabel;

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

    @FXML
    private Label previewLabel;

    @FXML
    private Label pulisherLabel;

    @FXML
    private Label titleLabel;

    @FXML
    private Label borrowDateLabel;

    @FXML
    private Label dueDateLabel;

    @FXML
    private Label returnDateLabel;

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
    void previewLinkClick(MouseEvent event) {
        String url = "https://google.com";
        openWebPage(url);
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
    }
}

