package uet.librarymanagementsystem.controllers.admin;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import uet.librarymanagementsystem.entity.documents.Document;
import uet.librarymanagementsystem.entity.documents.DocumentFactory;
import uet.librarymanagementsystem.entity.documents.MaterialType;
import uet.librarymanagementsystem.entity.documents.materials.Book;
import uet.librarymanagementsystem.services.documentServices.AddDocumentService;
import uet.librarymanagementsystem.services.documentServices.BookLookupService;
import uet.librarymanagementsystem.util.ValidationLabelUtil;

public class AddDocumentController {

    @FXML
    private ChoiceBox<MaterialType> choiceMaterialAddDoc;

    @FXML
    private ChoiceBox<String> choiceCategoryAddDoc;

    @FXML
    private TextField fieldAuthorAddDoc, fieldTitleAddDoc, fieldISBN;

    @FXML
    private Spinner<Integer> spinerQuantityAddDoc;

    @FXML
    private HBox isbnHbox;

    @FXML
    private Label materialValidLabel, categoryValidLabel, isbnValidLabel, titleValidLabel, authorValidLabel, statusLabel;

    @FXML
    private TableView<Document> addDocumentTableView;

    @FXML
    private TableColumn<Document, String> titleColumnAddResults, authorColumnAddResults, materialColumnAddResults, categoryColumnAddResults;

    private final ObservableList<Document> documents = FXCollections.observableArrayList();
    private final AddDocumentService addDocumentService = new AddDocumentService();
    private final ValidationLabelUtil validationUtil = new ValidationLabelUtil();

    @FXML
    private void initialize() {
        setupChoiceBoxes();
        setupValidationListeners();
        setupTableViewColumns();
        setupSpinner();
    }

    @FXML
    private void addDocumentButtonOnClick() {
        if (!validateInputs()) {
            statusLabel.setText("Please fix the errors before adding.");
            statusLabel.setStyle("-fx-text-fill: red;");
            return;
        }

        try {
            MaterialType materialType = choiceMaterialAddDoc.getValue();
            String category = choiceCategoryAddDoc.getValue();
            String title = fieldTitleAddDoc.getText().trim();
            String author = fieldAuthorAddDoc.getText().trim();
            String isbn = fieldISBN.getText().trim();
            int quantity = spinerQuantityAddDoc.getValue();

            for (int i = 0; i < quantity; i++) {
                Document document = DocumentFactory.createDocument(
                        null, title, author, materialType.name(), category, isbn.isEmpty() ? null : isbn
                );
                documents.add(document);
            }

            addDocumentTableView.setItems(documents);
            statusLabel.setText("Document(s) added to the list!");
            statusLabel.setStyle("-fx-text-fill: green;");
            clearFields();
        } catch (IllegalArgumentException e) {
            statusLabel.setText("Invalid category for selected material type.");
            statusLabel.setStyle("-fx-text-fill: red;");
        }
    }

    @FXML
    private void saveAllDocumentButtonOnClick() {
        if (documents.isEmpty()) {
            statusLabel.setText("No documents to save.");
            return;
        }

        for (Document document : documents) {
            addDocumentService.addDocument(
                    document.getTitle(), document.getAuthor(), document.getMaterial(),
                    document.getCategory(), document instanceof Book ? ((Book) document).getIsbn() : null
            );
        }
        documents.clear();
        addDocumentTableView.setItems(documents);
        statusLabel.setText("All documents have been saved to the database.");
        statusLabel.setStyle("-fx-text-fill: green;");
    }

    @FXML
    private void removeDocumentButtonOnClick() {
        Document selectedDocument = addDocumentTableView.getSelectionModel().getSelectedItem();
        if (selectedDocument != null) {
            documents.remove(selectedDocument);
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
    public void searchInforByAPI(MouseEvent event) {
        String isbn = fieldISBN.getText().trim();
        if (isbn.isEmpty()) {
            updateStatusLabel(isbnValidLabel, "Please enter an ISBN before searching.", "-fx-text-fill: red;");
            return;
        }
        lookupBookInfo(isbn);
    }

    private void lookupBookInfo(String isbn) {
        BookLookupService lookupService = new BookLookupService(isbn);
        if (lookupService.checkBookInfoByISBN()) {
            fieldTitleAddDoc.setText(lookupService.getTitleBook());
            fieldAuthorAddDoc.setText(lookupService.getTheFirstAuthor());
            choiceCategoryAddDoc.setValue(lookupService.getTheFirstCategory());
            updateStatusLabel(isbnValidLabel, "ISBN found!", "-fx-text-fill: green;");
        } else {
            updateStatusLabel(isbnValidLabel, "ISBN not found or invalid.", "-fx-text-fill: red;");
        }
    }

    private void setupChoiceBoxes() {
        choiceMaterialAddDoc.setItems(FXCollections.observableArrayList(MaterialType.values()));
        choiceMaterialAddDoc.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                choiceCategoryAddDoc.setItems(MaterialType.getCategoriesForMaterial(newValue));
                boolean isBook = newValue == MaterialType.BOOK;
                isbnHbox.setVisible(isBook);
                isbnValidLabel.setVisible(isBook);
            }
            validateChoiceBoxSelection(choiceMaterialAddDoc.getValue(), materialValidLabel);
        });

