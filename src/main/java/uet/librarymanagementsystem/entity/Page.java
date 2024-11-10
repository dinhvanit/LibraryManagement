package uet.librarymanagementsystem.entity;

public enum Page {
    // Thêm chức năng gì thì ném file vào đây, rồi chạy setCenter của windowUtil
    LOGIN("/uet/librarymanagementsystem/fxml/login.fxml"),
    ADMIN("/uet/librarymanagementsystem/fxml/admin/admin_page.fxml"),
    STUDENT("/uet/librarymanagementsystem/fxml/student/student_page.fxml"),
    ADD_STUDENT("/uet/librarymanagementsystem/fxml/admin/add_student.fxml"),
    ADD_DOCUMENT("/uet/librarymanagementsystem/fxml/admin/add_document.fxml"),
    SEARCH_AND_REMOVE_STUDENT("/uet/librarymanagementsystem/fxml/admin/search_and_remove_student.fxml"),
    SEARCH_AND_REMOVE_DOCUMENT("/uet/librarymanagementsystem/fxml/admin/search_and_remove_document.fxml");

    private final String fxmlPath;

    Page(String fxmlPath) {
        this.fxmlPath = fxmlPath;
    }

    public String getFXMLPath() {
        return fxmlPath;
    }
}
