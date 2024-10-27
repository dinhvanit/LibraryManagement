package uet.librarymanagementsystem;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.stage.Stage;
import uet.librarymanagementsystem.entity.Page;
import uet.librarymanagementsystem.util.WindowUtil;


public class LMSApplication extends Application {


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
