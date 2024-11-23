module uet.librarymanagementsystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.xerial.sqlitejdbc;
    requires org.slf4j;
    requires org.json;
    requires com.google.zxing;
    requires com.google.zxing.javase;
    requires java.mail;
    requires com.github.librepdf.openpdf;
    requires java.desktop;

    opens uet.librarymanagementsystem.controllers to javafx.fxml;
    exports uet.librarymanagementsystem;
    opens uet.librarymanagementsystem.controllers.admin to javafx.fxml;
    opens uet.librarymanagementsystem.controllers.student to javafx.fxml;
    opens uet.librarymanagementsystem.controllers.info_document to javafx.fxml;
    opens uet.librarymanagementsystem.controllers.notification to javafx.fxml;
}