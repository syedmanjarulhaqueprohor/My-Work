module com.numerical {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;

    opens com.numerical to javafx.fxml;
    opens com.numerical.controller to javafx.fxml;
    opens com.numerical.model to javafx.fxml;
    opens com.numerical.view to javafx.fxml;

    exports com.numerical;
    exports com.numerical.controller;
    exports com.numerical.model;
}
