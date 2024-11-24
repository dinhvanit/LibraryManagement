package uet.librarymanagementsystem.services.documentServices;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import uet.librarymanagementsystem.DatabaseOperation.DatabaseManager;
import uet.librarymanagementsystem.controllers.LoginController;
import uet.librarymanagementsystem.entity.transactions.Transaction;
import uet.librarymanagementsystem.services.TaskService;
import uet.librarymanagementsystem.services.transactionServices.SearchTransactionService;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import uet.librarymanagementsystem.LMSApplication;

public class AddBorrowDocumentService {
    private final Connection conn;
    private final TaskService taskService;

    public AddBorrowDocumentService() {
        this.conn = DatabaseManager.connect();
        this.taskService = LMSApplication.getTaskService();
    }

    public static ObservableList<Transaction> addBorrowDocument() {
        // Truy cập TaskService từ LMSApplication
        TaskService taskService = LMSApplication.getTaskService();

        Future<ObservableList<Transaction>> future = taskService.runTask(() -> {
            String id_student = LoginController.getIdCurrentStudent();
            SearchTransactionService searchTransactionService = new SearchTransactionService();
            return searchTransactionService.searchTransactionByIdStudentBorrowing(id_student);
        });

        ObservableList<Transaction> result = FXCollections.observableArrayList();
        try {
            result = future.get(5, TimeUnit.SECONDS); // Đợi tối đa 5 giây để lấy kết quả
        } catch (InterruptedException e) {
            System.err.println("Task interrupted: " + e.getMessage());
        } catch (ExecutionException e) {
            System.err.println("Task execution failed: " + e.getMessage());
        } catch (TimeoutException e) {
            System.err.println("Task timed out: " + e.getMessage());
        }
        return result;
    }
}
