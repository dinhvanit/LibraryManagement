package uet.librarymanagementsystem.controllers.student;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import uet.librarymanagementsystem.entity.documents.Document;
import uet.librarymanagementsystem.entity.documents.MaterialType;
import uet.librarymanagementsystem.entity.documents.materials.Book;
import uet.librarymanagementsystem.entity.documents.materials.Journal;
import uet.librarymanagementsystem.entity.documents.materials.Newspaper;
import uet.librarymanagementsystem.entity.documents.materials.Thesis;
import uet.librarymanagementsystem.services.documentServices.SearchDocumentService;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class SearchAndBorrowDocumentController implements Initializable {

    private final int borrowingPeriod = 6;
    private SearchDocumentService searchDocumentService;
    private ObservableList<Document> documentsListSearchResult;
    private ObservableList<Document> documentsListToBorrow;
    //    private String idDocument = "";
    private String titleDocument = "";
    private String authorDocument = "";
    private String materialDocument = "";
    private String categoryDocument = "";

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
    private TableView<Document> documentsToBorrowTableView;

    @FXML
    private TableColumn<Document, String> authorColumnDocumentsToBorrow;

    @FXML
    private TableColumn<Document, String> categoryColumnDocumentsToBorrow;

    @FXML
    private TableColumn<Document, String> idColumnDocumentsToBorrow;

    @FXML
    private TableColumn<Document, String> materialColumnDocumentsToBorrow;

    @FXML
    private TableColumn<Document, String> titleColumnDocumentsToBorrow;

    @FXML
    private ChoiceBox<String> choiceCategoryDocument;

    @FXML
    private ChoiceBox<MaterialType> choiceMaterialDocument;

    @FXML
    private TextField fieldAuthorDocument;

    @FXML
    private TextField fieldTitleDocument;

    @FXML
    private Label dueDateLabel;

    @FXML
    private void addDocumentToBorrowButtonOnClick(MouseEvent event) throws SQLException {
        performAdd();
    }

    private void performAdd() {
        dueDateLabel.setText(setDueDate());

        Document selectedDocument = searchResultsTableView.getSelectionModel().getSelectedItem();
        if (selectedDocument != null) {
            documentsListToBorrow.add(selectedDocument);
            documentsToBorrowTableView.setItems(documentsListToBorrow);
        }
    }

    private String setDueDate() {
        return LocalDate.now().plusMonths(borrowingPeriod).format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    @FXML
    void borrowAllDocumentsButtonOnClick(MouseEvent event) {

    }

    @FXML
    void borrowDocumentButtonOnClick(MouseEvent event) {

    }

    @FXML
    void deleteDocumentButtonOnClick(MouseEvent event) {
        performDelete();
    }

    private void performDelete() {
        Document selectedDocument = documentsToBorrowTableView.getSelectionModel().getSelectedItem();
        if (selectedDocument != null) {
            documentsListToBorrow.remove(selectedDocument);
            documentsToBorrowTableView.setItems(documentsListToBorrow);
        }
    }

    @FXML
    void findDocumentButtonOnClick(MouseEvent event) throws SQLException {
        performSearch();
    }

    private void performSearch() throws SQLException {
        documentsListSearchResult = searchDocumentService.search(titleDocument, authorDocument, materialDocument, categoryDocument);
        searchResultsTableView.setItems(documentsListSearchResult);
    }

    private void setupTextFieldsListeners() {
        fieldTitleDocument.textProperty().addListener((observable, oldValue, newValue) -> {
            titleDocument = newValue;
            try {
                performSearch();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        fieldAuthorDocument.textProperty().addListener((observable, oldValue, newValue) -> {
            authorDocument = newValue;
            try {
                performSearch();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    private void setupChoiceBoxes() {
        // Thêm lựa chọn trống cho Material
        ObservableList<MaterialType> materialTypesWithEmptyOption = FXCollections.observableArrayList();
        materialTypesWithEmptyOption.add(null); // Thêm tùy chọn trống
        materialTypesWithEmptyOption.addAll(MaterialType.values());
        choiceMaterialDocument.setItems(materialTypesWithEmptyOption);

        choiceMaterialDocument.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            materialDocument = (newValue == null) ? "" : newValue.name();

            ObservableList<String> categories = FXCollections.observableArrayList("");
            if (newValue != null) {
                switch (newValue) {
                    case BOOK -> categories.addAll(
                            Arrays.stream(Book.BookCategory.values()).map(Enum::name).collect(Collectors.toList()));
                    case THESIS -> categories.addAll(
                            Arrays.stream(Thesis.ThesisCategory.values()).map(Enum::name).collect(Collectors.toList()));
                    case NEWSPAPER -> categories.addAll(
                            Arrays.stream(Newspaper.NewspaperCategory.values()).map(Enum::name).collect(Collectors.toList()));
                    case JOURNAL -> categories.addAll(
                            Arrays.stream(Journal.JournalCategory.values()).map(Enum::name).collect(Collectors.toList()));
                }
            }
            choiceCategoryDocument.setItems(categories);
            categoryDocument = "";
            choiceCategoryDocument.getSelectionModel().clearSelection();
            try {
                performSearch();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        // Thêm lựa chọn trống cho Category
        choiceCategoryDocument.getItems().add(null); // Thêm lựa chọn trống cho Category
        choiceCategoryDocument.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            categoryDocument = (newValue == null || newValue.isEmpty()) ? "" : newValue;
            try {
                performSearch();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        searchDocumentService = new SearchDocumentService();

        idColumnSearchResults.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getId()));
        titleColumnSearchResults.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTitle()));
        authorColumnSearchResults.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAuthor()));
        materialColumnSearchResults.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMaterial()));
        categoryColumnSearchResults.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCategory()));

        documentsListSearchResult = FXCollections.observableArrayList();
        searchResultsTableView.setItems(documentsListSearchResult);

        idColumnDocumentsToBorrow.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getId()));
        titleColumnDocumentsToBorrow.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTitle()));
        authorColumnDocumentsToBorrow.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAuthor()));
        materialColumnDocumentsToBorrow.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMaterial()));
        categoryColumnDocumentsToBorrow.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCategory()));

        documentsListToBorrow = FXCollections.observableArrayList();
        documentsToBorrowTableView.setItems(documentsListToBorrow);

        setupTextFieldsListeners();
        setupChoiceBoxes();
    }
}
