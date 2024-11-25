package uet.librarymanagementsystem.services.EmailServices;

import javafx.collections.ObservableList;
import uet.librarymanagementsystem.entity.transactions.Transaction;
import uet.librarymanagementsystem.services.TaskService;

public class SendWarningEmail {

    private static final TaskService taskService = TaskService.getInstance();

    public static void notifyOverdueUsers() {
        CheckOverDateTrans overdueService = new CheckOverDateTrans();

        try {
            ObservableList<Transaction> overdueTransactions = overdueService.getOverdueTransactions();

            for (Transaction transaction : overdueTransactions) {
                taskService.runTask(() -> {
                    try {
                        String userEmail = transaction.getStudent().getEmail();
                        String bookTitle = transaction.getDocument().getTitle();
                        String bookID = transaction.getDocument().getId();
                        String dueDate = transaction.getDueDate();

                        String subject = "Nhắc thời hạn trả sách";
                        String body = String.format(
                                "Xin chào %s,\n\n" +
                                        "Cuốn sách \"%s\" (mã ID: %s) mà bạn mượn sẽ quá hạn trả vào ngày %s.\n" +
                                        "Vui lòng trả sách sớm nhất có thể để tránh bị phạt.\n\n" +
                                        "Trân trọng,\n" +
                                        "Đây là tin nhắn tự động từ thư viện, vui lòng không phản hồi!",
                                transaction.getStudent().getName(), bookTitle, bookID, dueDate
                        );

                        EmailService.sendEmail(userEmail, subject, body);
                        System.out.println("Email sent to: " + userEmail);

                    } catch (Exception e) {
                        System.err.println("Error sending email: " + e.getMessage());
                        e.printStackTrace();
                    }
                });
            }

            System.out.println("All email notifications are being processed in background.");
        } catch (Exception e) {
            System.err.println("Error fetching overdue transactions: " + e.getMessage());
            e.printStackTrace();
        } finally {
            overdueService.closeConnection();
        }
    }
}
