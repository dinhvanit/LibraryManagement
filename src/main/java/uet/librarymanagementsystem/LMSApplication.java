package uet.librarymanagementsystem;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import uet.librarymanagementsystem.entity.Page;
import uet.librarymanagementsystem.util.WindowUtil;

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
            // Set the Stage in WindowUtil
            WindowUtil.setStage(primaryStage);

            // Initialize the pages
            WindowUtil.initParents();

            // Load the initial page
            WindowUtil.setPage(Page.LOGIN, "Library Management System");

            primaryStage.show();

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Can't run application. Error: " + e.getMessage());
        }
    }
}
