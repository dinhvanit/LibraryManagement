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
import java.util.ResourceBundle;

public class QRCodeController implements Initializable {

    @FXML
    private Label idTransactionLabel;

    @FXML
    private Label nameStudentLabel;

    @FXML
    private ImageView qrCodeImage;

    /**
     * Initializes the QR code page by fetching the latest transaction,
     * generating a new QR code, and displaying it along with the
     * transaction details such as transaction ID and student name.
     *
     * @param url The location used to resolve relative paths for the root object.
     * @param resourceBundle The resources used to localize the root object.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            // Retrieve the latest transaction information
            Transaction transaction = ShareDataService.getTransactionShare();

            // Generate a new QR code for the transaction
            QRCodeGeneratorService.generateQRCode(transaction);

            // Check if the QR code file exists
            File qrCodeFile = new File(ImagesOfLibrary.QRCODEWIRTE.getPath());
            if (qrCodeFile.exists()) {
                // Read the QR code image from the file and display it
                Image image = new Image("file:" + qrCodeFile.getAbsolutePath());
                qrCodeImage.setImage(image);
            } else {
                // Log message if QR code file is not found
                System.out.println("QR Code file not found.");
            }

            // Update the UI with transaction details
            idTransactionLabel.setText(transaction.getId());
            nameStudentLabel.setText(transaction.getStudent().getName());
        } catch (Exception e) {
            // Print any exception stack trace for debugging purposes
            e.printStackTrace();
        }
    }
}
