package uet.librarymanagementsystem.services.EmailServices;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import uet.librarymanagementsystem.DatabaseOperation.DatabaseManager;
import uet.librarymanagementsystem.entity.documents.Document;
import uet.librarymanagementsystem.entity.documents.DocumentFactory;
import uet.librarymanagementsystem.entity.transactions.Transaction;
import uet.librarymanagementsystem.entity.users.Student;

public class OverDateTransactionService {
    private Connection conn;

    public OverDateTransactionService() {
        this.conn = DatabaseManager.connect(); // Kết nối CSDL khi khởi tạo
    }

    public ObservableList<Transaction> getOverdueTransactions() {
        ObservableList<Transaction> overdueTransactions = FXCollections.observableArrayList();
        String query = """
                SELECT * FROM TransactionDocument
                WHERE (return_date > due_date OR (return_date IS NULL AND due_date < ?))
                """;

        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            // Đặt ngày hiện tại cho tham số query
            pstmt.setDate(1, java.sql.Date.valueOf(LocalDate.now()));

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    String idTransaction = rs.getString("id_transaction");
                    String idStudent = rs.getString("id_student");
                    String nameStudent = rs.getString("name_student");
                    String dateOfBirth = rs.getString("date_of_birth");
                    String phoneNumber = rs.getString("phone_number");
                    String email = rs.getString("email");
                    String idDocument = rs.getString("id_document");
                    String titleDocument = rs.getString("title_document");
                    String author = rs.getString("author");
                    String material = rs.getString("material");
                    String category = rs.getString("category");
                    String isbn = rs.getString("isbn");
                    String borrowDate = rs.getString("borrow_date");
                    String returnDate = rs.getString("return_date");
                    String dueDate = rs.getString("due_date");

                    // Tạo đối tượng Student
                    Student student = new Student(idStudent, nameStudent, dateOfBirth, phoneNumber, email, null);

                    // Tạo đối tượng Document
                    Document document = DocumentFactory.createDocument(idDocument, titleDocument, author, material, category, isbn);

                    // Tạo đối tượng Transaction
                    Transaction transaction = new Transaction(idTransaction, document, student, borrowDate, returnDate, dueDate, null, null, null);

                    // Thêm giao dịch quá hạn vào danh sách
                    overdueTransactions.add(transaction);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error fetching overdue transactions: " + e.getMessage());
        }

        return overdueTransactions;
    }

    public void closeConnection() {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (SQLException e) {
            System.err.println("Error closing database connection: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        OverDateTransactionService overdueService = new OverDateTransactionService();

        try {
            ObservableList<Transaction> overdueTransactions = overdueService.getOverdueTransactions();
            OverdueEmailNotificationService.notifyOverdueUsers();
        } finally {
            overdueService.closeConnection();
        }
    }
}
