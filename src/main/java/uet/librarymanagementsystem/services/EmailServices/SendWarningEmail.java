package uet.librarymanagementsystem.services.EmailServices;

import javafx.collections.ObservableList;
import uet.librarymanagementsystem.entity.transactions.Transaction;

public class SendWarningEmail {

    public static void notifyOverdueUsers() {
        CheckOverDateTrans overdueService = new CheckOverDateTrans();
        try {

            ObservableList<Transaction> overdueTransactions = overdueService.getOverdueTransactions();

            for (Transaction transaction : overdueTransactions) {
                String userEmail = transaction.getStudent().getEmail();
                String bookTitle = transaction.getDocument().getTitle();
                String bookID = transaction.getDocument().getId();
                String dueDate = transaction.getDueDate();

                String subject = "Thông báo quá hạn trả sách";
                String body = String.format(
                        "Xin chào %s,\n\n" +
                                "Cuốn sách \"%s\" (mã ID: %s) mà bạn mượn đã quá hạn trả vào ngày %s.\n" +
                                "Vui lòng trả sách sớm nhất có thể để tránh bị phạt.\n\n" +
                                "Trân trọng,\n" +
                                "Đây là tin nhắn tự động từ thư viện, vui lòng không phản hồi!",
                        transaction.getStudent().getName(), bookTitle, bookID, dueDate
                );


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
}
