package uet.librarymanagementsystem.controllers.student;

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

public class SearchAndBorrowDocumentController implements Initializable {

    private Connection conn;
    private SearchDocument searchDocumentService;
    private ObservableList<Document> documentListSearchResult;
    private String idDocument;
    private String titleDocument;
    private String authorDocument;
    private String materialDocument;
    private String categoryDocument;

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
    private ChoiceBox<MaterialType> choiceMaterialDocument;

    @FXML
    private TextField fieldAuthorDocument;

    @FXML
    private TextField fieldTitleDocument;

    @FXML
    void addDocumentToBorrowButtonOnClick(MouseEvent event) throws SQLException {
        // Implementation here
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
    void findDocumentButtonOnClick(MouseEvent event) throws SQLException {
        textFileTitleAuthor();
        choice();
        documentListSearchResult =  searchDocumentService.search(titleDocument, authorDocument, materialDocument, categoryDocument);
        searchResultsTableView.setItems(documentListSearchResult);
    }


    void textFileTitleAuthor() {
        fieldTitleDocument.textProperty().addListener((observable, oldValue, newValue) -> {
            titleDocument = fieldTitleDocument.getText();
        });
        fieldAuthorDocument.textProperty().addListener((observable, oldValue, newValue) -> {
            authorDocument = fieldAuthorDocument.getText();
        });
    }

    void choice() {
        choiceMaterialDocument.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                materialDocument = newValue.name();

                switch (newValue) {
                    case BOOK:
                        choiceCategoryDocument.setItems(FXCollections.observableArrayList(
                                Arrays.stream(Book.BookCategory.values())
                                        .map(Book.BookCategory::name)
                                        .collect(Collectors.toList())
                        ));
                        break;
                    case THESIS:
                        choiceCategoryDocument.setItems(FXCollections.observableArrayList(
                                Arrays.stream(Thesis.ThesisCategory.values())
                                        .map(Thesis.ThesisCategory::name)
                                        .collect(Collectors.toList())
                        ));
                        break;
                    case NEWSPAPER:
                        choiceCategoryDocument.setItems(FXCollections.observableArrayList(
                                Arrays.stream(Newspaper.NewspaperCategory.values())
                                        .map(Newspaper.NewspaperCategory::name)
                                        .collect(Collectors.toList())
                        ));
                        break;
                    case JOURNAL:
                        choiceCategoryDocument.setItems(FXCollections.observableArrayList(
                                Arrays.stream(Journal.JournalCategory.values())
                                        .map(Journal.JournalCategory::name)
                                        .collect(Collectors.toList())
                        ));
                        break;
                    default:
                        choiceCategoryDocument.setItems(FXCollections.observableArrayList());
                        break;
                }

                try {
                    System.out.println("kiem tra viec lua chon : " );
                    System.out.println("title : " + titleDocument);
                    System.out.println(("title : ") + authorDocument);
                    System.out.println("title : " + materialDocument);
                    System.out.println(("title : ") + categoryDocument);

                    documentListSearchResult = searchDocumentService.search(titleDocument, authorDocument, materialDocument, categoryDocument);
                    searchResultsTableView.setItems(documentListSearchResult);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });

        choiceCategoryDocument.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            categoryDocument = newValue;

            try {
                System.out.println("kiem tra viec lua chon dghfgsdhfgdhsgfh: " );
                System.out.println("title : " + titleDocument);
                System.out.println(("title : ") + authorDocument);
                System.out.println("title : " + materialDocument);
                System.out.println(("title : ") + categoryDocument);

                documentListSearchResult = searchDocumentService.search(titleDocument, authorDocument, materialDocument, categoryDocument);
                searchResultsTableView.setItems(documentListSearchResult);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        conn = DatabaseManager.connect();
        searchDocumentService = new SearchDocument(); // Khởi tạo searchDocumentService

        idColumnSearchResults.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getId()));
        titleColumnSearchResults.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTitle()));
        authorColumnSearchResults.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAuthor()));
        materialColumnSearchResults.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMaterial()));
        categoryColumnSearchResults.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCategory()));

        documentListSearchResult = FXCollections.observableArrayList();
        searchResultsTableView.setItems(documentListSearchResult);

        choiceMaterialDocument.setItems(FXCollections.observableArrayList(MaterialType.values()));
        materialDocument = ""; // Đặt mặc định là rỗng
        categoryDocument = "";
        choice();
    }
}