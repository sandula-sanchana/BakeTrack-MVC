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
        setPages("/View/CustomerCrudPage.fxml");
    }

    @FXML
    void btnEmp(ActionEvent event) {
        setPages("/View/EmployeeCrudPage.fxml");
    }

    @FXML
    void btnOrder(ActionEvent event) {
        setPages("/View/AddOrderPage.fxml");
    }

    @FXML
    void btnPay(ActionEvent event) {
        setPages("/View/setPayments.fxml");
    }

    @FXML
    public void btnReports(ActionEvent event) {
    }

    @FXML
    public void btnDelivery(ActionEvent event) {
        setPages("/View/setDeliveryPage.fxml");
    }

    @FXML
    void btnSup(ActionEvent event) {
        setPages("/View/SupplierCrudPage.fxml");
    }

    @FXML
    void btnVehicle(ActionEvent event) {
        setPages("/View/VehicleCrudPage.fxml");
    }

    public void setPages(String pageLocation){
        try {
            apOwnerDB.getChildren().clear();
            AnchorPane ap= FXMLLoader.load(getClass().getResource(pageLocation));
            apOwnerDB.getChildren().add(ap);
        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Page Load Error");
            alert.setHeaderText("Could not load page: " + pageLocation);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }

    }


    public void btnUser(ActionEvent actionEvent) {

        setPages("/View/UserCrudPage.fxml");
    }

    public void btnSetProduction(ActionEvent actionEvent) {
        setPages("/View/setProductionCrudPage.fxml");
    }

    public void btnLogout(ActionEvent actionEvent) {
        setPages("/View/LogInPage.fxml");
    }
}