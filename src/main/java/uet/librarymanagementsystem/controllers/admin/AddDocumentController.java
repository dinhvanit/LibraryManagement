package uet.librarymanagementsystem.controllers.admin;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import uet.librarymanagementsystem.entity.documents.Document;
import uet.librarymanagementsystem.entity.documents.DocumentFactory;
import uet.librarymanagementsystem.entity.documents.MaterialType;
import uet.librarymanagementsystem.entity.documents.materials.Book;
import uet.librarymanagementsystem.services.documentServices.AddDocumentService;

import java.sql.SQLException;

public class AddDocumentController {

    @FXML
    private ChoiceBox<MaterialType> choiceMaterialAddDoc;

    @FXML
    private ChoiceBox<String> choiceCategoryAddDoc;

    @FXML
    private TextField fieldAuthorAddDoc;

    @FXML
    private Spinner<Integer> spinerQuantityAddDoc;

    @FXML
    private TextField fieldTitleAddDoc;

    @FXML
    private HBox isbnHbox;

    @FXML
    private TextField fieldISBN;

    @FXML
    private Label isbnValidLabel;

    @FXML
    private TableView<Document> addDocumentTableView;

    @FXML
    private TableColumn<Document, String> titleColumnAddResults;

    @FXML
    private TableColumn<Document, String> authorColumnAddResults;

    @FXML
    private TableColumn<Document, String> materialColumnAddResults;

    @FXML
    private TableColumn<Document, String> categoryColumnAddResults;

    @FXML
    private Label statusLabel;

    @FXML
    private Label titleValidLabel, authorValidLabel, quantityValidLabel;

    private final ObservableList<Document> documents = FXCollections.observableArrayList();
    private final AddDocumentService addDocumentService = new AddDocumentService();

    @FXML
    private void addDocumentButtonOnClick() throws SQLException {
        MaterialType materialType = choiceMaterialAddDoc.getValue();
        String category = choiceCategoryAddDoc.getValue();
        String title = fieldTitleAddDoc.getText().trim();
        String author = fieldAuthorAddDoc.getText().trim();
        String isbn = fieldISBN.getText().trim();
        Integer quantity = spinerQuantityAddDoc.getValue();

        // Xóa label trạng thái ban đầu
        boolean isValid = true;

        // Kiểm tra từng trường và cập nhật label cảnh báo tương ứng
        if (materialType == null) {
            statusLabel.setText("Material Type is required.");
            statusLabel.setStyle("-fx-text-fill: red;");
            isValid = false;
        }

        if (category == null) {
            statusLabel.setText("Category is required.");
            statusLabel.setStyle("-fx-text-fill: red;");
            isValid = false;
        }

        if (title.isEmpty()) {
            titleValidLabel.setText("Title is required.");
            titleValidLabel.setVisible(true);
            isValid = false;
        } else {
            titleValidLabel.setVisible(false);
        }

        if (author.isEmpty()) {
            authorValidLabel.setText("Author is required.");
            authorValidLabel.setVisible(true);
            isValid = false;
        } else {
            authorValidLabel.setVisible(false);
        }

        if (quantity == null || quantity <= 0) {
            quantityValidLabel.setText("Quantity must be greater than 0.");
            quantityValidLabel.setVisible(true);
            isValid = false;
        } else {
            quantityValidLabel.setVisible(false);
        }

        if (materialType == MaterialType.BOOK && isbn.isEmpty()) {
            isbnValidLabel.setText("ISBN is required for books.");
            isbnValidLabel.setVisible(true);
            isValid = false;
        } else {
            isbnValidLabel.setVisible(false);
        }

        // Nếu có lỗi đầu vào, dừng hàm
        if (!isValid) {
            return;
        }

        // Tạo các tài liệu theo số lượng
        for (int i = 0; i < quantity; i++) {
            try {
                Document document = DocumentFactory.createDocument(null, title, author, materialType.name(), category, isbn);
                documents.add(document);
            } catch (IllegalArgumentException e) {
                statusLabel.setText("Invalid category for selected material type.");
                statusLabel.setStyle("-fx-text-fill: red;");
                return;
            }
        }

        // Cập nhật giao diện TableView
        addDocumentTableView.setItems(documents);
        statusLabel.setText("Document(s) added to the list!");
        statusLabel.setStyle("-fx-text-fill: green;");
        clearFields();
    }


    private void clearFields() {
        choiceMaterialAddDoc.getSelectionModel().clearSelection();
        choiceCategoryAddDoc.getSelectionModel().clearSelection();
        fieldTitleAddDoc.clear();
        fieldAuthorAddDoc.clear();
        fieldISBN.clear();
        isbnHbox.setVisible(false);
    }

