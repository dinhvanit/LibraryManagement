package uet.librarymanagementsystem.util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
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

    public static void initParents() throws IOException {
        loadedParents.put(Page.LOGIN, FXMLLoader.load(WindowUtil.class.getResource("/uet/librarymanagementsystem/fxml/login.fxml")));
        loadedParents.put(Page.ADMIN, FXMLLoader.load(WindowUtil.class.getResource("/uet/librarymanagementsystem/fxml/adminPage.fxml")));
        // tạo xong gì thì thêm enum page và path fxml vào map
    }

    public static void setPage(Page page, String title) {
        Parent parent = loadedParents.get(page);
        if (parent != null && stage != null) {
            // Kiểm tra xem Parent đã có Scene chưa
            Scene scene = parent.getScene();
            if (scene == null) {
                // Nếu không có Scene, tạo Scene mới
                scene = new Scene(parent);
            }
            // Đặt Scene vào Stage
            stage.setScene(scene);
            stage.setTitle(title);
            stage.sizeToScene();
            stage.centerOnScreen();
            stage.show();
        } else {
            System.out.println("Error setting page: Page not found or Stage is null.");
        }
    }

    public static void logoutSession() {

        setPage(Page.LOGIN, "Library Management System");
    }
}
