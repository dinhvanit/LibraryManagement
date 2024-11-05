package uet.librarymanagementsystem.controllers.admin;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import uet.librarymanagementsystem.DatabaseOperation.DatabaseManager;
import uet.librarymanagementsystem.entity.documents.Document;
import uet.librarymanagementsystem.entity.documents.MaterialType;
import uet.librarymanagementsystem.entity.documents.materials.Book;
import uet.librarymanagementsystem.entity.documents.materials.Journal;
import uet.librarymanagementsystem.entity.documents.materials.Newspaper;
import uet.librarymanagementsystem.entity.documents.materials.Thesis;
import uet.librarymanagementsystem.services.documentServices.BorrowDocument;
import uet.librarymanagementsystem.services.documentServices.SearchDocument;

import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class AddDocumentController {

    @FXML
    private TableColumn<?, ?> authorColumnSearchResults;

    @FXML
    private TableColumn<?, ?> categoryColumnSearchResults;

    @FXML
    private ChoiceBox<?> choiceCategoryAdmin;

    @FXML
    private ChoiceBox<?> choiceMaterialAdmin;

    @FXML
    private TextField fieldAuthorAdmin;

    @FXML
    private TextField fieldIDAdmin;

    @FXML
    private TextField fieldTitleAdmin;

    @FXML
    private TableColumn<?, ?> idColumnSearchResults;

    @FXML
    private TableColumn<?, ?> materialColumnSearchResults;

    @FXML
    private TableView<?> searchDocumentTableView;

    @FXML
    private TableColumn<?, ?> titleColumnSearchResults;

    @FXML
    void addDocumentButtonOnClick(MouseEvent event) {

    }

    @FXML
    void removeDocumentButtonOnClick(MouseEvent event) {

    }

    @FXML
    void saveAllDocumentButtonOnClick(MouseEvent event) {

    }

    @FXML
    void searchDocumentButtonOnClick(MouseEvent event) {

    }



}
