package edu.ijse.baketrack.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class HRManagerDashboardController {

    @FXML
    private AnchorPane HMDap;

    @FXML
    void btnAttendance(ActionEvent event) {
           setPages("/View/AttendanceCrudPage.fxml");
    }

    @FXML
    void btnPAyroll(ActionEvent event) {
             setPages("/View/PayrollCrudPage.fxml");
    }

    public void setPages(String pageLocation){
        try {
            HMDap.getChildren().clear();
            AnchorPane ap= FXMLLoader.load(getClass().getResource(pageLocation));
            HMDap.getChildren().add(ap);
        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Page Load Error");
            alert.setHeaderText("Could not load page: " + pageLocation);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }

    }


    public void btnSetMobileSeller(ActionEvent actionEvent) {
        setPages("/View/setMobileSellerPage.fxml");
    }

    public void btnLogout(ActionEvent actionEvent) {
        setPages("/View/LogInPage.fxml");
    }
}
