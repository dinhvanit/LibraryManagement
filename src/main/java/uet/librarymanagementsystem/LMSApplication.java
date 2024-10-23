package uet.librarymanagementsystem;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.List;

import static java.util.Objects.requireNonNull;

public class LMSApplication extends Application {

    private void initParents() throws IOException {
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static Parent currentParent;

    @Override
    public void start(Stage primaryStage) {
        try {
            currentParent = FXMLLoader.load(getClass().getResource("fxml/add_document.fxml"));
            Scene scene = new Scene(currentParent);

            // primaryStage.initStyle(StageStyle.UNDECORATED);

            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            System.out.println("Can't run application. Error: " + e.getMessage());
        }
    }
}
