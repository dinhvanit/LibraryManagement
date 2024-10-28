package uet.librarymanagementsystem.controllers;

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
import org.w3c.dom.ls.LSOutput;
import uet.librarymanagementsystem.entity.Document;

import java.net.URL;
import java.util.ResourceBundle;

public class SearchAndBorrowDocumentController implements Initializable {

    private ObservableList<Document> documentListSearchResult;

    @FXML
    private TableView<Document> searchResultsTableView;

    @FXML
    private TableColumn<Document, String> authorColumnSearchResults;

    @FXML
    private TableColumn<Document, String> categoryColumnSearchResults;

    @FXML
    private TableColumn<Document, String> idColumnSearchResults;

    @FXML
    private TableColumn<Document, String> materialColumnSearchResults;

    @FXML
    private TableColumn<Document, String> titleColumnSearchResults;

    @FXML
    private ChoiceBox<String> choiceCategoryDocument;

    @FXML
    private ChoiceBox<String> choiceMaterialDocument;

    @FXML
    private TextField fieldAuthorDocument;

    @FXML
    private TextField fieldTitleDocument;

    @FXML
    void addDocumentToBorrowButtonOnClick(MouseEvent event) {

    }

    @FXML
    void borrowAllDocumentsButtonOnClick(MouseEvent event) {

    }

    @FXML
    void borrowDocumentButtonOnClick(MouseEvent event) {

    }

    @FXML
    void deleteDocumentButtonOnClick(MouseEvent event) {

    }

    @FXML
    void findDocumentButtonOnClick(MouseEvent event) {

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        idColumnSearchResults.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getId()));
        titleColumnSearchResults.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTitle()));
        authorColumnSearchResults.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAuthor()));
        materialColumnSearchResults.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMaterial()));
        categoryColumnSearchResults.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCategory()));

        System.out.println("NE");

        documentListSearchResult = FXCollections.observableArrayList(
                new Document("D001", "Java Programming", "John Doe", "Book", "Programming", 10),
                new Document("D002", "Data Structures", "Jane Smith", "Book", "Computer Science", 5),
                new Document("D003", "Algorithms", "Robert Martin", "Journal", "Mathematics", 3)
        );
        System.out.println("INIT");
        searchResultsTableView.setItems(documentListSearchResult);
    }
}
