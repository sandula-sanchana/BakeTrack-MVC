package edu.ijse.baketrack.controller;

import edu.ijse.baketrack.dto.DeliveryDto;
import edu.ijse.baketrack.dto.OrderDto;
import edu.ijse.baketrack.dto.VehicleDto;
import edu.ijse.baketrack.model.*;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class setDeliveryPageController implements Initializable {

    public TableView<OrderDto> tableOrderDetail;
    public TableColumn <OrderDto,Integer> clmnCusID;
    public TableColumn  <OrderDto,Integer>clmnDelID;
    public TableColumn  <OrderDto, LocalDate>clmnOrderDate;
    public TableColumn  <OrderDto,Double>clmnTotalPrice;
    public TableColumn  <OrderDto,String>clmnStatus;
    public TableView<VehicleDto> VehicleDetailTable;
    //public TextField txtDelPageCusID;
    public TableColumn<VehicleDto,Integer> clmnVheicleID;
    public TableColumn<VehicleDto,String>  clmnTYpe;
    public TableColumn<VehicleDto,String>  clmnLplate;
    public TextField txtAreaInput;
    public DatePicker datePicker;
    public TextField txtInputVid;
    public TextField txtDelPageOID;


    private OrderDto orderDto=new OrderDto();
    private OrderInterface orderInterface=new OrdersModel();
    private  ArrayList<OrderDto> orderDtos;
    private ArrayList<VehicleDto> vehicleDtos;
    private VehicleInterface vehicleInterface=new VehicleModel();
    private DeliveryDto deliveryDto;
    private DeliveryInterface deliveryInterface=new DeliveryModel();

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

        orderDtos = new ArrayList<>();
        vehicleDtos=new ArrayList<>();

        clmnCusID.setCellValueFactory(cellData ->
                new SimpleObjectProperty<>(cellData.getValue().getCustomerID()));

        clmnDelID.setCellValueFactory(cellData ->
                new SimpleObjectProperty<>(cellData.getValue().getDeliveryID()));

        clmnOrderDate.setCellValueFactory(cellData ->
                new SimpleObjectProperty<>(cellData.getValue().getOrderDate()));

        clmnTotalPrice.setCellValueFactory(cellData ->
                new SimpleObjectProperty<>(cellData.getValue().getTotalPrice()));

        clmnStatus.setCellValueFactory(cellData ->
                new SimpleObjectProperty<>(cellData.getValue().getStatus()));

        tableOrderDetail.setItems(FXCollections.observableArrayList(orderDtos));


        clmnVheicleID.setCellValueFactory(cellData ->
                new SimpleObjectProperty<>(cellData.getValue().getVehicle_id()));

        clmnTYpe.setCellValueFactory(cellData ->
                new SimpleObjectProperty<>(cellData.getValue().getType()));

        clmnLplate.setCellValueFactory(cellData ->
                new SimpleObjectProperty<>(cellData.getValue().getLicensePlate()));


        VehicleDetailTable.setItems(FXCollections.observableArrayList(vehicleDtos));

    }

    public void setValuesToTable() throws SQLException {
        orderDtos=orderInterface.getOrderByID(Integer.parseInt(txtDelPageOID.getText()));
        if(orderDtos!=null) {

            tableOrderDetail.getItems().clear();
            tableOrderDetail.getItems().addAll(orderDtos);
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
            VehicleDetailTable.getItems().clear();
            VehicleDetailTable.getItems().addAll(vehicleDtos);
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