        choiceCategoryAddDoc.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            validateChoiceBoxSelection(choiceCategoryAddDoc.getValue(), categoryValidLabel);
        });
    }

    private void setupValidationListeners() {
        fieldTitleAddDoc.textProperty().addListener((observable, oldValue, newValue) ->
                validateField(newValue, titleValidLabel, ValidationLabelUtil.ValidationType.EMPTY));

        fieldAuthorAddDoc.textProperty().addListener((observable, oldValue, newValue) ->
                validateField(newValue, authorValidLabel, ValidationLabelUtil.ValidationType.EMPTY));

        spinerQuantityAddDoc.valueProperty().addListener((observable, oldValue, newValue) -> {
            validateQuantity(newValue, isbnValidLabel); // Kiểm tra giá trị cho Spinner
        });
    }

    private void setupTableViewColumns() {
        titleColumnAddResults.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTitle()));
        authorColumnAddResults.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAuthor()));
        materialColumnAddResults.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMaterial()));
        categoryColumnAddResults.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCategory()));
    }

    private void setupSpinner() {
        spinerQuantityAddDoc.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, Integer.MAX_VALUE, 1));
        spinerQuantityAddDoc.setEditable(true);
    }

    private boolean validateChoiceBoxSelection(Object choiceBoxSelection, Label label) {
        if (choiceBoxSelection == null || choiceBoxSelection.toString().isEmpty()) {
            updateStatusLabel(label, "Trường này không được bỏ trống", "-fx-text-fill: red;");
            return false;
        }
        updateStatusLabel(label, "", "-fx-text-fill: green;");
        return true;
    }

    private boolean validateQuantity(Integer quantity, Label label) {
        String quantityText = quantity.toString();
        String errorMessage = validationUtil.validateNumericField(quantityText, "Số lượng phải là số hợp lệ");

        if (!errorMessage.isEmpty()) {
            updateStatusLabel(label, errorMessage, "-fx-text-fill: red;");
            return false;
        } else if (quantity <= 0) {
            updateStatusLabel(label, "Số lượng phải lớn hơn 0", "-fx-text-fill: red;");
            return false;
        }
        updateStatusLabel(label, "", "-fx-text-fill: green;");
        return true;
    }

    private boolean validateInputs() {
        return validateField(fieldTitleAddDoc.getText().trim(), titleValidLabel, ValidationLabelUtil.ValidationType.EMPTY) &&
                validateField(fieldAuthorAddDoc.getText().trim(), authorValidLabel, ValidationLabelUtil.ValidationType.EMPTY) &&
                validateChoiceBoxSelection(choiceMaterialAddDoc.getValue(), materialValidLabel) &&
                validateChoiceBoxSelection(choiceCategoryAddDoc.getValue(), categoryValidLabel) &&
                validateQuantity(spinerQuantityAddDoc.getValue(), isbnValidLabel);
    }
    private boolean validateField(String field, Label label, ValidationLabelUtil.ValidationType validationType) {
        String errorMessage = validationUtil.validateField(field, validationType);
        updateStatusLabel(label, errorMessage, errorMessage.isEmpty() ? "-fx-text-fill: green;" : "-fx-text-fill: red;");
        return errorMessage.isEmpty();
    }

    private void updateStatusLabel(Label label, String message, String style) {
        label.setText(message);
        label.setStyle(style);
    }

    private void clearFields() {
        //choiceMaterialAddDoc.getSelectionModel().clearSelection(); cần load lại mỗi lần add thì bỏ
        choiceCategoryAddDoc.getSelectionModel().clearSelection();
        fieldTitleAddDoc.clear();
        fieldAuthorAddDoc.clear();
        fieldISBN.clear();
        isbnValidLabel.setText("");
        isbnHbox.setVisible(false);
        spinerQuantityAddDoc.getValueFactory().setValue(1);
    }
}
