package uet.librarymanagementsystem.controllers.admin;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import uet.librarymanagementsystem.DatabaseOperation.DocumentDO;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ModifyDocumentController  implements Initializable {

    @FXML
    private TextField authorModifyDocumentTextField;

    @FXML
    private TextField categoryModifyDocumentTextField;

    @FXML
    private TextField materialModifyDocumentTextField;

    @FXML
    private TextField titleModifyDocumentTextField;

    /**
     * Handles the action when the "Save" button is clicked for modifying a document.
     * This method retrieves the modified document information from the text fields and prepares the data for update.
     *
     * @param event the mouse event triggered when the button is clicked
     */
    @FXML
    void saveModifyDocumentButtonOnClick(MouseEvent event) {
        String title = titleModifyDocumentTextField.getText().trim();
        String author = authorModifyDocumentTextField.getText().trim();
        String material = materialModifyDocumentTextField.getText().trim();
        String category = categoryModifyDocumentTextField.getText().trim();
/*
        try {
            //DocumentDO.updateDocument(title, author, material, category);
        } catch (SQLException e) {
            e.printStackTrace();
        }*/
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

}
