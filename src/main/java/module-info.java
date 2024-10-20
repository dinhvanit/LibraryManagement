module uet.librarymanagementsystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.xerial.sqlitejdbc;
    requires org.slf4j;

    opens uet.librarymanagementsystem.controllers to javafx.fxml;
    exports uet.librarymanagementsystem;
}