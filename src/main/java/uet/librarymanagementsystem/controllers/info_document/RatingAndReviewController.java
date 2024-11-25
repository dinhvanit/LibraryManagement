package uet.librarymanagementsystem.controllers.info_document;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import uet.librarymanagementsystem.DatabaseOperation.TransactionsTable;
import uet.librarymanagementsystem.services.shareDataServers.ShareDataService;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Objects;
import java.util.ResourceBundle;

public class RatingAndReviewController implements Initializable {

    private Image fullStar;
    private Image emptyStar;

    private String rating;

    @FXML
    private TextArea areaTextReview;

    @FXML
    private Label ratingLabel;

    @FXML
    private Label reviewLabel;

    @FXML
    private ImageView starRating1;

    @FXML
    private ImageView starRating2;

    @FXML
    private ImageView starRating3;

    @FXML
    private ImageView starRating4;

    @FXML
    private ImageView starRating5;

    @FXML
    void saveRatingAndReview(MouseEvent event) throws SQLException {
        if (rating == null) {
            ratingLabel.setVisible(true);
        } else if (areaTextReview.getText().isEmpty()) {
            reviewLabel.setVisible(true);
        } else {
            TransactionsTable.updateRatingReviewDateReview(
                    ShareDataService.getTransactionShare().getId(),
                    rating, areaTextReview.getText(), String.valueOf(LocalDate.now()));
            Stage stage = (Stage) ratingLabel.getScene().getWindow();
            stage.close();
        }
    }

    @FXML
    void starRatingClick1(MouseEvent event) {
        ratingLabel.setVisible(false);
        rating = "1";
        starRating1.setImage(fullStar);
        starRating2.setImage(emptyStar);
        starRating3.setImage(emptyStar);
        starRating4.setImage(emptyStar);
        starRating5.setImage(emptyStar);
    }

    @FXML
    void starRatingClick2(MouseEvent event) {
        ratingLabel.setVisible(false);
        rating = "2";
        starRating1.setImage(fullStar);
        starRating2.setImage(fullStar);
        starRating3.setImage(emptyStar);
        starRating4.setImage(emptyStar);
        starRating5.setImage(emptyStar);
    }

    @FXML
    void starRatingClick3(MouseEvent event) {
        ratingLabel.setVisible(false);
        rating = "3";
        starRating1.setImage(fullStar);
        starRating2.setImage(fullStar);
        starRating3.setImage(fullStar);
        starRating4.setImage(emptyStar);
        starRating5.setImage(emptyStar);
    }

    @FXML
    void starRatingClick4(MouseEvent event) {
        ratingLabel.setVisible(false);
        rating = "4";
        starRating1.setImage(fullStar);
        starRating2.setImage(fullStar);
        starRating3.setImage(fullStar);
        starRating4.setImage(fullStar);
        starRating5.setImage(emptyStar);
    }

    @FXML
    void starRatingClick5(MouseEvent event) {
        ratingLabel.setVisible(false);
        rating = "5";
        starRating1.setImage(fullStar);
        starRating2.setImage(fullStar);
        starRating3.setImage(fullStar);
        starRating4.setImage(fullStar);
        starRating5.setImage(fullStar);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        fullStar = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/uet/librarymanagementsystem/image/Star_full.png")));
        emptyStar = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/uet/librarymanagementsystem/image/Star_empty.png")));

    }
}
