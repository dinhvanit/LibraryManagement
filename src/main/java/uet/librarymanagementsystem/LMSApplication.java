package uet.librarymanagementsystem;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import uet.librarymanagementsystem.entity.Page;
import uet.librarymanagementsystem.services.TaskService;
import uet.librarymanagementsystem.util.WindowUtil;

public class LMSApplication extends Application {

    /**
     * Main method to launch the JavaFX application.
     * Registers a shutdown hook to ensure the TaskService is properly shut down when the application exits.
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        // Register a shutdown hook to ensure TaskService is shut down when the application exits
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Shutting down TaskService...");
            TaskService.getInstance().shutdown(); // Shut down TaskService via Singleton
        }));

        // Launch the JavaFX application
        launch(args);
    }

    /**
     * This method is called when the JavaFX application is started.
     * It sets up the primary stage, runs background tasks, and loads the initial page.
     *
     * @param primaryStage the main window (stage) of the application
     */
    @Override
    public void start(Stage primaryStage) {
        try {
            // Configure the main window
            WindowUtil.setStage(primaryStage);

            // Run a background task to perform initial setup
            TaskService.getInstance().runTask(() -> {
                System.out.println("Performing initial setup task in background...");
                try {
                    Thread.sleep(2000); // Simulate setup time
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    System.err.println("Setup task interrupted: " + e.getMessage());
                }
                System.out.println("Setup completed!");
            });

            // Load the initial login page
            WindowUtil.setPage(Page.LOGIN, "Library Management System");

            // Display the main window
            primaryStage.show();

            // Register the exit action for the application
            primaryStage.setOnCloseRequest(event -> {
                System.out.println("Application is closing...");
                Platform.exit(); // Exit the JavaFX application
            });

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Can't run application. Error: " + e.getMessage());
        }
    }

    /**
     * Provides access to the global TaskService instance.
     *
     * @return the TaskService instance
     */
    public static TaskService getTaskService() {
        return TaskService.getInstance(); // Access via Singleton
    }
}
