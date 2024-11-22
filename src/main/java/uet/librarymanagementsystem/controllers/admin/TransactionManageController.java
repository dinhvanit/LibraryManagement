package uet.librarymanagementsystem.controllers.admin;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import uet.librarymanagementsystem.controllers.LoginController;
import uet.librarymanagementsystem.entity.Page;
import uet.librarymanagementsystem.entity.documents.Document;
import uet.librarymanagementsystem.services.shareDataServers.ShareDataService;
import uet.librarymanagementsystem.services.transactionServices.SearchTransactionService;
import uet.librarymanagementsystem.entity.transactions.Transaction;
import uet.librarymanagementsystem.util.WindowUtil;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class TransactionManageController implements Initializable {

    private ObservableList<Transaction> transactionManageList;

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
    private TextField idManageDocumentTextField;

    @FXML
    private TextField idManageStudentTextField;

    @FXML
    private TableColumn<Transaction, String> idStudentManageColumnTransaction;

    @FXML
    private TableColumn<Transaction, String> idTransactionManageColumnTransaction;

    @FXML
    private TableColumn<Transaction, String> materialManageColumnTransaction;

    @FXML
    private TableColumn<Transaction, String> ratingManageColumnTransaction;

    @FXML
    private TableColumn<Transaction, String> returnDateManageColumnTransaction;

    @FXML
    private TableColumn<Transaction, String> reviewDateManageColumnTransaction;

    @FXML
    private TableColumn<Transaction, String> reviewManageColumnTransaction;

    @FXML
    private TableColumn<Transaction, String> titleManageColumnTransaction;

    @FXML
    private TableColumn<Transaction, String> nameStudentManageColumnTransaction;

    @FXML
    private TableView<Transaction> transactionManageTableView;

    @FXML
    private Label notionChoiceTransactionLabel;

    @FXML
    void infoDocumentManageButtonOnClick(MouseEvent event) {
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

    @FXML
    void searchTransactionManageButtonOnClick(MouseEvent event) {
        // Lấy giá trị từ các trường nhập liệu
        String idStudent = idManageStudentTextField.getText().trim();
        String idDocument = idManageDocumentTextField.getText().trim();

        // Tạo service để tìm kiếm giao dịch
        SearchTransactionService searchTransactionService = new SearchTransactionService();
        ObservableList<Transaction> transactions;

        try {if (!idStudent.isEmpty()) {
                // Tìm kiếm theo ID STUDENT
                transactions = searchTransactionService.searchTransactionByIdStudent(idStudent);
            } else if (!idDocument.isEmpty()) {
                // Tìm kiếm theo ID DOCUMENT
                transactions = searchTransactionService.searchTransactionByIdDocument(idDocument);
            } else {
                // Nếu không nhập gì thì lấy tất cả giao dịch
                transactions = searchTransactionService.getAllTransactions();
            }

            // Cập nhật bảng với dữ liệu tìm được
            transactionManageTableView.setItems(transactions);

            // Hiển thị số lượng giao dịch (debug)
            System.out.println("Số lượng giao dịch tìm được: " + transactions.size());

        } catch (SQLException e) {
            // Xử lý lỗi kết nối hoặc truy vấn
            e.printStackTrace();
            System.out.println("Lỗi khi tìm kiếm giao dịch: " + e.getMessage());
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        SearchTransactionService searchTransactionService = new SearchTransactionService();

        // Cấu hình các cột của bảng
        idTransactionManageColumnTransaction.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getId()));
        idStudentManageColumnTransaction.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getStudent().getId())); // Lấy ID sinh viên
        nameStudentManageColumnTransaction.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getStudent().getName())); // Lấy tên sinh viên
        idDocumentManageColumnTransaction.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDocument().getId()));
        titleManageColumnTransaction.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDocument().getTitle()));
        authorManageColumnTransaction.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDocument().getAuthor()));
        materialManageColumnTransaction.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDocument().getMaterial()));
        categoryManageColumnTransaction.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDocument().getCategory()));
        borrowDateManageColumnTransaction.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getBorrowDate()));
        returnDateManageColumnTransaction.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getReturnDate()));
        dueDateManageColumnTransaction.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDueDate()));
        reviewDateManageColumnTransaction.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getReviewDate()));
        reviewManageColumnTransaction.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getReview()));
        ratingManageColumnTransaction.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getRating()));

        // Khởi tạo danh sách giao dịch
        transactionManageList = FXCollections.observableArrayList();

        try {
            // Lấy toàn bộ giao dịch từ cơ sở dữ liệu
            transactionManageList = searchTransactionService.getAllTransactions();

            // Hiển thị số lượng giao dịch (dành cho debug)
            System.out.println("Tổng số giao dịch: " + transactionManageList.size());
        } catch (SQLException e) {
            // Xử lý lỗi khi kết nối cơ sở dữ liệu
            System.out.println("Lỗi khi tải dữ liệu giao dịch: " + e.getMessage());
            throw new RuntimeException(e);
        }

        // Gắn danh sách giao dịch vào bảng
        transactionManageTableView.setItems(transactionManageList);
    }
}
