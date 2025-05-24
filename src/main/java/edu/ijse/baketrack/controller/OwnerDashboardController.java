package edu.ijse.baketrack.controller;

import edu.ijse.baketrack.dto.OrderTrendDto;
import edu.ijse.baketrack.dto.PaymentsDto;
import edu.ijse.baketrack.dto.ProductDto;
import edu.ijse.baketrack.model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class OwnerDashboardController implements Initializable {

    public AnchorPane apOwnerDB;
    public LineChart <String, Number>lineChart;
    public PieChart paymentPieChart;
    public PieChart employeePieChart;
    private ProductInterface productInterface=new ProductModel();
    private OrderInterface orderInterface=new OrdersModel();
    private PaymentInterface paymentInterface=new PaymentModel();
    private EmployeeInterface employeeInterface=new EmployeeModel();

    public OwnerDashboardController() throws SQLException, ClassNotFoundException {
    }

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



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadLineChart();
        loadPaymentPieChart();
        loadEmpPieChart();
    }

    public void loadLineChart(){
        try {
            XYChart.Series<String, Number> series = new XYChart.Series<>();
            series.setName("Orders Over Time");

            List<OrderTrendDto> data = orderInterface.getOrderTrends();
            for (OrderTrendDto d : data) {
                series.getData().add(new XYChart.Data<>(d.getDate(), d.getCount()));
            }
            lineChart.setTitle("Orders Over Time");
            lineChart.getData().add(series);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadPaymentPieChart(){
        try {
            Map<String,Integer> paymentCountArray=paymentInterface.getPaymentCount();
            if(paymentCountArray!=null){
                ObservableList<PieChart.Data> pieSData=FXCollections.observableArrayList();
                for(Map.Entry<String,Integer> mapData : paymentCountArray.entrySet() ){
                    pieSData.add(new PieChart.Data(mapData.getKey(),mapData.getValue()));
                }
                paymentPieChart.setData(pieSData);
                paymentPieChart.setTitle("Payments");

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void loadEmpPieChart() {
        try {
            Map<String, Integer> employeeCountMap = employeeInterface.getEmpCount();
            if (employeeCountMap != null) {
                ObservableList<PieChart.Data> pieData = FXCollections.observableArrayList();
                for (Map.Entry<String, Integer> entry : employeeCountMap.entrySet()) {
                    pieData.add(new PieChart.Data(entry.getKey(), entry.getValue()));
                }
                employeePieChart.setData(pieData);
                employeePieChart.setTitle("Working Employees ");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}