    @FXML
    private void removeDocumentButtonOnClick() {
        Document selectedDocument = addDocumentTableView.getSelectionModel().getSelectedItem();
        if (selectedDocument != null) {
            documents.remove(selectedDocument);
            addDocumentTableView.setItems(documents);
            statusLabel.setText("Selected document removed from the list.");
        } else {
            statusLabel.setText("Please select a document to remove.");
        }
    }

    @FXML
    private void removeAllDocumentButtonOnClick() {
        documents.clear();
        addDocumentTableView.setItems(documents);
        statusLabel.setText("All documents removed from the list.");
    }

    @FXML
    private void saveAllDocumentButtonOnClick() {
        if (documents.isEmpty()) {
            statusLabel.setText("No documents to save.");
            return;
        }

        try {
            for (Document document : documents) {
                String isbn = (document instanceof Book) ? ((Book) document).getIsbn() : null;
                addDocumentService.addDocument(
                        document.getTitle(),
                        document.getAuthor(),
                        document.getMaterial(),
                        document.getCategory(),
                        isbn
                );
            }

            // Sau khi lưu, xóa danh sách và cập nhật giao diện
            documents.clear();
            addDocumentTableView.setItems(documents);
            statusLabel.setText("All documents have been saved to the database.");
        } catch (Exception e) {
            statusLabel.setText("Error occurred while saving documents.");
            e.printStackTrace();
        }
    }

    private boolean validateDocumentInput() {
        boolean isValid = true;

        // Kiểm tra trường Title
        if (fieldTitleAddDoc.getText().trim().isEmpty()) {
            titleValidLabel.setText("Title cannot be empty.");
            titleValidLabel.setVisible(true);
            isValid = false;
        } else {
            titleValidLabel.setVisible(false);
        }

        // Kiểm tra trường Author
        if (fieldAuthorAddDoc.getText().trim().isEmpty()) {
            authorValidLabel.setText("Author cannot be empty.");
            authorValidLabel.setVisible(true);
            isValid = false;
        } else {
            authorValidLabel.setVisible(false);
        }

        // Kiểm tra trường MaterialType
        if (choiceMaterialAddDoc.getValue() == null) {
            statusLabel.setText("Material Type must be selected.");
            statusLabel.setStyle("-fx-text-fill: red;");
            isValid = false;
        } else {
            statusLabel.setText("");
        }

        // Kiểm tra trường Category
        if (choiceCategoryAddDoc.getValue() == null) {
            statusLabel.setText("Category must be selected.");
            statusLabel.setStyle("-fx-text-fill: red;");
            isValid = false;
        } else {
            statusLabel.setText("");
        }

        // Kiểm tra ISBN nếu MaterialType là BOOK
        if (choiceMaterialAddDoc.getValue() == MaterialType.BOOK && fieldISBN.getText().trim().isEmpty()) {
            isbnValidLabel.setText("ISBN is required for books.");
            isbnValidLabel.setVisible(true);
            isValid = false;
        } else {
            isbnValidLabel.setVisible(false);
        }

        // Kiểm tra trường Quantity
        if (spinerQuantityAddDoc.getValue() == null || spinerQuantityAddDoc.getValue() <= 0) {
            quantityValidLabel.setText("Quantity must be greater than 0.");
            quantityValidLabel.setVisible(true);
            isValid = false;
        } else {
            quantityValidLabel.setVisible(false);
        }

        return isValid;
    }



    @FXML
    public void initialize() {
        // Khởi tạo các lựa chọn MaterialType
        choiceMaterialAddDoc.setItems(FXCollections.observableArrayList(MaterialType.values()));

        // Cập nhật khi chọn MaterialType
        choiceMaterialAddDoc.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                choiceCategoryAddDoc.setItems(MaterialType.getCategoriesForMaterial(newValue));
                boolean isBook = newValue == MaterialType.BOOK;
                isbnHbox.setVisible(isBook);
                isbnValidLabel.setVisible(isBook);
            }
        });

        // Thiết lập Spinner cho số lượng
        spinerQuantityAddDoc.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, Integer.MAX_VALUE, 1));
        spinerQuantityAddDoc.setEditable(true);

        // Gán dữ liệu cho các cột trong TableView
        titleColumnAddResults.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTitle()));
        authorColumnAddResults.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAuthor()));
        materialColumnAddResults.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMaterial()));
        categoryColumnAddResults.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCategory()));


    }
}
