package uet.librarymanagementsystem.controllers.admin;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.ChoiceBox;
import uet.librarymanagementsystem.entity.documents.Document;

public class SearchAndRemoveDocumentController {
    @FXML
    private TableColumn<Document, String> authorColumnSearchResults;

    @FXML
    private TableColumn<Document, String> categoryColumnSearchResults;

    @FXML
    private ChoiceBox<Document> choiceCategoryDocument;

    @FXML
    private ChoiceBox<Document> choiceMaterialDocument;

    @FXML
    private TextField fieldAuthorDocument;

    @FXML
    private TextField fieldTitleDocument;

    @FXML
    private TableColumn<Document, String> idColumnSearchResults;

    @FXML
    private TableColumn<Document, String> materialColumnSearchResults;

    @FXML
    private TableView<Document> searchResultsTableView;

    @FXML
    private TableColumn<Document, String> titleColumnSearchResults;

    @FXML
    void deleteAllDocumentButtonOnClick(MouseEvent event) {

    }

    @FXML
    void deleteDocumentButtonOnClick(MouseEvent event) {

    }

    @FXML
    void removeDocumentButtonOnClick(MouseEvent event) {

    }

    @FXML
    void searchDocumentButtonOnClick(MouseEvent event) {

    }

}
