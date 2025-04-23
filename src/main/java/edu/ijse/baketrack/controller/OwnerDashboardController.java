package edu.ijse.baketrack.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class OwnerDashboardController {

    public AnchorPane apOwnerDB;

    @FXML
    void btnCus(ActionEvent event) {

    }

    @FXML
    void btnEmp(ActionEvent event) {

    }

    @FXML
    void btnOrder(ActionEvent event) {
       setPages("/View/AddOrderPage.fxml");
    }

    @FXML
    void btnPay(ActionEvent event) {

    }

    @FXML
    void btnReport(ActionEvent event) {

    }

    @FXML
    void btnSup(ActionEvent event) {

    }

    @FXML
    void btnVehicle(ActionEvent event) {

    }

    public void setPages(String pageLocation){
        try {
            apOwnerDB.getChildren().clear();
            AnchorPane ap= FXMLLoader.load(getClass().getResource(pageLocation));
            apOwnerDB.getChildren().add(ap);
        } catch (IOException e) {
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setTitle("not found");
            alert.showAndWait();
            throw new RuntimeException(e);

        }
    }


}
