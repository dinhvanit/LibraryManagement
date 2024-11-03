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
import uet.librarymanagementsystem.entity.documents.DocumentFactory;
import uet.librarymanagementsystem.entity.documents.MaterialType;
import uet.librarymanagementsystem.entity.documents.materials.Book;
import uet.librarymanagementsystem.entity.documents.materials.Journal;
import uet.librarymanagementsystem.entity.documents.materials.Newspaper;
import uet.librarymanagementsystem.entity.documents.materials.Thesis;

import java.net.URL;
import java.sql.*;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class SearchAndBorrowDocumentController implements Initializable {

    private ObservableList<Document> documentListSearchResult;
    private Connection conn;
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
        documentListSearchResult = getDocumentListSearchResult(titleDocument, authorDocument, materialDocument, categoryDocument);
        searchResultsTableView.setItems(documentListSearchResult);
    }

    public ObservableList<Document> getDocumentListSearchResult(String title, String author, String material, String category) throws SQLException {

        documentListSearchResult.clear();
        documentListSearchResult = FXCollections.observableArrayList();

        StringBuilder query = new StringBuilder("SELECT id, title, author, material, category FROM Document WHERE 1=1");

        if (title != null && !title.isEmpty()) {
            query.append(" AND title LIKE ?");
        }
        if (author != null && !author.isEmpty()) {
            query.append(" AND author LIKE ?");
        }
        if (material != null && !material.isEmpty()) {
            query.append(" AND material = ?");
        }
        if (category != null && !category.isEmpty()) {
            query.append(" AND category = ?");
        }

        PreparedStatement pstmt = conn.prepareStatement(query.toString());
        int paramIndex = 1;

        if (title != null && !title.isEmpty()) {
            pstmt.setString(paramIndex++, "%" + title + "%");
        }
        if (author != null && !author.isEmpty()) {
            pstmt.setString(paramIndex++, "%" + author + "%");
        }
        if (material != null && !material.isEmpty()) {
            pstmt.setString(paramIndex++, material);
        }
        if (category != null && !category.isEmpty()) {
            pstmt.setString(paramIndex++, category);
        }

        try (ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                documentListSearchResult.add(
                        DocumentFactory.createDocument(
                                rs.getString("title"),
                                rs.getString("author"),
                                rs.getString("material"),
                                rs.getString("category")
                        )
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println("tim kiem duoc cac document");
        return documentListSearchResult;
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
                    case book:
                        choiceCategoryDocument.setItems(FXCollections.observableArrayList(
                                Arrays.stream(Book.BookCategory.values())
                                        .map(Book.BookCategory::name)
                                        .collect(Collectors.toList())
                        ));
                        break;
                    case thesis:
                        choiceCategoryDocument.setItems(FXCollections.observableArrayList(
                                Arrays.stream(Thesis.ThesisCategory.values())
                                        .map(Thesis.ThesisCategory::name)
                                        .collect(Collectors.toList())
                        ));
                        break;
                    case newspaper:
                        choiceCategoryDocument.setItems(FXCollections.observableArrayList(
                                Arrays.stream(Newspaper.NewspaperCategory.values())
                                        .map(Newspaper.NewspaperCategory::name)
                                        .collect(Collectors.toList())
                        ));
                        break;
                    case journal:
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
                    documentListSearchResult = getDocumentListSearchResult(titleDocument, authorDocument, materialDocument, categoryDocument);
                    searchResultsTableView.setItems(documentListSearchResult);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });

        choiceCategoryDocument.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            categoryDocument = newValue;

            try {
                documentListSearchResult = getDocumentListSearchResult(titleDocument, authorDocument, materialDocument, categoryDocument);
                searchResultsTableView.setItems(documentListSearchResult);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        conn = DatabaseManager.connect();

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
