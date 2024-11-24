package uet.librarymanagementsystem;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import uet.librarymanagementsystem.entity.Page;
import uet.librarymanagementsystem.services.TaskService;
import uet.librarymanagementsystem.util.WindowUtil;

public class LMSApplication extends Application {

    public static void main(String[] args) {
        // Đăng ký hook để đảm bảo TaskService được tắt khi ứng dụng thoát
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Shutting down TaskService...");
            TaskService.getInstance().shutdown(); // Đóng TaskService qua Singleton
        }));

        // Khởi chạy JavaFX Application
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            // Cấu hình cửa sổ chính
            WindowUtil.setStage(primaryStage);

            // Chạy một tác vụ nền để thực hiện các thiết lập ban đầu
            TaskService.getInstance().runTask(() -> {
                System.out.println("Performing initial setup task in background...");
                try {
                    Thread.sleep(2000); // Mô phỏng thời gian thiết lập
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    System.err.println("Setup task interrupted: " + e.getMessage());
                }
                System.out.println("Setup completed!");
            });

            // Load trang khởi đầu
            WindowUtil.setPage(Page.LOGIN, "Library Management System");

            // Hiển thị cửa sổ chính
            primaryStage.show();

            // Đăng ký hành động thoát ứng dụng
            primaryStage.setOnCloseRequest(event -> {
                System.out.println("Application is closing...");
                Platform.exit(); // Dừng ứng dụng JavaFX
            });

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Can't run application. Error: " + e.getMessage());
        }
    }

    // Phương thức truy xuất TaskService toàn cục
    public static TaskService getTaskService() {
        return TaskService.getInstance(); // Truy xuất từ Singleton
    }
}
