package uet.librarymanagementsystem.controllers.admin;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.ChoiceBox;
import uet.librarymanagementsystem.entity.documents.Document;
import uet.librarymanagementsystem.entity.documents.MaterialType;
import uet.librarymanagementsystem.entity.documents.materials.Book;
import uet.librarymanagementsystem.services.documentServices.DeleteDocumentService;
import uet.librarymanagementsystem.services.documentServices.SearchDocumentService;

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
    void addDocumentButtonOnClick(MouseEvent event) {
        performAdd();
    }

    private void performAdd() {
        Document selectedDocument = searchResultsTableView.getSelectionModel().getSelectedItem();
        if (selectedDocument != null) {
            documentsListToDelete.add(selectedDocument);
            documentsListSearchResult.remove(selectedDocument);
            searchResultsTableView.setItems(documentsListSearchResult);
        }
    }

    @FXML
    void deleteAllDocumentButtonOnClick(MouseEvent event) {
        if (documentsListToDelete.isEmpty()) {
            System.out.println("Danh sách tài liệu cần xóa đang trống.");
            return;
        }

        DeleteDocumentService deleteDocumentService = new DeleteDocumentService();

        // Duyệt qua danh sách để xóa từng tài liệu
        for (Document document : documentsListToDelete) {
            deleteDocumentService.deleteDocument(
                    document.getId(),
                    document.getMaterial(),
                    document.getCategory(),
                    document.getTitle(),
                    document.getAuthor()
            );
        }

        // Xóa tất cả các tài liệu trong danh sách
        documentsListToDelete.clear();
        deleteResultsTableView.setItems(documentsListToDelete);
        System.out.println("Xóa tất cả tài liệu trong danh sách thành công.");
    }


    @FXML
    void deleteDocumentButtonOnClick(MouseEvent event) {
        // Lấy danh sách các tài liệu được chọn
        ObservableList<Document> selectedDocuments = deleteResultsTableView.getSelectionModel().getSelectedItems();

        if (selectedDocuments.isEmpty()) {
            System.out.println("Không có tài liệu nào được chọn để xóa.");
            return;
        }

        DeleteDocumentService deleteDocumentService = new DeleteDocumentService();

        // Duyệt qua từng tài liệu để xóa
        for (Document document : selectedDocuments) {
            deleteDocumentService.deleteDocument(
                    document.getId(),
                    document.getMaterial(),
                    document.getCategory(),
                    document.getTitle(),
                    document.getAuthor()
            );
        }

        // Cập nhật lại danh sách hiển thị
        documentsListToDelete.removeAll(selectedDocuments);
        deleteResultsTableView.setItems(documentsListToDelete);
        System.out.println("Xóa các tài liệu đã chọn thành công.");
    }


    @FXML
    void removeDocumentButtonOnClick(MouseEvent event) {
        performRemove();
    }

    private void performRemove() {
        Document selectedDocument = deleteResultsTableView.getSelectionModel().getSelectedItem();
        if (selectedDocument != null) {
            documentsListSearchResult.add(selectedDocument);
            searchResultsTableView.setItems(documentsListSearchResult);
            documentsListToDelete.remove(selectedDocument);
            deleteResultsTableView.setItems(documentsListToDelete);
        }
    }

    private SearchDocumentService searchDocumentService;
    private ObservableList<Document> documentsListSearchResult;
    private ObservableList<Document> documentsListToDelete;

    @FXML
    private void searchDocumentButtonOnClick(MouseEvent event) throws SQLException {
        performSearch();

        ObservableList<Document> documents = searchDocumentService.searchByNotNull(titleDocument, authorDocument, materialDocument, categoryDocument);
        documents.forEach(document -> {
            System.out.print(document.getId() + " - " + document.getTitle() + " - " + document.getAuthor() + " - " + document.getMaterial() + " - " + document.getCategory());

            // Kiểm tra xem tài liệu có phải là Book không để lấy ISBN
            if (document instanceof Book) {
                Book book = (Book) document;
                System.out.println(" - ISBN: " + book.getIsbn());
            } else {
                System.out.println();
            }
        });
    }

    private void performSearch() throws SQLException {
        documentsListSearchResult = searchDocumentService.searchByNotNull(titleDocument, authorDocument, materialDocument, categoryDocument);
        searchResultsTableView.setItems(documentsListSearchResult);
    }

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

    private void setupChoiceBoxes() {
        ObservableList<MaterialType> materialTypesWithEmptyOption = FXCollections.observableArrayList();
        materialTypesWithEmptyOption.add(null); // Thêm tùy chọn trống
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

        idColumnDocumentsToDelete.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getId()));
        titleColumnDocumentsToDelete.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTitle()));
        authorColumnDocumentsToDelete.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAuthor()));
        materialColumnDocumentsToDelete.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMaterial()));
        categoryColumnDocumentsToDelete.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCategory()));
        //isbnColumnDocumentsToBorrow.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getIsbn()));

        documentsListToDelete = FXCollections.observableArrayList();
        deleteResultsTableView.setItems(documentsListToDelete);

        setupTextFieldsListeners();
        setupChoiceBoxes();
    }
}

