module com.example.spl_file {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.base;
    requires javafx.graphics;


    opens com.example.spl_file to javafx.fxml;
    exports com.example.spl_file;
}