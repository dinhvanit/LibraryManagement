package uet.librarymanagementsystem.controllers.admin;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import uet.librarymanagementsystem.entity.Page;
import uet.librarymanagementsystem.entity.documents.Document;
import uet.librarymanagementsystem.entity.transactions.Transaction;
import uet.librarymanagementsystem.entity.users.Student;
import uet.librarymanagementsystem.services.EmailServices.CheckOverDateTrans;
import uet.librarymanagementsystem.services.EmailServices.SendWarningEmail;
import uet.librarymanagementsystem.services.shareDataServers.ShareDataService;
import uet.librarymanagementsystem.util.WindowUtil;

import java.io.*;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class GmailNotionController implements Initializable {

    @FXML
    private TableColumn<Transaction, String> authorManageColumnTransaction;

    @FXML
    private TableColumn<Transaction, String> borrowDateManageColumnTransaction;

    @FXML
    private TableColumn<Transaction, String> categoryManageColumnTransaction;

    @FXML
    private TableColumn<Transaction, String> dueDateManageColumnTransaction;

    @FXML
    private TableColumn<Transaction, String> idDocumentManageColumnTransaction;

    @FXML
    private TableColumn<Transaction, String> idStudentManageColumnTransaction;

    @FXML
    private TableColumn<Transaction, String> idTransactionManageColumnTransaction;

    @FXML
    private TableColumn<Transaction, String> materialManageColumnTransaction;

    @FXML
    private TableColumn<Transaction, String> nameStudentManageColumnTransaction;

    @FXML
    private TableColumn<Transaction, String> titleManageColumnTransaction;

    @FXML
    private TableView<Transaction> transactionManageTableView;

    private ObservableList<Transaction> transactionManageList;
    @FXML
    private Label peopleEmailLabel;

    @FXML
    private Label dateEmailLabel;

    @FXML
    private Button sendAllEmailButton;

    @FXML
    private Label notionChoiceTransactionLabel;

    /**
     * Sends notification emails to all users with overdue transactions.
     * @param event the mouse event triggering this action
     */
    @FXML
    void sendAllEmailClick(MouseEvent event) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/main/resources/uet/librarymanagementsystem/fileTxt/date_notion.txt"))) {
            LocalDateTime now = LocalDateTime.now();
            String newTime = now.format(formatter);
            writer.write(newTime); // Write the current date and time to the file.
            dateEmailLabel.setText(newTime);
            System.out.println("Updated notification time: " + newTime);
            SendWarningEmail.notifyOverdueUsers(); // Sends the warning emails.
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            WindowUtil.showSecondaryWindow(Page.NOTION_SUCCESS, "Notion success", currentStage);
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }

    /**
     * Opens a secondary window with document details.
     * @param event the mouse event triggering this action
     */
    @FXML
    void infoDocumentClick(MouseEvent event) {
        if (transactionManageTableView.getSelectionModel().getSelectedItem() == null) {
            notionChoiceTransactionLabel.setVisible(true);
        } else {
            notionChoiceTransactionLabel.setVisible(false);
            Document selectedDocument = transactionManageTableView.getSelectionModel().getSelectedItem().getDocument();
            Transaction selectedTransaction = transactionManageTableView.getSelectionModel().getSelectedItem();
            if (selectedDocument != null && selectedTransaction != null) {
                ShareDataService.setDocumentShare(selectedDocument);
                ShareDataService.setTransactionShare(selectedTransaction);
                Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                WindowUtil.showSecondaryWindowWithShowInfo(
                        Page.SHOW_INFO_DOCUMENT, "Information Document", currentStage, false, false);
            }
        }
    }

    /**
     * Opens a secondary window with student details.
     * @param event the mouse event triggering this action
     */
    @FXML
    void infoStudentClick(MouseEvent event) {
        if (transactionManageTableView.getSelectionModel().getSelectedItem() != null) {
            Student selectedStudent = transactionManageTableView.getSelectionModel().getSelectedItem().getStudent();
            ShareDataService.setIdStudentShare(selectedStudent.getId());
            notionChoiceTransactionLabel.setVisible(true);
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            WindowUtil.showSecondaryWindow(Page.SHOW_INFO, "Information student", currentStage);
            System.out.println("Student added to Students to Delete table.");
        } else {
            System.out.println("Please select a student from the List of Students table.");
            notionChoiceTransactionLabel.setVisible(false);
        }
    }

    /**
     * Initializes the controller.
     * @param url the location used to resolve relative paths for the root object
     * @param resourceBundle the resources used to localize the root object
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        CheckOverDateTrans checkOverDateTrans = new CheckOverDateTrans();

        // Configure table columns
        idTransactionManageColumnTransaction.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getId()));
        idStudentManageColumnTransaction.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getStudent().getId()));
        nameStudentManageColumnTransaction.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getStudent().getName()));
        idDocumentManageColumnTransaction.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDocument().getId()));
        titleManageColumnTransaction.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDocument().getTitle()));
        authorManageColumnTransaction.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDocument().getAuthor()));
        materialManageColumnTransaction.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDocument().getMaterial()));
        categoryManageColumnTransaction.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDocument().getCategory()));
        borrowDateManageColumnTransaction.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getBorrowDate()));
        dueDateManageColumnTransaction.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDueDate()));

        transactionManageList = FXCollections.observableArrayList();
        transactionManageList = checkOverDateTrans.getOverdueTransactions();

        peopleEmailLabel.setText(String.valueOf(transactionManageList.size()));
        transactionManageTableView.setItems(transactionManageList);

        if (transactionManageList.isEmpty()) {
            sendAllEmailButton.setVisible(false);
        } else {
            sendAllEmailButton.setVisible(true);
        }

        try (BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/uet/librarymanagementsystem/fileTxt/date_notion.txt"))) {
            String oldTime = reader.readLine();
            System.out.println("Previous notification time: " + oldTime);
            dateEmailLabel.setText(oldTime);
        } catch (FileNotFoundException e) {
            System.out.println("File not found, creating a new one.");
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }
}
