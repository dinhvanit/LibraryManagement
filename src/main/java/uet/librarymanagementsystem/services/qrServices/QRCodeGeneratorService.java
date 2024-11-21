package uet.librarymanagementsystem.services.qrServices;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import uet.librarymanagementsystem.entity.documents.ImagesOfLibrary;
import uet.librarymanagementsystem.entity.transactions.Transaction;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static java.lang.Thread.sleep;

public class QRCodeGeneratorService {
    public static void generateQRCode (Transaction transaction) throws InterruptedException {
        LocalDateTime now = LocalDateTime.now();

        // Định dạng thời gian theo mẫu: yyyy-MM-dd HH:mm:ss
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDate = now.format(formatter);

        StringBuilder transactionInfo = new StringBuilder();
        transactionInfo.append("QR code creation date: ").append(formattedDate).append("\n");
        transactionInfo.append("Id transaction: ").append(transaction.getId()).append("\n");
        transactionInfo.append("Id student: ").append(transaction.getStudent().getId()).append("\n");
        transactionInfo.append("Name: ").append(transaction.getStudent().getName()).append("\n");
        transactionInfo.append("Id document: ").append(transaction.getDocument().getId()).append("\n");
        transactionInfo.append("Title document: ").append(transaction.getDocument().getTitle()).append("\n");
        transactionInfo.append("Borrowing date: ").append(transaction.getBorrowDate()).append("\n");
        if (transaction.getReturnDate() == null) {
            transactionInfo.append("Return date: ").append("Not yet returned").append("\n");
        } else {
            transactionInfo.append("Return date: ").append(transaction.getReturnDate()).append("\n");
        }
        transactionInfo.append("Due date: ").append(transaction.getDueDate()).append("\n");

        int width = 300; // Chiều rộng của mã QR
        int height = 300; // Chiều cao của mã QR

        try {
            // Tạo QR Code
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = qrCodeWriter.encode(transactionInfo.toString(), BarcodeFormat.QR_CODE, width, height);

            // Lưu QR Code vào file
            Path path = FileSystems.getDefault().getPath(ImagesOfLibrary.QRCODEWIRTE.getPath());
            MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);

            System.out.println("QR Code created successfully! Saved to " + ImagesOfLibrary.QRCODEWIRTE.getPath());
            System.out.println(transactionInfo);
        } catch (WriterException | IOException e) {
            e.printStackTrace();
        }
    }
}