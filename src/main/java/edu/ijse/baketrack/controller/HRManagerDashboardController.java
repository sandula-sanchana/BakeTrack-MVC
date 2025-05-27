package edu.ijse.baketrack.controller;

import edu.ijse.baketrack.dto.AttendanceCount;
import edu.ijse.baketrack.dto.OrderTrendDto;
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
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class HRManagerDashboardController implements Initializable {
    public LineChart attendanceLIneChart;
    public PieChart totalEmpPie;
    public PieChart salaryStatusPie;
    private AttendanceInterface attendanceInterface=new AttendanceModel();
    private   EmployeeInterface employeeInterface=new EmployeeModel();
    private PayrollInterface payrollInterface=new PayrollModel();

    @FXML
    private AnchorPane HMDap;

    public HRManagerDashboardController() throws SQLException, ClassNotFoundException {
    }

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

    public void loadLineChart(){
        try {
            XYChart.Series<String,Number> series = new XYChart.Series<>();
            series.setName("Attendance overview");

            List<AttendanceCount> data = attendanceInterface.getAttendanceEachDay();
            for (AttendanceCount attendanceCount : data) {
                series.getData().add(new XYChart.Data<>(attendanceCount.getDate().toString(),attendanceCount.getCount()));
            }
             attendanceLIneChart.setTitle("Attendance Over Time");
              attendanceLIneChart.getData().add(series);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
         loadLineChart();
         loadEmpPieChart();
         loadStatusPayrollPie();
    }

    public void loadEmpPieChart() {

        try {

            Map<String, Integer> employeeCountMap = employeeInterface.getEmpCount();
            if (employeeCountMap != null) {
                ObservableList<PieChart.Data> pieData = FXCollections.observableArrayList();
                for (Map.Entry<String, Integer> entry : employeeCountMap.entrySet()) {
                    String label = entry.getKey() + " (" + entry.getValue() + ")";
                    pieData.add(new PieChart.Data(label, entry.getValue()));
                }
                totalEmpPie.setData(pieData);
                totalEmpPie.setTitle("Working Employees ");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void loadStatusPayrollPie(){

        try {
            Map<String,Integer> statusCounts=new HashMap<>();
            ObservableList<PieChart.Data> pieData=FXCollections.observableArrayList();

            statusCounts=payrollInterface.getPayrollStatus();

            if (statusCounts!=null){
               for(Map.Entry<String,Integer> entry: statusCounts.entrySet()){
                   String label = entry.getKey() + " (" + entry.getValue() + ")";
                   pieData.add(new PieChart.Data(label,entry.getValue()));
               }
               salaryStatusPie.getData().clear();
               salaryStatusPie.setData(pieData);
               LocalDate date=LocalDate.now();
               int month=date.getMonthValue();
               int year=date.getYear();
               salaryStatusPie.setTitle("Salary Status of : " + year+" - "+month);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

