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
import uet.librarymanagementsystem.DatabaseOperation.DatabaseManager;
import uet.librarymanagementsystem.entity.Document;

import java.net.URL;
import java.sql.*;
import java.util.Objects;
import java.util.ResourceBundle;

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
    private ChoiceBox<String> choiceMaterialDocument;

    @FXML
    private TextField fieldAuthorDocument;

    @FXML
    private TextField fieldTitleDocument;
    private Throwable e;

    @FXML
    void addDocumentToBorrowButtonOnClick(MouseEvent event) throws SQLException {

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
        documentListSearchResult = getDocumentListSearchResult(titleDocument, authorDocument, categoryDocument);
        searchResultsTableView.setItems(documentListSearchResult);
    }

    public ObservableList<Document> getDocumentListSearchResult(String title, String author, String category) throws SQLException {

        documentListSearchResult.clear();
        documentListSearchResult = FXCollections.observableArrayList();

        StringBuilder query = new StringBuilder("SELECT id, title, author, category FROM Document WHERE 1=1");

        if (title != null && !title.isEmpty()) {
            query.append(" AND title = ?");
        }

        if (author != null && !author.isEmpty()) {
            query.append(" AND author = ?");
        }

        if (category != null && !category.isEmpty()) {
            System.out.println("HE");
            System.out.println(category);
            query.append(" AND category = ?");
        }

        PreparedStatement pstmt = conn.prepareStatement(query.toString());

        int paramIndex = 1;

        if (title != null && !title.isEmpty()) {
            pstmt.setString(paramIndex++, title);
        }

        if (author != null && !author.isEmpty()) {
            pstmt.setString(paramIndex++, author);
        }

        if (category != null && !category.isEmpty()) {
            pstmt.setString(paramIndex++, category);
        }

        try (ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                documentListSearchResult.add(new Document(
                        rs.getString("id"),
                        rs.getString("title"),
                        rs.getString("author"),
                        "100",
                        rs.getString("category"),
                        10
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return documentListSearchResult;
    }

    void textFileTitleAuthor() {
        fieldTitleDocument.textProperty().addListener((observable, oldValue, newValue) -> {
            titleDocument = fieldTitleDocument.getText();
            System.out.println(titleDocument);
        });
        fieldAuthorDocument.textProperty().addListener((observable, oldValue, newValue) -> {
            authorDocument = fieldAuthorDocument.getText();
        });

    }

    void choice() {
        choiceMaterialDocument.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("Selected material: " + newValue);
            materialDocument = choiceMaterialDocument.getValue();

            if (Objects.equals(materialDocument, "Book")) {
                choiceCategoryDocument.setItems(FXCollections.observableArrayList("Programming", "Mathematics", "Software Engineering", ""));
            }
            else if (Objects.equals(materialDocument, "Thesis")) {
                choiceCategoryDocument.setItems(FXCollections.observableArrayList("Programming", "Computer Science", ""));
            }
            else if (Objects.equals(materialDocument, "Newspaper")) {
                choiceCategoryDocument.setItems(FXCollections.observableArrayList("Programming", "NewTV", ""));
            }
        });

        choiceCategoryDocument.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            categoryDocument = newValue;
            System.out.println("Selected category: " + categoryDocument);
        });
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("INIT");

        conn = DatabaseManager.connect();

        idColumnSearchResults.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getId()));
        titleColumnSearchResults.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTitle()));
        authorColumnSearchResults.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAuthor()));
        materialColumnSearchResults.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMaterial()));
        categoryColumnSearchResults.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCategory()));

        documentListSearchResult = FXCollections.observableArrayList();

        searchResultsTableView.setItems(documentListSearchResult);

        choiceMaterialDocument.setItems(FXCollections.observableArrayList("Book", "Thesis", "Newspaper", ""));
        choiceMaterialDocument.setValue("");
        choice();

    }
}
