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

    void rating1() {
        starRating1.setImage(fullStar);
        starRating2.setImage(emptyStar);
        starRating3.setImage(emptyStar);
        starRating4.setImage(emptyStar);
        starRating5.setImage(emptyStar);
    }

    void rating2() {
        starRating1.setImage(fullStar);
        starRating2.setImage(fullStar);
        starRating3.setImage(emptyStar);
        starRating4.setImage(emptyStar);
        starRating5.setImage(emptyStar);
    }

    void rating3() {
        starRating1.setImage(fullStar);
        starRating2.setImage(fullStar);
        starRating3.setImage(fullStar);
        starRating4.setImage(emptyStar);
        starRating5.setImage(emptyStar);
    }

    void rating4() {
        starRating1.setImage(fullStar);
        starRating2.setImage(fullStar);
        starRating3.setImage(fullStar);
        starRating4.setImage(fullStar);
        starRating5.setImage(emptyStar);
    }

    void rating5() {
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
        dateLabel.setText(ShareDataService.getTransactionShare().getReviewDate());
        setRatingImage(Integer.parseInt(ShareDataService.getTransactionShare().getRating()));
        reviewLabel.setText(ShareDataService.getTransactionShare().getReview());
    }

}
