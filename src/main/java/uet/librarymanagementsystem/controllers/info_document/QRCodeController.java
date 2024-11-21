package uet.librarymanagementsystem.controllers.info_document;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import uet.librarymanagementsystem.entity.documents.ImagesOfLibrary;
import uet.librarymanagementsystem.entity.transactions.Transaction;
import uet.librarymanagementsystem.services.qrServices.QRCodeGeneratorService;
import uet.librarymanagementsystem.services.shareDataServers.ShareDataService;

import java.io.File;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class QRCodeController implements Initializable {

    @FXML
    private Label idTransactionLabel;

    @FXML
    private Label nameStudentLabel;

    @FXML
    private ImageView qrCodeImage;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            // Lấy lại thông tin giao dịch mới nhất
            Transaction transaction = ShareDataService.getTransactionShare();

            // Tạo lại QR Code mới
            QRCodeGeneratorService.generateQRCode(transaction);

            // Kiểm tra sự tồn tại của file QR code
            File qrCodeFile = new File(ImagesOfLibrary.QRCODEWIRTE.getPath());
            if (qrCodeFile.exists()) {
                // Đọc ảnh QR code từ file và hiển thị
                Image image = new Image("file:" + qrCodeFile.getAbsolutePath());
                qrCodeImage.setImage(image);
            } else {
                System.out.println("QR Code file not found.");
            }

            // Cập nhật lại thông tin giao dịch trong giao diện người dùng
            idTransactionLabel.setText(transaction.getId());
            nameStudentLabel.setText(transaction.getStudent().getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
