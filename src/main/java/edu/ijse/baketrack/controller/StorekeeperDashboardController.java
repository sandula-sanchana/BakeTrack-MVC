package edu.ijse.baketrack.controller;

import edu.ijse.baketrack.dto.IngredientDto;
import edu.ijse.baketrack.dto.ProductDto;
import edu.ijse.baketrack.model.IngredientInterface;
import edu.ijse.baketrack.model.IngredientModel;
import edu.ijse.baketrack.model.ProductInterface;
import edu.ijse.baketrack.model.ProductModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class StorekeeperDashboardController implements Initializable {
    public BarChart ingBarChart;
    private ProductInterface productInterface=new ProductModel();

    public PieChart pieChart;
    @FXML
    private AnchorPane apSKDashBoard;

    public StorekeeperDashboardController() throws SQLException, ClassNotFoundException {
    }

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

    public void loadPieChart() {
        try {
            List<ProductDto> items = new ArrayList<>();
            items=productInterface.getAllProducts();

            ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();

            for (ProductDto item : items) {
                pieChartData.add(new PieChart.Data(item.getName(),item.getQuantity()));
            }

            pieChart.setData(pieChartData);
            pieChart.setTitle("Product Quantity Overview");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
         loadPieChart();
         loadToBarChart();
    }

    public void loadToBarChart(){
        try {
            IngredientInterface ingredientInterface=new IngredientModel();
            List<IngredientDto> ingredients = ingredientInterface.getAllIngredients();

            XYChart.Series<String, Number> series = new XYChart.Series<>();
            series.setName("Ingredient Stocks");

            for (IngredientDto ingredient : ingredients) {
                series.getData().add(new XYChart.Data<>(ingredient.getName(), ingredient.getStock_amount()));
            }

            ingBarChart.getData().clear();
            ingBarChart.getData().add(series);
            ingBarChart.setTitle("Ingredient Stock Levels");

        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Could not load ingredient bar chart.");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }
}
