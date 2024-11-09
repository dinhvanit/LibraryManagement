package uet.librarymanagementsystem.controllers.admin;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import uet.librarymanagementsystem.entity.documents.Document;

import javax.print.Doc;

public class AddDocumentController {

    @FXML
    private TableColumn<Document, String> authorColumnSearchResults;

    @FXML
    private TableColumn<Document, String> categoryColumnSearchResults;

    @FXML
    private ChoiceBox<Document> choiceCategoryAdmin;

    @FXML
    private ChoiceBox<Document> choiceMaterialAdmin;

    @FXML
    private TextField fieldAuthorAdmin;

    @FXML
    private TextField fieldQuantityAdmin;

    @FXML
    private TextField fieldTitleAdmin;

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




}
