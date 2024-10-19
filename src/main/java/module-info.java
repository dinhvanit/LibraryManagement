module uet.librarymanagementsystem {
    requires javafx.controls;
    requires javafx.fxml;


    opens uet.librarymanagementsystem.controllers to javafx.fxml;
    exports uet.librarymanagementsystem;
}