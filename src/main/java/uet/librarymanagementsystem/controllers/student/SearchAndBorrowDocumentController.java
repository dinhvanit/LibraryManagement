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
import uet.librarymanagementsystem.services.transactionServices.SearchTransactionService;
import uet.librarymanagementsystem.services.userServices.SearchStudentService;
import uet.librarymanagementsystem.util.WindowUtil;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

/**
 * Controller class to manage the search and borrowing functionality for documents.
 */
public class SearchAndBorrowDocumentController implements Initializable {
    private final int borrowingPeriod = 6;
    private final int maxDocuments = 10;
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
    private Label notionChooseADocumentLabel;

    @FXML
    private Label notionChooseAddLabel;

    /**
     * Handles adding a document to the list of documents to borrow.
     *
     * @param event the mouse event triggered when clicking the add button.
     */
    @FXML
    private void addDocumentToBorrowButtonOnClick(MouseEvent event) throws SQLException {
        performAdd();
    }

    private void performAdd() {
        dueDateLabel.setText(setDueDate());
        Document selectedDocument = searchResultsTableView.getSelectionModel().getSelectedItem();
        if (selectedDocument != null) {
            notionChooseAddLabel.setVisible(false);
            documentsListToBorrow.add(selectedDocument);
            documentsListSearchResult.remove(selectedDocument);
            searchResultsTableView.setItems(documentsListSearchResult);
        } else {
            notionChooseAddLabel.setVisible(true);
        }
    }

    private String setDueDate() {
        return LocalDate.now().plusMonths(borrowingPeriod).format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
    }

    /**
     * Handles borrowing all documents in the borrowing list.
     *
     * @param event the mouse event triggered when clicking the borrow all button.
     */
    @FXML
    void borrowAllDocumentsButtonOnClick(MouseEvent event) throws SQLException {
        String id_student = LoginController.getIdCurrentStudent();
        SearchTransactionService searchTransactionService = new SearchTransactionService();
        ObservableList<Transaction> listTransactionBorrowing = searchTransactionService.searchTransactionByIdStudentBorrowing(id_student);
        if (documentsListToBorrow.isEmpty()) {
            notionChooseADocumentLabel.setVisible(true);
        } else {
            if (listTransactionBorrowing.size() + documentsListToBorrow.size() > maxDocuments) {
                Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                WindowUtil.showSecondaryWindow(Page.NOTION_MAX_DOCUMENTS, "Notion", currentStage);
                System.out.println("You have exceeded the maximum number of documents you can borrow.");
            } else {
                notionChooseADocumentLabel.setVisible(false);
                String today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
                String dueDate = setDueDate();
                for (Document document : documentsListToBorrow) {
                    Transaction transaction = new Transaction(document, student, today, null, dueDate);
                    TransactionsTable.insertTransaction(transaction);
                }
                documentsListToBorrow.clear();
            }
        }
    }

    /**
     * Handles borrowing a specific document.
     *
     * @param event the mouse event triggered when clicking the borrow button.
     */
    @FXML
    void borrowDocumentButtonOnClick(MouseEvent event) throws SQLException {
        performBorrow(event);
    }

    private void performBorrow(MouseEvent event) throws SQLException {
        String id_student = LoginController.getIdCurrentStudent();
        Document selectedDocument = documentsToBorrowTableView.getSelectionModel().getSelectedItem();
        SearchTransactionService searchTransactionService = new SearchTransactionService();
        ObservableList<Transaction> listTransactionBorrowing = searchTransactionService.searchTransactionByIdStudentBorrowing(id_student);
        if (selectedDocument != null) {
            notionChooseADocumentLabel.setVisible(false);
            if (listTransactionBorrowing.size() >= maxDocuments) {
                System.out.println("You have exceeded the maximum number of documents you can borrow.");
                Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                WindowUtil.showSecondaryWindow(Page.NOTION_MAX_DOCUMENTS, "Notion", currentStage);
            } else {
                String today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
                String dueDate = setDueDate();
                Transaction transaction = new Transaction(selectedDocument, student, today, null, dueDate);
                TransactionsTable.insertTransaction(transaction);
                documentsListToBorrow.remove(selectedDocument);
                documentsToBorrowTableView.setItems(documentsListToBorrow);
            }
        } else {
            notionChooseADocumentLabel.setVisible(true);
        }
    }

    /**
     * Handles deleting a document from the borrowing list.
     *
     * @param event the mouse event triggered when clicking the delete button.
     */
    @FXML
    void deleteDocumentButtonOnClick(MouseEvent event) {
        performDelete();
    }

    private void performDelete() {
        Document selectedDocument = documentsToBorrowTableView.getSelectionModel().getSelectedItem();
        if (selectedDocument != null) {
            notionChooseADocumentLabel.setVisible(false);
            documentsListSearchResult.add(selectedDocument);
            searchResultsTableView.setItems(documentsListSearchResult);
            documentsListToBorrow.remove(selectedDocument);
            documentsToBorrowTableView.setItems(documentsListToBorrow);
        } else {
            notionChooseADocumentLabel.setVisible(true);
        }
    }

    /**
     * Handles showing detailed information about a selected document.
     *
     * @param event the mouse event triggered when clicking a document.
     */
    @FXML
    void infoDocumentClick(MouseEvent event) {
        Document selectedDocument = searchResultsTableView.getSelectionModel().getSelectedItem();
        if (selectedDocument != null) {
            notionChooseAddLabel.setVisible(false);
            ShareDataService.setDocumentShare(selectedDocument);
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            WindowUtil.showSecondaryWindowWithShowInfo(
                    Page.SHOW_INFO_DOCUMENT, "Information Document", currentStage, false, false);
        } else {
            notionChooseAddLabel.setVisible(true);
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

        documentsListSearchResult = FXCollections.observableArrayList();
        searchResultsTableView.setItems(documentsListSearchResult);

        idColumnDocumentsToBorrow.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getId()));
        titleColumnDocumentsToBorrow.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTitle()));
        authorColumnDocumentsToBorrow.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAuthor()));
        materialColumnDocumentsToBorrow.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMaterial()));
        categoryColumnDocumentsToBorrow.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCategory()));

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
