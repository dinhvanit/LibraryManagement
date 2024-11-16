package uet.librarymanagementsystem.controllers.admin;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import uet.librarymanagementsystem.entity.documents.Document;
import uet.librarymanagementsystem.entity.documents.MaterialType;


public class AddDocumentController {

    @FXML
    private TableColumn<Document, String> authorColumnSearchResults;

    @FXML
    private TableColumn<Document, String> categoryColumnSearchResults;

    @FXML
    private ChoiceBox<String> choiceCategoryAdmin;

    @FXML
    private ChoiceBox<MaterialType> choiceMaterialAdmin;

    @FXML
    private TextField fieldAuthorAdmin;

    @FXML
    private TextField fieldQuantityAdmin;

    @FXML
    private TextField fieldTitleAdmin;

    //api document
    @FXML
    private HBox isbnHbox;

    @FXML
    private TextField fieldISBN;

    @FXML
    private Label isbnValidLabel;

    @FXML
    private TableColumn<Document, String> idColumnSearchResults;

    @FXML
    private TableColumn<Document, String> materialColumnSearchResults;

    @FXML
    private TableView<Document> searchDocumentTableView;

    @FXML
    private TableColumn<Document, String> titleColumnSearchResults;

    @FXML
    void addDocumentButtonOnClick(MouseEvent event) {

    }

    @FXML
    void removeDocumentButtonOnClick(MouseEvent event) {

    }

    @FXML
    void removeAllDocumentButtonOnClick(MouseEvent event) {

    }

    @FXML
    void saveAllDocumentButtonOnClick(MouseEvent event) {

    }

    @FXML
    public void initialize() {
        // cập nhập choice box
        ObservableList<MaterialType> materialTypesWithEmptyOption = FXCollections.observableArrayList();
        materialTypesWithEmptyOption.addAll(MaterialType.values());
        choiceMaterialAdmin.setItems(materialTypesWithEmptyOption);

        // nhận sự thay đổi trong ChoiceBox Material
        choiceMaterialAdmin.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                // cập nhật các thể loại tương ứng với loại tài liệu
                ObservableList<String> categories = MaterialType.getCategoriesForMaterial(newValue);
                choiceCategoryAdmin.setItems(categories);

                // hiển thị trường ISBN nếu chọn "BOOK"
                isbnValidLabel.setVisible(newValue == MaterialType.BOOK);// hiển thị thông báo
                isbnHbox.setVisible(newValue == MaterialType.BOOK);  // hiển thị HBox ISBN khi chọn BOOK
            }
        });

        /*// Lắng nghe sự thay đổi trong lựa chọn thể loại
        choiceCategoryAdmin.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            // Xử lý khi chọn thể loại (nếu cần)
        });*/
    }
}
