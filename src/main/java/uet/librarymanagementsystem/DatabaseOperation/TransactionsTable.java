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
            String insertTransactionSQL = "INSERT INTO TransactionDocument (id_document, title_document, id_student, type, date) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement transactionStmt = conn.prepareStatement(insertTransactionSQL);
            transactionStmt.setString(1, transaction.getDocument().getId());
            transactionStmt.setString(2, transaction.getDocument().getTitle());
            transactionStmt.setString(3, transaction.getStudent().getId());
            transactionStmt.setString(4, transaction.getTypeTransaction());
            transactionStmt.setString(5, transaction.getDateTransaction());

            transactionStmt.executeUpdate();

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
        String id = "23020714";
        String name = "Nguyen Dinh Van";
        String birthday = "03-11-2005";
        String phone = "123456789";
        String email = "nguyendinhvanefto@gmail.com";
        Student student = new Student(id, name, birthday, phone, email, id);

        Book book = new Book("0101000200310001", "Hungary 56", "Andy Anderson", Book.BookCategory.FICTION);

        Transaction transaction = new Transaction(book, student, Transaction.TypeTransaction.BORROW, "11-12-2025");
        insertTransaction(transaction);


    }
}
