package uet.librarymanagementsystem;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

import static java.util.Objects.requireNonNull;

public class LMSApplication extends Application {

    private void initParents() throws IOException {
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            Parent loginPage = FXMLLoader.load(getClass().getResource("fxml/login.fxml"));
            loginPage.setId("login_window");
            Scene scene = new Scene(loginPage);
            primaryStage.setScene(scene);
            primaryStage.show();
        }catch (Exception e){
            System.out.println("Can't run application. Error : " + e.getMessage());
        }
    }
}
