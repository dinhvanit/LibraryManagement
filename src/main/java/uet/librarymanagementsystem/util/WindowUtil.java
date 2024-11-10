package uet.librarymanagementsystem.util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import uet.librarymanagementsystem.entity.Page;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import static java.util.Objects.requireNonNull;

public class WindowUtil {
    private static Stage stage;
    private static final Map<Page, Parent> loadedParents = new HashMap<>();

    public static void setStage(Stage primaryStage) {
        stage = primaryStage;
    }

    public static void setPage(Page page, String title) {
        try {
            // Tải FXML chỉ khi cần thiết
            Parent parent = loadedParents.computeIfAbsent(page, p -> {
                try {
                    return FXMLLoader.load(WindowUtil.class.getResource(p.getFXMLPath()));
                } catch (IOException e) {
                    e.printStackTrace();
                    throw new RuntimeException("Unable to load FXML for page: " + page, e);
                }
            });

            if (stage != null) {
                // Đặt Scene và tiêu đề vào Stage
                Scene scene = new Scene(parent);
                stage.setScene(scene);
                stage.setTitle(title);
                stage.sizeToScene();
                stage.centerOnScreen();
                stage.show();
            }
        } catch (RuntimeException e) {
            System.out.println("Error setting page: " + e.getMessage());
        }
    }

    public static void loadCenterPane(Page page, BorderPane borderPane) {
        try {
            Parent view = FXMLLoader.load(WindowUtil.class.getResource(page.getFXMLPath()));
            borderPane.setCenter(view);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void logoutSession() {
        // Xóa bộ nhớ các trang đã tải và quay lại trang đăng nhập
        loadedParents.clear();
        setPage(Page.LOGIN, "Library Management System");
    }
}
