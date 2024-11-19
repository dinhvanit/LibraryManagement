package uet.librarymanagementsystem.controllers.info_document;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import uet.librarymanagementsystem.controllers.student.SearchAndBorrowDocumentController;
import uet.librarymanagementsystem.entity.documents.Document;
import uet.librarymanagementsystem.entity.documents.ImagesOfMaterial;
import uet.librarymanagementsystem.entity.documents.materials.Book;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class RatingAndReviewController implements Initializable {

    private Image fullStar;
    private Image halfStar;
    private Image emptyStar;

    @FXML
    private TextArea areaTextReview;

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
    void saveRatingAndReview(MouseEvent event) {

    }

    @FXML
    void starRatingClick1(MouseEvent event) {
        starRating1.setImage(fullStar);
        starRating2.setImage(emptyStar);
        starRating3.setImage(emptyStar);
        starRating4.setImage(emptyStar);
        starRating5.setImage(emptyStar);
    }

    @FXML
    void starRatingClick2(MouseEvent event) {
        starRating1.setImage(fullStar);
        starRating2.setImage(fullStar);
        starRating3.setImage(emptyStar);
        starRating4.setImage(emptyStar);
        starRating5.setImage(emptyStar);
    }

    @FXML
    void starRatingClick3(MouseEvent event) {
        starRating1.setImage(fullStar);
        starRating2.setImage(fullStar);
        starRating3.setImage(fullStar);
        starRating4.setImage(emptyStar);
        starRating5.setImage(emptyStar);
    }

    @FXML
    void starRatingClick4(MouseEvent event) {
        starRating1.setImage(fullStar);
        starRating2.setImage(fullStar);
        starRating3.setImage(fullStar);
        starRating4.setImage(fullStar);
        starRating5.setImage(emptyStar);
    }

    @FXML
    void starRatingClick5(MouseEvent event) {
        starRating1.setImage(fullStar);
        starRating2.setImage(fullStar);
        starRating3.setImage(fullStar);
        starRating4.setImage(fullStar);
        starRating5.setImage(fullStar);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        fullStar = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/uet/librarymanagementsystem/image/Star_full.png")));
        halfStar = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/uet/librarymanagementsystem/image/Star_half.png")));
        emptyStar = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/uet/librarymanagementsystem/image/Star_empty.png")));

    }


}
