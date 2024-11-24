package uet.librarymanagementsystem.controllers.admin;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import uet.librarymanagementsystem.entity.Page;
import uet.librarymanagementsystem.entity.documents.Document;
import uet.librarymanagementsystem.entity.documents.DocumentFactory;
import uet.librarymanagementsystem.entity.documents.MaterialType;
import uet.librarymanagementsystem.entity.documents.materials.Book;
import uet.librarymanagementsystem.services.documentServices.AddDocumentService;
import uet.librarymanagementsystem.services.documentServices.BookLookupService;
import uet.librarymanagementsystem.util.ValidationLabelUtil;
import uet.librarymanagementsystem.util.WindowUtil;

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

    /**
     * Initializes.
     */
    @FXML
    private void initialize() {
        setupChoiceBoxes();
        setupValidationListeners();
        setupTableViewColumns();
        setupSpinner();
    }

    /**
     * Validates inputs and adds the document(s) to the list if valid.
     */
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

    /**
     * Saves all documents in the list to the database.
     * @param event the event triggering this action
     */
    @FXML
    private void saveAllDocumentButtonOnClick(Event event) {
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
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        WindowUtil.showSecondaryWindow(Page.NOTION_SUCCESS, "Notion success", currentStage);

        statusLabel.setText("All documents have been saved to the database.");
        statusLabel.setStyle("-fx-text-fill: green;");
    }

    /**
     * Removes the selected document from the list and updates the status label.
     */
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

    /**
     * Clears all documents from the list and updates the status label.
     */
    @FXML
    private void removeAllDocumentButtonOnClick() {
        documents.clear();
        addDocumentTableView.setItems(documents);
        statusLabel.setText("All documents removed from the list.");
    }

    /**
     * Looks up book information using the provided ISBN.
     * @param event the mouse event triggering this action
     */
    @FXML
    public void searchInforByAPI(MouseEvent event) {
        String isbn = fieldISBN.getText().trim();
        if (isbn.isEmpty()) {
            updateStatusLabel(isbnValidLabel, "Please enter an ISBN before searching.", "-fx-text-fill: red;");
            return;
        }
        lookupBookInfo(isbn);
    }

    /**
     * Updates fields with retrieved book data if found.
     * @param isbn the ISBN to look up
     */
    private void lookupBookInfo(String isbn) {
        BookLookupService lookupService = new BookLookupService(isbn);
        if (lookupService.checkBookInfoByISBN()) {
            fieldTitleAddDoc.setText(lookupService.getTitleBook());
            fieldAuthorAddDoc.setText(lookupService.getAllAuthors());
            choiceCategoryAddDoc.setValue(lookupService.getTheFirstCategory());
            updateStatusLabel(isbnValidLabel, "ISBN found!", "-fx-text-fill: green;");
        } else {
            updateStatusLabel(isbnValidLabel, "ISBN not found or invalid.", "-fx-text-fill: red;");
        }
    }

    /**
     * Sets up the choice boxes for material type and category.
     */
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

    /**
     * Sets up validation listeners for input fields and spinner.
     */
    private void setupValidationListeners() {
        fieldTitleAddDoc.textProperty().addListener((observable, oldValue, newValue) ->
                validateField(newValue, titleValidLabel, ValidationLabelUtil.ValidationType.EMPTY));

        fieldAuthorAddDoc.textProperty().addListener((observable, oldValue, newValue) ->
                validateField(newValue, authorValidLabel, ValidationLabelUtil.ValidationType.EMPTY));

        spinerQuantityAddDoc.valueProperty().addListener((observable, oldValue, newValue) -> {
            validateQuantity(newValue, isbnValidLabel);
        });
    }

    /**
     * Sets up the table view columns to display document data.
     */
    private void setupTableViewColumns() {
        titleColumnAddResults.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTitle()));
        authorColumnAddResults.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAuthor()));
        materialColumnAddResults.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMaterial()));
        categoryColumnAddResults.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCategory()));
    }

    /**
     * Configures the spinner for document quantity input.
     */
    private void setupSpinner() {
        spinerQuantityAddDoc.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, Integer.MAX_VALUE, 1));
        spinerQuantityAddDoc.setEditable(true);
    }

    /**
     * Validates the selected item in a choice box.
     * @param choiceBoxSelection the selected value in the choice box
     * @param label              the label to display validation messages
     * @return true if valid, false otherwise
     */
    private boolean validateChoiceBoxSelection(Object choiceBoxSelection, Label label) {
        if (choiceBoxSelection == null || choiceBoxSelection.toString().isEmpty()) {
            updateStatusLabel(label, "This field cannot be empty.", "-fx-text-fill: red;");
            return false;
        }
        updateStatusLabel(label, "", "-fx-text-fill: green;");
        return true;
    }

    /**
     * Validates the quantity input in the spinner.
     * @param quantity the quantity value to validate
     * @param label    the label to display validation messages
     * @return true if valid, false otherwise
     */
    private boolean validateQuantity(Integer quantity, Label label) {
        String quantityText = quantity.toString();
        String errorMessage = validationUtil.validateNumericField(quantityText, "Quantity must be a valid number");

        if (!errorMessage.isEmpty()) {
            updateStatusLabel(label, errorMessage, "-fx-text-fill: red;");
            return false;
        } else if (quantity <= 0) {
            updateStatusLabel(label, "Quantity must be greater than 0", "-fx-text-fill: red;");
            return false;
        }
        updateStatusLabel(label, "", "-fx-text-fill: green;");
        return true;
    }

    /**
     * Validates all input fields and choice boxes.
     * @return true if all inputs are valid, false otherwise
     */
    private boolean validateInputs() {
        return validateField(fieldTitleAddDoc.getText().trim(), titleValidLabel, ValidationLabelUtil.ValidationType.EMPTY) &&
                validateField(fieldAuthorAddDoc.getText().trim(), authorValidLabel, ValidationLabelUtil.ValidationType.EMPTY) &&
                validateChoiceBoxSelection(choiceMaterialAddDoc.getValue(), materialValidLabel) &&
                validateChoiceBoxSelection(choiceCategoryAddDoc.getValue(), categoryValidLabel) &&
                validateQuantity(spinerQuantityAddDoc.getValue(), isbnValidLabel);
    }

    /**
     * Validates a specific field based on the given validation type.
     * @param field          the input value to validate
     * @param label          the label to display validation messages
     * @param validationType the type of validation to perform
     * @return true if valid, false otherwise
     */
    private boolean validateField(String field, Label label, ValidationLabelUtil.ValidationType validationType) {
        String errorMessage = validationUtil.validateField(field, validationType);
        updateStatusLabel(label, errorMessage, errorMessage.isEmpty() ? "-fx-text-fill: green;" : "-fx-text-fill: red;");
        return errorMessage.isEmpty();
    }

    /**
     * Updates the status label with a message and style.
     * @param label   the label to update
     * @param message the message to display
     * @param style   the style to apply
     */
    private void updateStatusLabel(Label label, String message, String style) {
        label.setText(message);
        label.setStyle(style);
    }

    /**
     * Clears all input fields and resets validation labels.
     */
    private void clearFields() {
        choiceMaterialAddDoc.getSelectionModel().clearSelection();
        choiceCategoryAddDoc.getSelectionModel().clearSelection();
        fieldTitleAddDoc.clear();
        fieldAuthorAddDoc.clear();
        fieldISBN.clear();
        isbnValidLabel.setText("");
        isbnHbox.setVisible(false);
        spinerQuantityAddDoc.getValueFactory().setValue(1);
    }
}