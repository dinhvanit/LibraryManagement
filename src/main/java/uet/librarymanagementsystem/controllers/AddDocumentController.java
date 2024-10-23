package uet.librarymanagementsystem.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class AddDocumentController implements Initializable {

    @FXML
    private HBox HBoxOfButton;

    @FXML
    private VBox VBoxOfTextField;

    @FXML
    private TextField author;

    @FXML
    private TextField documentId;

    @FXML
    private TextField documentName;

    @FXML
    private TextField documentTitle;

    @FXML
    private TextField quantity;

    @FXML
    void addOfAddBook(MouseEvent event) {

    }

    @FXML
    void cancelOfAddBook(MouseEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
