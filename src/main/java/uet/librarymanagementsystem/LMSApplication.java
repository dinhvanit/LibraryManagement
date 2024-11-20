package uet.librarymanagementsystem;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.stage.Stage;
import uet.librarymanagementsystem.entity.Page;
import uet.librarymanagementsystem.services.TaskService;
import uet.librarymanagementsystem.util.WindowUtil;

public class LMSApplication extends Application {

    // Khởi tạo TaskService toàn cục
    private static TaskService taskService;

    public static void main(String[] args) {
        taskService = new TaskService();


        launch(args);


        taskService.shutdown();
    }

    @Override
    public void start(Stage primaryStage) {
        try {

            WindowUtil.setStage(primaryStage);

            taskService.runTask(() -> {
                System.out.println("Performing initial setup task in background...");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Setup completed!");
            });

            // Load trang khởi đầu
            WindowUtil.setPage(Page.LOGIN, "Library Management System");

            primaryStage.show();

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Can't run application. Error: " + e.getMessage());
        }
    }

    public static TaskService getTaskService() {
        return taskService;
    }
}
