package uet.librarymanagementsystem.controllers.admin;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import uet.librarymanagementsystem.entity.Page;
import uet.librarymanagementsystem.entity.documents.Document;
import uet.librarymanagementsystem.entity.documents.MaterialType;
import uet.librarymanagementsystem.services.documentServices.DeleteDocumentService;
import uet.librarymanagementsystem.services.documentServices.SearchDocumentService;
import uet.librarymanagementsystem.services.shareDataServers.ShareDataService;
import uet.librarymanagementsystem.util.WindowUtil;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class SearchAndRemoveDocumentController  implements Initializable {


    private String titleDocument = "";
    private String authorDocument = "";
    private String materialDocument = "";
    private String categoryDocument = "";

    @FXML
    private TableColumn<Document, String> authorColumnDocumentsToDelete;

    @FXML
    private TableColumn<Document, String> authorColumnSearchResults;

    @FXML
    private TableColumn<Document, String> categoryColumnDocumentsToDelete;

    @FXML
    private TableColumn<Document, String> categoryColumnSearchResults;

    @FXML
    private ChoiceBox<String> choiceCategoryDocumentAdmin;

    @FXML
    private ChoiceBox<MaterialType> choiceMaterialDocumentAdmin;

    @FXML
    private TableView<Document> deleteResultsTableView;

    @FXML
    private TextField fieldAuthorDocumentAdmin;

    @FXML
    private TextField fieldTitleDocumentAdmin;

    @FXML
    private TableColumn<Document, String> idColumnDocumentsToDelete;

    @FXML
    private TableColumn<Document, String> idColumnSearchResults;

    @FXML
    private TableColumn<Document, String> materialColumnDocumentsToDelete;

    @FXML
    private TableColumn<Document, String> materialColumnSearchResults;

    @FXML
    private TableView<Document> searchResultsTableView;

    @FXML
    private TableColumn<Document, String> titleColumnDocumentsToDelete;

    @FXML
    private TableColumn<Document, String> titleColumnSearchResults;

    @FXML
    private Label notionChoiceDocumentAddLabel;

    @FXML
    private Label notionChoiceDocumentDeleteLabel;


    private SearchDocumentService searchDocumentService;
    private ObservableList<Document> documentsListSearchResult;
    private ObservableList<Document> documentsListToDelete;

    /**
     * Adds the selected document from the search results to the list of documents to delete.
     * @param event the mouse event triggered when the button is clicked
     */
    @FXML
    void addDocumentButtonOnClick(MouseEvent event) {
        performAdd();
    }

    /**
     * Adds the selected document from the search results to the list of documents to delete.
     */
    private void performAdd() {
        Document selectedDocument = searchResultsTableView.getSelectionModel().getSelectedItem();
        if (selectedDocument != null) {
            notionChoiceDocumentAddLabel.setVisible(false);
            documentsListToDelete.add(selectedDocument);
            documentsListSearchResult.remove(selectedDocument);
            searchResultsTableView.setItems(documentsListSearchResult);
        } else {
            notionChoiceDocumentAddLabel.setVisible(true);
        }
    }

    /**
     * Deletes all documents in the list of documents to delete.
     * @param event the mouse event triggered when the button is clicked
     */
    @FXML
    void deleteAllDocumentButtonOnClick(MouseEvent event) {
        if (documentsListToDelete.isEmpty()) {
            System.out.println("The list of documents to delete is empty.");
            return;
        }

        DeleteDocumentService deleteDocumentService = new DeleteDocumentService();

        for (Document document : documentsListToDelete) {
            deleteDocumentService.deleteDocument(
                    document.getId(),
                    document.getMaterial(),
                    document.getCategory(),
                    document.getTitle(),
                    document.getAuthor()
            );
        }

        documentsListToDelete.clear();
        deleteResultsTableView.setItems(documentsListToDelete);
        System.out.println("All documents in the list were successfully deleted.");
    }

    /**
     * Deletes the selected document from the list of documents to delete.
     * @param event the mouse event triggered when the button is clicked
     */
    @FXML
    void deleteDocumentButtonOnClick(MouseEvent event) {
        if (deleteResultsTableView.getSelectionModel().getSelectedItem() == null) {
            notionChoiceDocumentDeleteLabel.setVisible(true);
        } else {
            notionChoiceDocumentDeleteLabel.setVisible(false);
            DeleteDocumentService deleteDocumentService = new DeleteDocumentService();
            Document selectedDocument = deleteResultsTableView.getSelectionModel().getSelectedItem();
            deleteDocumentService.deleteDocument(
                    selectedDocument.getId(),
                    selectedDocument.getMaterial(),
                    selectedDocument.getCategory(),
                    selectedDocument.getTitle(),
                    selectedDocument.getAuthor()
            );
            documentsListToDelete.remove(selectedDocument);
            deleteResultsTableView.setItems(documentsListToDelete);
        }
    }

    /**
     * Removes the selected document from the list of documents to delete and adds it back to the search results.
     * @param event the mouse event triggered when the button is clicked
     */
    @FXML
    void removeDocumentButtonOnClick(MouseEvent event) {
        performRemove();
    }

    /**
     * Removes the selected document from the list of documents to delete and adds it back to the search results.
     */
    private void performRemove() {
        Document selectedDocument = deleteResultsTableView.getSelectionModel().getSelectedItem();
        if (selectedDocument != null) {
            notionChoiceDocumentDeleteLabel.setVisible(false);
            documentsListSearchResult.add(selectedDocument);
            searchResultsTableView.setItems(documentsListSearchResult);
            documentsListToDelete.remove(selectedDocument);
            deleteResultsTableView.setItems(documentsListToDelete);
        } else {
            notionChoiceDocumentDeleteLabel.setVisible(true);
        }
    }

    /**
     * Searches for documents based on user input and displays the results in the search results table view.
     * @param event the mouse event triggered when the button is clicked
     * @throws SQLException if a database access error occurs
     */
    @FXML
    private void searchDocumentButtonOnClick(MouseEvent event) throws SQLException {
        performSearch();
    }

    /**
     * Performs the search for documents based on user input and updates the search results table view.
     * @throws SQLException if a database access error occurs
     */
    private void performSearch() throws SQLException {
        documentsListSearchResult = searchDocumentService.searchByNotNull(titleDocument, authorDocument, materialDocument, categoryDocument);
        searchResultsTableView.setItems(documentsListSearchResult);
    }

    /**
     * Sets up listeners for text fields to perform real-time searches based on user input.
     */
    private void setupTextFieldsListeners() {
        fieldTitleDocumentAdmin.textProperty().addListener((observable, oldValue, newValue) -> {
            titleDocument = newValue;
            try {
                performSearch();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        fieldAuthorDocumentAdmin.textProperty().addListener((observable, oldValue, newValue) -> {
            authorDocument = newValue;
            try {
                performSearch();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Sets up the choice boxes for selecting material types and categories.
     */
    private void setupChoiceBoxes() {
        ObservableList<MaterialType> materialTypesWithEmptyOption = FXCollections.observableArrayList();
        materialTypesWithEmptyOption.add(null); // Adds an empty option
        materialTypesWithEmptyOption.addAll(MaterialType.values());
        choiceMaterialDocumentAdmin.setItems(materialTypesWithEmptyOption);

        choiceMaterialDocumentAdmin.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            materialDocument = (newValue == null) ? "" : newValue.name();
            ObservableList<String> categories = FXCollections.observableArrayList("");
            if (newValue != null) {
                categories.addAll(MaterialType.getCategoriesForMaterial(newValue));
            }
            choiceCategoryDocumentAdmin.setItems(categories);
            categoryDocument = "";
            choiceCategoryDocumentAdmin.getSelectionModel().clearSelection();
            try {
                performSearch();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        choiceCategoryDocumentAdmin.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            categoryDocument = (newValue == null || newValue.isEmpty()) ? "" : newValue;
            try {
                performSearch();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Opens a window to display detailed information about the selected document.
     * @param event the mouse event triggered when the button is clicked
     */
    @FXML
    void infoDocumentClick(MouseEvent event) {
        Document selectedDocument = searchResultsTableView.getSelectionModel().getSelectedItem();
        if (selectedDocument != null) {
            notionChoiceDocumentAddLabel.setVisible(false);
            ShareDataService.setDocumentShare(selectedDocument);
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            WindowUtil.showSecondaryWindowWithShowInfo(
                    Page.SHOW_INFO_DOCUMENT, "Information Document", currentStage, false, false);
        } else {
            notionChoiceDocumentAddLabel.setVisible(true);
        }
    }

    /**
     * Initializes the controller.
     * @param url            the location used to resolve relative paths for the root object
     * @param resourceBundle the resources used to localize the root object
     */
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

        idColumnDocumentsToDelete.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getId()));
        titleColumnDocumentsToDelete.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTitle()));
        authorColumnDocumentsToDelete.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAuthor()));
        materialColumnDocumentsToDelete.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMaterial()));
        categoryColumnDocumentsToDelete.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCategory()));

        documentsListToDelete = FXCollections.observableArrayList();
        deleteResultsTableView.setItems(documentsListToDelete);

        setupTextFieldsListeners();
        setupChoiceBoxes();

    }
}
