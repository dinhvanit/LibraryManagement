package uet.librarymanagementsystem.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import uet.librarymanagementsystem.util.WindowUtil;

import java.io.IOException;

public class StudentPageController {


    @FXML
    private BorderPane borderPaneStudentPage;

    @FXML
    void clickBorrowDocuments(MouseEvent event) throws IOException {
        try {
            AnchorPane view = FXMLLoader.load(getClass().getResource("/uet/librarymanagementsystem/fxml/student/search_borrow_document.fxml"));
            borderPaneStudentPage.setCenter(view);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("borrow");
    }

    @FXML
    void clickLogOut(MouseEvent event) {
        WindowUtil.logoutSession();
    }

    @FXML
    void clickReturnDocuments(MouseEvent event) {
        System.out.println("return");
    }

    @FXML
    void clickViewListOfBorrowDocuments(MouseEvent event) {
        System.out.println("view");
    }

}
