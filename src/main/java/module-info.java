module baketrack.project_bakery_x {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.base;
    requires java.sql;
    requires java.desktop;

    exports edu.ijse.baketrack to javafx.fxml;
    exports edu.ijse.baketrack.controller to javafx.fxml;

    opens edu.ijse.baketrack to javafx.graphics, javafx.fxml;
    opens edu.ijse.baketrack.controller to javafx.fxml;
}
