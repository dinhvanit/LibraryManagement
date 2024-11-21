module uet.librarymanagementsystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.xerial.sqlitejdbc;
    requires org.slf4j;
    requires java.desktop;
    requires org.json;
    requires com.google.zxing;
    requires com.google.zxing.javase;

    opens uet.librarymanagementsystem.controllers to javafx.fxml;
    exports uet.librarymanagementsystem;
    opens uet.librarymanagementsystem.controllers.admin to javafx.fxml;
    opens uet.librarymanagementsystem.controllers.student to javafx.fxml;
    opens uet.librarymanagementsystem.controllers.info_document to javafx.fxml;
}