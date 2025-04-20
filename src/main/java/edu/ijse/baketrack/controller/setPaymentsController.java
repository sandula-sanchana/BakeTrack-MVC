package edu.ijse.baketrack.controller;

import edu.ijse.baketrack.dto.OrderDto;
import edu.ijse.baketrack.dto.PaymentsDto;
import edu.ijse.baketrack.model.*;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class setPaymentsController implements Initializable {

    public RadioButton RadioPaid;
    public ToggleGroup PaymentStatusGroup;
    public RadioButton RadioCancelled;
    public TextField txtPayid;
    public TextField txtPayMethod;
    private OrderInterface orderInterface;
    private ArrayList<OrderDto> orderDtos;
    private int orderID;
    private ArrayList<PaymentsDto> paymentsDtos;
    private PaymentInterface paymentInterface;
    private String paymentStatus;
    private PaymentsDto paymentsDto;
    private int vehicle_id;
    private DeliveryInterface deliveryInterface;

    public setPaymentsController(){
        try {
            orderInterface=new OrdersModel();
            paymentInterface=new PaymentModel();
            deliveryInterface=new DeliveryModel();
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private TableView<OrderDto> TableOrder;

    @FXML
    private TableView<PaymentsDto> TablePayment;

    @FXML
    private TableColumn<OrderDto, Integer> clmnCusID;

    @FXML
    private TableColumn<OrderDto, Integer> clmnDelID;

    @FXML
    private TableColumn<OrderDto, Integer> clmnOIDcus;

    @FXML
    private TableColumn<PaymentsDto,Integer> clmnOIDpay;

    @FXML
    private TableColumn<OrderDto, LocalDate> clmnOrderDate;

    @FXML
    private TableColumn<PaymentsDto,LocalDate> clmnPaymentDAte;

    @FXML
    private TableColumn<PaymentsDto,String> clmnPaymentMethod;

    @FXML
    private TableColumn<PaymentsDto, Integer> clmnPid;

    @FXML
    private TableColumn<PaymentsDto,Double> clmnPricepay;

    @FXML
    private TableColumn<OrderDto, String> clmnStatus;

    @FXML
    private TableColumn<PaymentsDto,String> clmnStatusPAy;

    @FXML
    private TableColumn<OrderDto, Double>clmnTotalPrice;

    @FXML
    private DatePicker datePicker;

    @FXML
    private TextField txtAreaInput;

    @FXML
    private TextField txtInputVid;

    @FXML
    private TextField txtPaymentPageDelID;

    @FXML
    void OrderPageGoBackButton(ActionEvent event) {

    }

    @FXML
    void btnDelIDSearch(ActionEvent event) throws SQLException {
         getOrderandPaymentByDelID();
         getVehicleID();


    }


     @FXML
     void btnPAymentDone(ActionEvent event) throws SQLException{
         setPayment();
         getOrderandPaymentByDelID();
     }


    public void  getOrderandPaymentByDelID() throws SQLException {
        orderDtos=orderInterface.getOrderByDelID(txtPaymentPageDelID.getText());
        if(orderDtos!=null){
            loadOrderToTable(orderDtos);
            orderID=orderDtos.getFirst().getOrder_id();
            paymentsDtos=paymentInterface.getPaymentDetailsByOrderId(orderID);
            if (paymentsDtos!=null){
                loadPaymentToTable(paymentsDtos);
            }else{
                Alert alert=new Alert(Alert.AlertType.ERROR);
                alert.setTitle("payment not found");
                alert.showAndWait();
            }

        }else{
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setTitle("not found");
            alert.showAndWait();
        }
    }

    public  void loadOrderToTable(ArrayList<OrderDto> orderDtos){
        TableOrder.getItems().clear();
        TableOrder.getItems().addAll(orderDtos);
    }
    public void loadPaymentToTable(ArrayList<PaymentsDto> paymentsDtos){
        TablePayment.getItems().clear();
        TablePayment.getItems().addAll(paymentsDtos);
    }


    public void initialize(URL url, ResourceBundle resourceBundle) {
       orderDtos=new ArrayList<>();
       paymentsDtos=new ArrayList<>();


        clmnCusID.setCellValueFactory(cellData ->
                new SimpleObjectProperty<>(cellData.getValue().getCustomerID()));
        clmnOIDcus.setCellValueFactory(cellData ->
                new SimpleObjectProperty<>(cellData.getValue().getOrder_id()));
        clmnDelID.setCellValueFactory(cellData ->
                new SimpleObjectProperty<>(cellData.getValue().getDeliveryID()));
        clmnOrderDate.setCellValueFactory(cellData ->
                new SimpleObjectProperty<>(cellData.getValue().getOrderDate()));
        clmnTotalPrice.setCellValueFactory(cellData ->
                new SimpleObjectProperty<>(cellData.getValue().getTotalPrice()));
        clmnStatus.setCellValueFactory(cellData ->
                new SimpleObjectProperty<>(cellData.getValue().getStatus()));

        TableOrder.setItems(FXCollections.observableArrayList(orderDtos));

        clmnPid.setCellValueFactory(cellData ->
                new SimpleObjectProperty<>(cellData.getValue().getPayment_id()));
        clmnOIDpay.setCellValueFactory(cellData ->
                new SimpleObjectProperty<>(cellData.getValue().getOrderID()));
        clmnPricepay.setCellValueFactory(cellData ->
                new SimpleObjectProperty<>(cellData.getValue().getPrice()));
        clmnPaymentMethod.setCellValueFactory(cellData ->
                new SimpleObjectProperty<>(cellData.getValue().getPaymentMethod()));
        clmnPaymentDAte.setCellValueFactory(cellData ->
                new SimpleObjectProperty<>(cellData.getValue().getPaymentDate()));
        clmnStatusPAy.setCellValueFactory(cellData ->
                new SimpleObjectProperty<>(cellData.getValue().getStatus()));

        TablePayment.setItems(FXCollections.observableArrayList(paymentsDtos));

        PaymentStatusGroup.selectedToggleProperty().addListener((observable,oldToggle,newToggle) ->{getSelectedStatus();} );


    }

    public void getSelectedStatus(){
        RadioButton selectedOne=(RadioButton)PaymentStatusGroup.getSelectedToggle();
        if(selectedOne!=null){
            paymentStatus=selectedOne.getText();

        }else {
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setTitle("select status first");
            alert.showAndWait();
        }

    }

    public void setPayment() throws SQLException {
         paymentsDto=new PaymentsDto(Integer.parseInt(txtPayid.getText()),orderID,txtPayMethod.getText(),datePicker.getValue(),paymentStatus);
         String resp=paymentInterface.setPayments(paymentsDto,vehicle_id);
         Alert alert=new Alert(Alert.AlertType.INFORMATION);
         alert.setTitle(resp);
         alert.showAndWait();
    }

    public void getVehicleID() throws SQLException {
        int resp=deliveryInterface.getVehicleIDbyDelID(Integer.parseInt(txtPaymentPageDelID.getText()));
        if(resp>0){
            vehicle_id=resp;
        }else{
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setTitle("vehicle not found by del id");
            alert.showAndWait();
        }

    }
}
