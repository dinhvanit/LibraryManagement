package uet.librarymanagementsystem.services.advancedServices;

import javafx.collections.ObservableList;
import uet.librarymanagementsystem.entity.transactions.Transaction;
import uet.librarymanagementsystem.services.transactionServices.OverDateTransactionService;

public class OverdueEmailNotificationService {

    public static void notifyOverdueUsers() {
        OverDateTransactionService overdueService = new OverDateTransactionService();
        try {

            ObservableList<Transaction> overdueTransactions = overdueService.getOverdueTransactions();

            for (Transaction transaction : overdueTransactions) {
                String userEmail = transaction.getStudent().getEmail();
                String bookTitle = transaction.getDocument().getTitle();
                String dueDate = transaction.getDueDate();

                String subject = "Thông báo quá hạn trả sách";
                String body = "Xin chào " + transaction.getStudent().getName() + ",\n\n" +
                        "Cuốn sách \"" + bookTitle + "\" mà bạn mượn đã quá hạn trả vào ngày " + dueDate + ".\n" +
                        "Vui lòng trả sách sớm nhất có thể để tránh bị phạt.\n\n" +
                        "Trân trọng,\nĐây là tin nhắn tự động từ thư viện, vui lòng không phản hồi !";

                // Gửi email
                EmailService.sendEmail(userEmail, subject, body);
            }

            System.out.println("Email notifications sent for overdue transactions.");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            overdueService.closeConnection();
        }
    }

    public static void main(String[] args) {
        notifyOverdueUsers();
    }
}
