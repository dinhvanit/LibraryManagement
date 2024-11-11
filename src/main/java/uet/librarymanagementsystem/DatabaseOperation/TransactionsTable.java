package uet.librarymanagementsystem.DatabaseOperation;

import uet.librarymanagementsystem.entity.documents.Document;
import uet.librarymanagementsystem.entity.documents.materials.Book;
import uet.librarymanagementsystem.entity.transactions.Transaction;
import uet.librarymanagementsystem.entity.users.Student;
import uet.librarymanagementsystem.services.userServices.AddStudentService;

import java.sql.*;

import static uet.librarymanagementsystem.DatabaseOperation.DatabaseManager.connect;

public class TransactionsTable {
    public static void insertTransaction(Transaction transaction) throws SQLException {
        Connection conn = connect();

        if (conn == null || conn.isClosed()) {
            throw new SQLException("Cannot insert transaction, connection is closed or invalid.");
        }

        try {
            String insertTransactionSQL = "INSERT INTO TransactionDocument " +
                    "(id_student, name_student, date_of_birth, phone_number, " +
                    "email, password, id_document, title_document, author, " +
                    "material, category, type, date_transaction, due_date) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement transactionStmt = conn.prepareStatement(insertTransactionSQL);

            transactionStmt.setString(1, transaction.getStudent().getId());
            transactionStmt.setString(2, transaction.getStudent().getName());
            transactionStmt.setString(3, transaction.getStudent().getDateOfBirth());

            transactionStmt.setString(4, transaction.getStudent().getPhoneNumber());
            transactionStmt.setString(5, transaction.getStudent().getEmail());
            transactionStmt.setString(6, transaction.getStudent().getPassword());

            transactionStmt.setString(7, transaction.getDocument().getId());
            transactionStmt.setString(8, transaction.getDocument().getTitle());
            transactionStmt.setString(9, transaction.getDocument().getAuthor());
            transactionStmt.setString(10, transaction.getDocument().getMaterial());
            transactionStmt.setString(11, transaction.getDocument().getCategory());

            transactionStmt.setString(12, transaction.getTypeTransaction());
            transactionStmt.setString(13, transaction.getDateTransaction());
            transactionStmt.setString(14, transaction.getDocument().getDueDate());

            transactionStmt.executeUpdate();

            String generatedIdSQL = "SELECT last_insert_rowid()";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(generatedIdSQL);

            if (rs.next()) {
                String generatedId = rs.getString(1); // Lấy giá trị id tự động sinh ra
                transaction.setId(generatedId);  // Cập nhật ID cho transaction
            }

            System.out.println("Transaction added successfully!");

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Loiiii");
            throw e;
        } finally {
            conn.close();
        }
    }

    public static void main(String[] args) throws SQLException {
        String id = "23020675";
        String name = "Dang Dinh Khang";
        String birthday = "2005-03-27";
        String phone = "123456789";
        String email = "lhang18022005@gmail.com";
        Student student = new Student(id, name, birthday, phone, email, id);

        Book book = new Book("0101000200310001", "Hungary 56", "Andy Anderson", Book.BookCategory.FICTION);

        Transaction transaction = new Transaction(book, student, Transaction.TypeTransaction.BORROW, "11-12-2025");
        insertTransaction(transaction);
        System.out.println(transaction.getId());

    }
}
