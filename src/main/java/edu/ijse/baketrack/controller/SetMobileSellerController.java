package edu.ijse.baketrack.controller;

import edu.ijse.baketrack.dto.DeliveryDto;
import edu.ijse.baketrack.dto.EmployeeDto;
import edu.ijse.baketrack.dto.tm.DeliveryTM;
import edu.ijse.baketrack.dto.tm.EmployeeTM;
import edu.ijse.baketrack.model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class SetMobileSellerController implements Initializable {
    public TextField txtEidInput;
    private ArrayList<EmployeeDto> employeeDtoArrayList;
    private ArrayList<DeliveryDto> deliveryDtoArrayList;
    private ObservableList<EmployeeTM> employeeTMObservableList= FXCollections.observableArrayList();
    private ObservableList<DeliveryTM> deliveryTMObservableList=FXCollections.observableArrayList();
    private DeliveryInterface deliveryInterface;
    private OrderInterface orderInterface;
    private EmployeeInterface employeeInterface;
    private DeliveryTM deliveryTM;
    private EmployeeTM employeeTM;


    public  SetMobileSellerController(){
        try {
            deliveryInterface=new DeliveryModel();
            employeeInterface=new EmployeeModel();
            orderInterface=new OrdersModel();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private TableView<DeliveryTM> DeliveryTable;

    @FXML
    private TableView<EmployeeTM> TableMobileSeller;

    @FXML
    private TableColumn<DeliveryTM, String> clmnArea;

    @FXML
    private TableColumn<EmployeeTM, String> clmnCno;

    @FXML
    private TableColumn<DeliveryTM, LocalDate> clmnDelDAte;

    @FXML
    private TableColumn<DeliveryTM, Integer> clmnDelid;

    @FXML
    private TableColumn<DeliveryTM, Integer> clmnEidDel;

    @FXML
    private TableColumn<EmployeeTM, Integer> clmnEidE;

    @FXML
    private TableColumn<EmployeeTM, String> clmnNAme;

    @FXML
    private TableColumn<EmployeeTM, String> clmnRole;

    @FXML
    private TableColumn<DeliveryTM, Integer> clmnVid;

    //private VBox txtEmpID;

    @FXML
    private TextField txtPayidInput;

    @FXML
    void OrderPageGoBackButton(ActionEvent event) {

    }

    @FXML
    void btnGetDelAndEmp(ActionEvent event) throws SQLException {
        getDelDetails();


    }

    @FXML
    void btnSetSeller(ActionEvent event) {
        setSellerForDelivery();
        getDelDetails();
    }

    public void getDelDetails(){
        try {
            deliveryTMObservableList.clear();
            deliveryDtoArrayList=deliveryInterface.getUnassignedDeliveries();
            if(deliveryDtoArrayList!=null){
                for(DeliveryDto deliveryDto : deliveryDtoArrayList){
                    deliveryTM=new DeliveryTM(deliveryDto.getDelivery_id(),deliveryDto.getVehicleID(),
                            deliveryDto.getEmployee_id(),deliveryDto.getDeliveryDate(),deliveryDto.getDeliveryArea());
                    deliveryTMObservableList.add(deliveryTM);
                }
                getEmpDetails();
            }else {
                new Alert(Alert.AlertType.INFORMATION,"not found any Orders");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void getEmpDetails() throws SQLException {
        employeeTMObservableList.clear();
        employeeDtoArrayList=employeeInterface.getAllAvailableAndNonAssinEmp();
        if(employeeDtoArrayList!=null) {
            for (EmployeeDto employeeDto : employeeDtoArrayList){
                employeeTM=new EmployeeTM(employeeDto.getEmployee_id(),employeeDto.getName(),employeeDto.getContact(),employeeDto.getROle());
                employeeTMObservableList.add(employeeTM);
            }
        }else{
            new Alert(Alert.AlertType.INFORMATION,"not found");
        }
    }



    public void initialize(URL url, ResourceBundle resourceBundle) {

        clmnDelid.setCellValueFactory(new PropertyValueFactory<>("delivery_id"));
        clmnVid.setCellValueFactory(new PropertyValueFactory<>("vehicle_id"));
        clmnEidDel.setCellValueFactory(new PropertyValueFactory<>("employee_id"));
        clmnDelDAte.setCellValueFactory(new PropertyValueFactory<>("delivery_date"));
        clmnArea.setCellValueFactory(new PropertyValueFactory<>("area"));

        DeliveryTable.setItems(deliveryTMObservableList);

        clmnEidE.setCellValueFactory(new PropertyValueFactory<>("employee_id"));
        clmnNAme.setCellValueFactory(new PropertyValueFactory<>("name"));
        clmnCno.setCellValueFactory(new PropertyValueFactory<>("contact"));
        clmnRole.setCellValueFactory(new PropertyValueFactory<>("role"));

        TableMobileSeller.setItems(employeeTMObservableList);

    }

    public void setSellerForDelivery(){
        DeliveryTM selectedDel=DeliveryTable.getSelectionModel().getSelectedItem();
        EmployeeTM selectedEmp=TableMobileSeller.getSelectionModel().getSelectedItem();
        if(selectedEmp!=null && selectedDel!=null) {
            int del_id = selectedDel.getDelivery_id();
            int emp_id = selectedEmp.getEmployee_id();
            try {
                String resp = deliveryInterface.setEmployeeForDelivery(del_id, emp_id);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle(resp);
                alert.showAndWait();
                DeliveryTable.getSelectionModel().clearSelection();
                TableMobileSeller.getSelectionModel().clearSelection();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }else {
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setTitle("select delivery and a employee first");
            alert.showAndWait();
        }
    }
}
