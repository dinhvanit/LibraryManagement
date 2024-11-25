package uet.librarymanagementsystem.services.documentServices;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import uet.librarymanagementsystem.DatabaseOperation.DatabaseManager;
import uet.librarymanagementsystem.controllers.LoginController;
import uet.librarymanagementsystem.entity.transactions.Transaction;
import uet.librarymanagementsystem.services.TaskService;
import uet.librarymanagementsystem.services.transactionServices.SearchTransactionService;

import java.sql.Connection;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import uet.librarymanagementsystem.LMSApplication;

/**
 * The {@code AddBorrowDocumentService} class provides a service for adding borrow transactions of documents
 * by a student. It uses a background task to retrieve transaction data related to the current student
 * and returns it as an observable list of {@link Transaction}.
 */
public class AddBorrowDocumentService {
    private final Connection conn;
    private final TaskService taskService;

    /**
     * Constructs a new {@code AddBorrowDocumentService} instance.
     * Initializes the connection to the database and the task service from {@link LMSApplication}.
     */
    public AddBorrowDocumentService() {
        this.conn = DatabaseManager.connect();
        this.taskService = LMSApplication.getTaskService();
    }

    /**
     * Adds a borrow document transaction for the current student.
     * This method retrieves the student's transaction records by their ID and
     * returns a list of transactions that are currently borrowed by the student.
     * The search is performed in a background task with a timeout of 5 seconds.
     *
     * @return an observable list of {@link Transaction} representing the borrow documents for the current student.
     * @throws InterruptedException if the thread executing the task is interrupted.
     * @throws ExecutionException if an exception occurs during the execution of the task.
     * @throws TimeoutException if the task execution exceeds the specified timeout period of 5 seconds.
     */
    public static ObservableList<Transaction> addBorrowDocument() {
        // Truy cập TaskService từ LMSApplication
        TaskService taskService = LMSApplication.getTaskService();

        // Run the task in background to retrieve transactions
        Future<ObservableList<Transaction>> future = taskService.runTask(() -> {
            String id_student = LoginController.getIdCurrentStudent();
            SearchTransactionService searchTransactionService = new SearchTransactionService();
            return searchTransactionService.searchTransactionByIdStudentBorrowing(id_student);
        });

        ObservableList<Transaction> result = FXCollections.observableArrayList();
        try {
            // Wait for the result, with a timeout of 5 seconds
            result = future.get(5, TimeUnit.SECONDS);
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
