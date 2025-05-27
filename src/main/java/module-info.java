module baketrack.project_bakery_x {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.base;
    requires java.sql;
    requires java.desktop;
    requires twilio;
    requires net.sf.jasperreports.core;
    requires java.mail;

    exports edu.ijse.baketrack to javafx.fxml;
    exports edu.ijse.baketrack.controller to javafx.fxml;
    exports edu.ijse.baketrack.dto.tm;
    opens edu.ijse.baketrack.dto.tm to javafx.base;

    opens edu.ijse.baketrack to javafx.graphics, javafx.fxml;
    opens edu.ijse.baketrack.controller to javafx.fxml;
}
