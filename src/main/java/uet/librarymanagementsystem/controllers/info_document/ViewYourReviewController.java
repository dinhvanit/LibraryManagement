package uet.librarymanagementsystem.controllers.info_document;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import uet.librarymanagementsystem.services.shareDataServers.ShareDataService;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * The {@code ViewYourReviewController} class handles the view and display of a user's review for a document.
 * It allows the user to see their rating, review text, and the date the review was given.
 * The rating is represented visually using star images.
 */
public class ViewYourReviewController implements Initializable {

    private Image fullStar;
    private Image emptyStar;

    @FXML
    private Label dateLabel;

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

    /**
     * Sets the rating image according to the provided rating count.
     *
     * @param count The rating count, which determines how many stars are filled.
     */
    void setRatingImage(int count) {
        if (count == 1) {
            rating1();
        } else if (count == 2) {
            rating2();
        } else if (count == 3) {
            rating3();
        } else if (count == 4) {
            rating4();
        } else if (count == 5) {
            rating5();
        }
    }

    /**
     * Updates the stars to represent a rating of 1.
     */
    void rating1() {
        starRating1.setImage(fullStar);
        starRating2.setImage(emptyStar);
        starRating3.setImage(emptyStar);
        starRating4.setImage(emptyStar);
        starRating5.setImage(emptyStar);
    }

    /**
     * Updates the stars to represent a rating of 2.
     */
    void rating2() {
        starRating1.setImage(fullStar);
        starRating2.setImage(fullStar);
        starRating3.setImage(emptyStar);
        starRating4.setImage(emptyStar);
        starRating5.setImage(emptyStar);
    }

    /**
     * Updates the stars to represent a rating of 3.
     */
    void rating3() {
        starRating1.setImage(fullStar);
        starRating2.setImage(fullStar);
        starRating3.setImage(fullStar);
        starRating4.setImage(emptyStar);
        starRating5.setImage(emptyStar);
    }

    /**
     * Updates the stars to represent a rating of 4.
     */
    void rating4() {
        starRating1.setImage(fullStar);
        starRating2.setImage(fullStar);
        starRating3.setImage(fullStar);
        starRating4.setImage(fullStar);
        starRating5.setImage(emptyStar);
    }

    /**
     * Updates the stars to represent a rating of 5.
     */
    void rating5() {
        starRating1.setImage(fullStar);
        starRating2.setImage(fullStar);
        starRating3.setImage(fullStar);
        starRating4.setImage(fullStar);
        starRating5.setImage(fullStar);
    }

    /**
     * Initializes the controller by setting the star images and the review details.
     * It also sets the review date and review text from shared data.
     *
     * @param url The location used to resolve relative paths for the root object, or {@code null} if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or {@code null} if the resources are not available.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        fullStar = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/uet/librarymanagementsystem/image/Star_full.png")));
        emptyStar = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/uet/librarymanagementsystem/image/Star_empty.png")));
        dateLabel.setText(ShareDataService.getTransactionShare().getReviewDate());
        setRatingImage(Integer.parseInt(ShareDataService.getTransactionShare().getRating()));
        reviewLabel.setText(ShareDataService.getTransactionShare().getReview());
    }
}
