package uet.librarymanagementsystem.controllers.notification;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * The {@code SuccessNotificationController} class handles the functionality of closing the success
 * notification window when the user clicks the 'OK' button.
 */
public class SuccessNotificationController {

    /**
     * Closes the success notification window when the user clicks the 'OK' button.
     *
     * @param event The MouseEvent triggered when the 'OK' button is clicked.
     */
    @FXML
    void okCloseClick(MouseEvent event) {
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
}
