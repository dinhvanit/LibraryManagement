package uet.librarymanagementsystem.entity;

public enum Page {
    // Thêm chức năng gì thì ném file vào đây, rồi chạy setCenter của windowUtil
    LOGIN("/uet/librarymanagementsystem/fxml/login.fxml"),
    ADMIN("/uet/librarymanagementsystem/fxml/admin/admin_page.fxml"),
    STUDENT("/uet/librarymanagementsystem/fxml/student/student_page.fxml"),
    CHANGE_PASSWORD("/uet/librarymanagementsystem/fxml/student/change_password.fxml"),
    ADD_STUDENT("/uet/librarymanagementsystem/fxml/admin/add_student.fxml"),
    ADD_DOCUMENT("/uet/librarymanagementsystem/fxml/admin/add_document.fxml"),
    SEARCH_AND_REMOVE_STUDENT("/uet/librarymanagementsystem/fxml/admin/search_and_remove_student.fxml"),
    SEARCH_AND_REMOVE_DOCUMENT("/uet/librarymanagementsystem/fxml/admin/search_and_remove_document.fxml"),
    SEARCH_AND_BORROW_DOCUMENT("/uet/librarymanagementsystem/fxml/student/search_borrow_document.fxml"),
    TRANSACTION_DOCUMENT("/uet/librarymanagementsystem/fxml/student/transaction_documents.fxml"),
    RETURN_DOCUMENT("/uet/librarymanagementsystem/fxml/student/borrowed_documents.fxml"),
    SHOW_INFO_DOCUMENT("/uet/librarymanagementsystem/fxml/info_document/get_info_document.fxml"),
    SHOW_WRITE_RATING_AND_REVIEW("/uet/librarymanagementsystem/fxml/info_document/rating_and_review.fxml"),
    SHOW_VIEW_YOUR_REVIEW("/uet/librarymanagementsystem/fxml/info_document/view_your_review.fxml");
    private final String fxmlPath;

    Page(String fxmlPath) {
        this.fxmlPath = fxmlPath;
    }

    public String getFXMLPath() {
        return fxmlPath;
    }
}
