package uet.librarymanagementsystem.services.documentServices;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import uet.librarymanagementsystem.DatabaseOperation.DatabaseManager;
import uet.librarymanagementsystem.controllers.LoginController;
import uet.librarymanagementsystem.entity.transactions.Transaction;
import uet.librarymanagementsystem.services.transactionServices.SearchTransactionService;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.*;

public class AddBorrowDocumentService {
    public Connection getConn() {
        return conn;
    }

    private Connection conn;

    public AddBorrowDocumentService() {
        this.conn = DatabaseManager.connect();
    }

    public static ObservableList<Transaction> addBorrowDocument() throws SQLException {
        ExecutorService executor = Executors.newSingleThreadExecutor(); // Một luồng riêng để xử lý
        Future<ObservableList<Transaction>> future = executor.submit(() -> {
            String id_student = LoginController.getIdCurrentStudent();
            SearchTransactionService searchTransactionService = new SearchTransactionService();
            return searchTransactionService.searchTransactionByIdStudentBorrowing(id_student);
        });

        ObservableList<Transaction> result = FXCollections.observableArrayList();
        try {
            result = future.get(5, TimeUnit.SECONDS); // Đợi tối đa 10 giây để xử lý hoàn tất
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            e.printStackTrace();
        } finally {
            executor.shutdown();
        }
        return result;
    }

    public static void main(String[] args) throws SQLException {
        AddBorrowDocumentService addBorrowDocumentService = new AddBorrowDocumentService();
        addBorrowDocument();
    }

}
