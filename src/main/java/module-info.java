module baketrack.project_bakery_x {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens edu.ijse.baketrack.project_bakery_x to javafx.fxml;
    exports edu.ijse.baketrack.project_bakery_x;
}