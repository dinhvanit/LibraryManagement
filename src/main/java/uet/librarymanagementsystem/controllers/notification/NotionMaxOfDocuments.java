package uet.librarymanagementsystem.controllers.notification;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * The {@code NotionMaxOfDocuments} class handles the functionality of closing a notification window
 * when the user clicks on a close button.
 */
public class NotionMaxOfDocuments {

    /**
     * Closes the notification window when the user clicks on the close button.
     *
     * @param event The MouseEvent triggered when the close button is clicked.
     */
    @FXML
    void closeNotionClick(MouseEvent event) {
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
}