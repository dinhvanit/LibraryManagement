package uet.librarymanagementsystem.controllers.student;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import uet.librarymanagementsystem.DatabaseOperation.TransactionsTable;
import uet.librarymanagementsystem.controllers.LoginController;
import uet.librarymanagementsystem.entity.Page;
import uet.librarymanagementsystem.entity.documents.Document;
import uet.librarymanagementsystem.entity.documents.MaterialType;
import uet.librarymanagementsystem.entity.transactions.Transaction;
import uet.librarymanagementsystem.entity.users.Student;
import uet.librarymanagementsystem.services.documentServices.SearchDocumentService;
import uet.librarymanagementsystem.services.shareDataServers.ShareDataService;
import uet.librarymanagementsystem.services.userServices.SearchStudentService;
import uet.librarymanagementsystem.util.WindowUtil;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class SearchAndBorrowDocumentController implements Initializable {
    private final int borrowingPeriod = 6;
    private Student student;
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
            documentsListSearchResult.remove(selectedDocument);
            searchResultsTableView.setItems(documentsListSearchResult);
        }
    }

    private String setDueDate() {
        return LocalDate.now().plusMonths(borrowingPeriod).format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
    }

    @FXML
    void borrowAllDocumentsButtonOnClick(MouseEvent event) throws SQLException {
        String today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
        String dueDate = setDueDate();
        for (Document document : documentsListToBorrow) {
            Transaction transaction = new Transaction(document, student, today, null, dueDate);
            TransactionsTable.insertTransaction(transaction);
        }
        documentsListToBorrow.clear();
    }

    @FXML
    void borrowDocumentButtonOnClick(MouseEvent event) throws SQLException {
        performBorrow();
    }

    private void performBorrow() throws SQLException {
        Document selectedDocument = documentsToBorrowTableView.getSelectionModel().getSelectedItem();
        String today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
        String dueDate = setDueDate();
        Transaction transaction = new Transaction(selectedDocument, student, today, null, dueDate);
        TransactionsTable.insertTransaction(transaction);
        documentsListToBorrow.remove(selectedDocument);
        documentsToBorrowTableView.setItems(documentsListToBorrow);
    }

    @FXML
    void deleteDocumentButtonOnClick(MouseEvent event) {
        performDelete();
    }

    private void performDelete() {
        Document selectedDocument = documentsToBorrowTableView.getSelectionModel().getSelectedItem();
        if (selectedDocument != null) {
            documentsListSearchResult.add(selectedDocument);
            searchResultsTableView.setItems(documentsListSearchResult);
            documentsListToBorrow.remove(selectedDocument);
            documentsToBorrowTableView.setItems(documentsListToBorrow);
        }
    }

    @FXML
    void infoDocumentClick(MouseEvent event) {
        Document selectedDocument = searchResultsTableView.getSelectionModel().getSelectedItem();
        if (selectedDocument != null) {
            ShareDataService.setDocumentShare(selectedDocument);
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            WindowUtil.showSecondaryWindowWithShowInfo(
                    Page.SHOW_INFO_DOCUMENT, "Information Document", currentStage, false, false);
        }
    }

    @FXML
    void findDocumentButtonOnClick(MouseEvent event) throws SQLException {
        performSearch();
    }

    private void performSearch() throws SQLException {
        documentsListSearchResult = searchDocumentService.searchByNotNull(titleDocument, authorDocument, materialDocument, categoryDocument);
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
        ObservableList<MaterialType> materialTypesWithEmptyOption = FXCollections.observableArrayList();
        materialTypesWithEmptyOption.add(null); // Thêm tùy chọn trống
        materialTypesWithEmptyOption.addAll(MaterialType.values());
        choiceMaterialDocument.setItems(materialTypesWithEmptyOption);

        choiceMaterialDocument.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            materialDocument = (newValue == null) ? "" : newValue.name();
            ObservableList<String> categories = FXCollections.observableArrayList("");
            if (newValue != null) {
                categories.addAll(MaterialType.getCategoriesForMaterial(newValue));
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
        //isbnColumnSearchResults.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getIsbn()));

        documentsListSearchResult = FXCollections.observableArrayList();
        searchResultsTableView.setItems(documentsListSearchResult);

        idColumnDocumentsToBorrow.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getId()));
        titleColumnDocumentsToBorrow.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTitle()));
        authorColumnDocumentsToBorrow.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAuthor()));
        materialColumnDocumentsToBorrow.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMaterial()));
        categoryColumnDocumentsToBorrow.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCategory()));
        //isbnColumnDocumentsToBorrow.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getIsbn()));

        documentsListToBorrow = FXCollections.observableArrayList();
        documentsToBorrowTableView.setItems(documentsListToBorrow);

        String id_student = LoginController.getIdCurrentStudent();
        SearchStudentService searchStudentService = new SearchStudentService();
        try {
            student = searchStudentService.searchID(id_student);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        setupTextFieldsListeners();
        setupChoiceBoxes();
    }
}
