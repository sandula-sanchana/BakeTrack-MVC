package edu.ijse.baketrack.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class StorekeeperDashboardController {

    @FXML
    private AnchorPane apSKDashBoard;

    @FXML
    void btnIngredients(ActionEvent event) {
        setPages("/View/IngredientCrudPage.fxml");
    }

    @FXML
    void btnProduct(ActionEvent event) {
        setPages("/View/ProductCrudPage.fxml");
    }

    @FXML
    void btnProductIng(ActionEvent event) {
        setPages("/View/ProductIngredientPage.fxml");
    }

    public void setPages(String pageLocation){
        try {
            apSKDashBoard.getChildren().clear();
            AnchorPane ap= FXMLLoader.load(getClass().getResource(pageLocation));
            apSKDashBoard.getChildren().add(ap);
        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Page Load Error");
            alert.setHeaderText("Could not load page: " + pageLocation);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }

    }

    public void btnLogout(ActionEvent actionEvent) {
        setPages("/View/LogInPage.fxml");
    }
}
