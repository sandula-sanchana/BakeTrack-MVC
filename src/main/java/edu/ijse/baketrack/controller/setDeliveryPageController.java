package edu.ijse.baketrack.controller;

import edu.ijse.baketrack.dto.DeliveryDto;
import edu.ijse.baketrack.dto.OrderDto;
import edu.ijse.baketrack.dto.VehicleDto;
import edu.ijse.baketrack.dto.tm.OrderTM;
import edu.ijse.baketrack.dto.tm.VehicleTM;
import edu.ijse.baketrack.model.*;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class setDeliveryPageController implements Initializable {

    public TableView<OrderTM> tableOrderDetail;
    public TableColumn <OrderTM,Integer> clmnCusID;
    public TableColumn  <OrderTM,Integer>clmnDelID;
    public TableColumn  <OrderTM, LocalDate>clmnOrderDate;
    public TableColumn  <OrderTM,Double>clmnTotalPrice;
    public TableColumn  <OrderTM,String>clmnStatus;
    public TableView<VehicleTM> VehicleDetailTable;
    //public TextField txtDelPageCusID;
    public TableColumn<VehicleTM,Integer> clmnVheicleID;
    public TableColumn<VehicleTM,String>  clmnTYpe;
    public TableColumn<VehicleTM,String>  clmnLplate;
    public TextField txtAreaInput;
    public DatePicker datePicker;
    public TextField txtInputVid;
    public TextField txtDelPageOID;
    public AnchorPane setDelPageAP;


    private OrderDto orderDto=new OrderDto();
    private OrderInterface orderInterface=new OrdersModel();
    private  ArrayList<OrderDto> orderDtos;
    private ArrayList<VehicleDto> vehicleDtos;
    private VehicleInterface vehicleInterface=new VehicleModel();
    private DeliveryDto deliveryDto;
    private DeliveryInterface deliveryInterface=new DeliveryModel();
    private ObservableList<OrderTM> OrderTmOB=FXCollections.observableArrayList();
    private ObservableList<VehicleTM> vehicleTMS=FXCollections.observableArrayList();
    private VehicleTM vehicleTM;

    @FXML
    private TableView<?> addOrderPageTable;

    @FXML
    private TableColumn<?, ?> clmnPID;

    @FXML
    private TableColumn<?, ?> clmnPatOrder;

    @FXML
    private TableColumn<?, ?> clmnQty;

    @FXML
    private TextField txtOrderPageCusID;

    public setDeliveryPageController() throws SQLException, ClassNotFoundException {
    }

    @FXML
    void OrderPageGoBackButton(ActionEvent event) {
        try {
            setDelPageAP.getChildren().clear();
            AnchorPane ap= FXMLLoader.load(getClass().getResource("/View/OwnerDashboard.fxml"));
            setDelPageAP.getChildren().add(ap);
        } catch (IOException e) {
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setTitle("not found");
            alert.showAndWait();
            throw new RuntimeException(e);
        }

    }

    @FXML
    void btnGetVehicleDetails(ActionEvent event) throws SQLException {
      getAvailableVehicles();
    }

    @FXML
    void btnOidSearch(ActionEvent event) throws SQLException {
       setValuesToTable();
    }

    @FXML
    void btnSetArea(ActionEvent event) {

    }

    @FXML
    void btnSetDelivery(ActionEvent event) throws SQLException {
        setDelivery();
        setValuesToTable();
    }

    @FXML
    void btnSetVehicleID(ActionEvent event) {

    }

    @FXML
    void datePicker(ActionEvent event) {

    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        clmnCusID.setCellValueFactory(new PropertyValueFactory<>("customer_id"));

        clmnDelID.setCellValueFactory(new PropertyValueFactory<>("delivery_id"));

        clmnOrderDate.setCellValueFactory(new PropertyValueFactory<>("order_date"));

        clmnTotalPrice.setCellValueFactory(new PropertyValueFactory<>("total_price"));

        clmnStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        tableOrderDetail.setItems(OrderTmOB);


        clmnVheicleID.setCellValueFactory(new PropertyValueFactory<>("vehicle_id"));

        clmnTYpe.setCellValueFactory(new PropertyValueFactory<>("type"));

        clmnLplate.setCellValueFactory(new PropertyValueFactory<>("license_plate"));

        VehicleDetailTable.setItems(vehicleTMS);

    }

    public void setValuesToTable() throws SQLException {
        orderDtos=orderInterface.getOrderByID(Integer.parseInt(txtDelPageOID.getText()));

        if(orderDtos!=null){
            OrderTM orderTM=new OrderTM(orderDtos.getFirst().getCustomerID(),orderDtos.getFirst().getDeliveryID(),orderDtos.getFirst().getOrderDate(),
                    orderDtos.getFirst().getTotalPrice(),orderDtos.getFirst().getStatus());
            OrderTmOB.add(orderTM);
        }else{
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Not found");
            alert.showAndWait();
        }

    }

    private void getAvailableVehicles() throws SQLException {
         String status="available";
        vehicleDtos=vehicleInterface.getAvailableVehicles(status);
        if(vehicleDtos!=null){
            for(VehicleDto vdto :vehicleDtos) {
                vehicleTM = new VehicleTM(vdto.getVehicle_id(), vdto.getType(), vdto.getLicensePlate());
                vehicleTMS.add(vehicleTM);
            }
        }else{
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setTitle("All vehicles are out for delivery");
            alert.showAndWait();
        }

    }

    public void setDelivery() throws SQLException {
         deliveryDto=new DeliveryDto(Integer.parseInt(txtInputVid.getText()),datePicker.getValue(),txtAreaInput.getText());
         String resp=deliveryInterface.setDelivery(deliveryDto,txtDelPageOID.getText());

         Alert alert=new Alert(Alert.AlertType.INFORMATION);
         alert.setTitle(resp);
         alert.showAndWait();



    }
}
