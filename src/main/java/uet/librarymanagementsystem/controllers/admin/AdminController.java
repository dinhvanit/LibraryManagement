package uet.librarymanagementsystem.controllers.admin;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import uet.librarymanagementsystem.util.WindowUtil;

import java.io.IOException;

public class AdminController {

    @FXML
    private BorderPane borderPaneAdminPage;

    @FXML
    void clickManageStudents(MouseEvent event) throws IOException {
        try {
            AnchorPane view = FXMLLoader.load(getClass().getResource("/uet/librarymanagementsystem/fxml/admin/addStudent.fxml"));
            borderPaneAdminPage.setCenter(view);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("manage Students");
    }

    @FXML
    void clickManageDocuments(MouseEvent event) {
        try {
            AnchorPane view = FXMLLoader.load(getClass().getResource("/uet/librarymanagementsystem/fxml/admin/add_document.fxml"));
            borderPaneAdminPage.setCenter(view);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("manage Documents");
    }

    @FXML
    void clickListOfStudents(MouseEvent event) {
        System.out.println("view");
    }

    @FXML
    void clickListOfDocuments(MouseEvent event) {
        System.out.println("view");
    }

    @FXML
    void clickLogOut(MouseEvent event) {
        WindowUtil.logoutSession();
    }


}
