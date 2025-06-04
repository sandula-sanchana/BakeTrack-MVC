package edu.ijse.baketrack.controller;

import edu.ijse.baketrack.dto.DeliveryDto;
import edu.ijse.baketrack.dto.EmployeeDto;
import edu.ijse.baketrack.dto.tm.DeliveryTM;
import edu.ijse.baketrack.dto.tm.EmployeeTM;
import edu.ijse.baketrack.model.*;
import edu.ijse.baketrack.util.WhatsAppSender;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class SetMobileSellerController implements Initializable {
    public TextField txtEidInput;
    public AnchorPane setMSellerap;
    public ComboBox<String> cmbContactNo;
    public TextField txtContactNO;
    private ArrayList<EmployeeDto> employeeDtoArrayList;
    private ArrayList<DeliveryDto> deliveryDtoArrayList;
    private ObservableList<EmployeeTM> employeeTMObservableList= FXCollections.observableArrayList();
    private ObservableList<DeliveryTM> deliveryTMObservableList=FXCollections.observableArrayList();
    private DeliveryInterface deliveryInterface;
    private OrderInterface orderInterface;
    private EmployeeInterface employeeInterface;
    private DeliveryTM deliveryTM;
    private EmployeeTM employeeTM;
    private VehicleInterface vehicleInterface;


    public  SetMobileSellerController(){
        try {
            deliveryInterface=new DeliveryModel();
            employeeInterface=new EmployeeModel();
            orderInterface=new OrdersModel();
            vehicleInterface=new VehicleModel();
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

        setMSellerap.getChildren().clear();
        try {
            AnchorPane ap= FXMLLoader.load(getClass().getResource("/View/HRManagerDashboard.fxml"));
            setMSellerap.getChildren().add(ap);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

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

        clmnEidE.setCellValueFactory(new PropertyValueFactory<>("employeeID"));
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
            int emp_id = selectedEmp.getEmployeeID();
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

    public void btnSendWappMsg(ActionEvent actionEvent) {

        DeliveryTM selectedDel=DeliveryTable.getSelectionModel().getSelectedItem();
        EmployeeTM selectedEmp=TableMobileSeller.getSelectionModel().getSelectedItem();

        if(selectedDel == null || selectedEmp == null || txtContactNO==null) {
            new Alert(Alert.AlertType.INFORMATION,"select a delivery and A mobile seller first").showAndWait();
            return;
        }

        try {
            String licence_plate=vehicleInterface.getLicensePlateById(selectedDel.getVehicle_id());
            String del_id=String.valueOf(selectedDel.getDelivery_id());
            String del_date=selectedDel.getDelivery_date().toString();
            String area=selectedDel.getArea();

            String message = "🚚 *Delivery Assignment Notice* 🚚\n\n" +
                    "Hello! You have been assigned a new delivery.\n\n" +
                    "*Delivery ID:* " + del_id + "\n" +
                    "*Delivery Date:* " + del_date + "\n" +
                    "*Assigned Vehicle:* " + licence_plate + "\n" +
                    "*Delivery Area:* " + area + "\n\n" +
                    "Please be prepared and ensure the vehicle is ready for dispatch. Contact the HR manager if you have any questions.\n\n" +
                    "Thank you! 😊";

            WhatsAppSender.sendMessage(txtContactNO.getText(), message);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    public void empTableClick(MouseEvent mouseEvent) {
        EmployeeTM selected=TableMobileSeller.getSelectionModel().getSelectedItem();

        if(selected!=null){
            txtContactNO.setText(selected.getContact());

        }
    }

    public void tableMouseClick(MouseEvent mouseEvent) {
    }
}
