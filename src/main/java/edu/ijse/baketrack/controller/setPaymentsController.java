package edu.ijse.baketrack.controller;

import edu.ijse.baketrack.dto.OrderDto;
import edu.ijse.baketrack.dto.PaymentsDto;
import edu.ijse.baketrack.dto.tm.OrderTM;
import edu.ijse.baketrack.dto.tm.PaymentsTM;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class setPaymentsController implements Initializable {

    public RadioButton RadioPaid;
    public ToggleGroup PaymentStatusGroup;
    public RadioButton RadioCancelled;
    public TextField txtPayid;;
    public AnchorPane setPayAp;
    public ComboBox<String> cmbPayMethod;
    private OrderInterface orderInterface;
    private ArrayList<OrderDto> orderDtos;
    private int orderID;
    private ArrayList<PaymentsDto> paymentsDtos;
    private PaymentInterface paymentInterface;
    private String paymentStatus;
    private PaymentsDto paymentsDto;
    private int vehicle_id;
    private DeliveryInterface deliveryInterface;
    private ObservableList<OrderTM> orderTMObservableList=FXCollections.observableArrayList();
    private ObservableList<PaymentsTM> paymentsTMObservableList=FXCollections.observableArrayList();

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
    private TableView<OrderTM> TableOrder;

    @FXML
    private TableView<PaymentsTM> TablePayment;

    @FXML
    private TableColumn<OrderTM, Integer> clmnCusID;

    @FXML
    private TableColumn<OrderTM, Integer> clmnDelID;

    @FXML
    private TableColumn<OrderTM, Integer> clmnOIDcus;

    @FXML
    private TableColumn<PaymentsTM,Integer> clmnOIDpay;

    @FXML
    private TableColumn<OrderTM, LocalDate> clmnOrderDate;

    @FXML
    private TableColumn<PaymentsTM,LocalDate> clmnPaymentDAte;

    @FXML
    private TableColumn<PaymentsTM,String> clmnPaymentMethod;

    @FXML
    private TableColumn<PaymentsTM, Integer> clmnPid;

    @FXML
    private TableColumn<PaymentsTM,Double> clmnPricepay;

    @FXML
    private TableColumn<OrderTM, String> clmnStatus;

    @FXML
    private TableColumn<PaymentsTM,String> clmnStatusPAy;

    @FXML
    private TableColumn<OrderTM, Double>clmnTotalPrice;

    @FXML
    private DatePicker datePicker;

    @FXML
    private TextField txtPaymentPageDelID;

    @FXML
    void OrderPageGoBackButton(ActionEvent event) {

        try {
             setPayAp.getChildren().clear();
            AnchorPane ap= FXMLLoader.load(getClass().getResource("/View/OwnerDashboard.fxml"));
            setPayAp.getChildren().add(ap);
        } catch (IOException e) {
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setTitle("not found");
            alert.showAndWait();
            throw new RuntimeException(e);
        }

    }

    @FXML
    void btnDelIDSearch(ActionEvent event) throws SQLException {
        orderTMObservableList.clear();
         paymentsTMObservableList.clear();
         boolean done=getOrderandPaymentByDelID();
         if (done) {
             getVehicleID();
         }


    }


     @FXML
     void btnPAymentDone(ActionEvent event) throws SQLException{
         setPayment();
         paymentsTMObservableList.clear();
         orderTMObservableList.clear();
         getOrderandPaymentByDelID();
         cleatTxt();
     }


    public boolean getOrderandPaymentByDelID() throws SQLException {
        orderDtos = orderInterface.getOrderByDelID(txtPaymentPageDelID.getText());

        if (orderDtos != null && !orderDtos.isEmpty()) {
            loadOrderToTable(orderDtos);
            orderID = orderDtos.getFirst().getOrder_id();
            paymentsDtos = paymentInterface.getPaymentDetailsByOrderId(orderID);

            if (paymentsDtos != null && !paymentsDtos.isEmpty()) {
                loadPaymentToTable(paymentsDtos);
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Payment not found");
                alert.showAndWait();
            }
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Orders not found");
            alert.showAndWait();
            return false;
        }
    }
    public void cleatTxt(){
        txtPaymentPageDelID.clear();
        txtPayid.clear();
    }

    public  void loadOrderToTable(ArrayList<OrderDto> orderDtos){
        for (OrderDto orderDto: orderDtos){
            OrderTM orderTM=new OrderTM(orderDto.getOrder_id(),orderDto.getCustomerID(),orderDto.getDeliveryID(),orderDto.getOrderDate(),orderDto.getTotalPrice(),orderDto.getStatus());
            orderTMObservableList.add(orderTM);
        }


    }
    public void loadPaymentToTable(ArrayList<PaymentsDto> paymentsDtos){
       for(PaymentsDto paymentsDto : paymentsDtos){
           PaymentsTM paymentsTM=new PaymentsTM(paymentsDto.getPayment_id(),paymentsDto.getOrderID(),paymentsDto.getPrice(),paymentsDto.getPaymentMethod(),paymentsDto.getPaymentDate(),paymentsDto.getStatus());
           paymentsTMObservableList.add(paymentsTM);
       }

    }


    public void initialize(URL url, ResourceBundle resourceBundle) {

        clmnCusID.setCellValueFactory(new PropertyValueFactory<>("customer_id"));
        clmnOIDcus.setCellValueFactory(new PropertyValueFactory<>("order_id"));
        clmnDelID.setCellValueFactory(new PropertyValueFactory<>("delivery_id"));
        clmnOrderDate.setCellValueFactory(new PropertyValueFactory<>("order_date"));
        clmnTotalPrice.setCellValueFactory(new PropertyValueFactory<>("total_price"));
        clmnStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        TableOrder.setItems(orderTMObservableList);


        clmnPid.setCellValueFactory(new PropertyValueFactory<>("payment_id"));
        clmnOIDpay.setCellValueFactory(new PropertyValueFactory<>("order_id"));
        clmnPricepay.setCellValueFactory(new PropertyValueFactory<>("price"));
        clmnPaymentMethod.setCellValueFactory(new PropertyValueFactory<>("payment_method"));
        clmnPaymentDAte.setCellValueFactory(new PropertyValueFactory<>("payment_date"));
        clmnStatusPAy.setCellValueFactory(new PropertyValueFactory<>("status"));

        TablePayment.setItems(paymentsTMObservableList);

        cmbPayMethod.getItems().addAll("cash","card");


        PaymentStatusGroup.selectedToggleProperty().addListener((observable, oldToggle, newToggle) -> getSelectedStatus());
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

    public void loadSelectedPaytoFIelds(){
       PaymentsTM selected=TablePayment.getSelectionModel().getSelectedItem();

       if(selected!=null){
           txtPayid.setText(String.valueOf(selected.getPayment_id()));
       }
    }

    public void setPayment() throws SQLException {
         paymentsDto=new PaymentsDto(Integer.parseInt(txtPayid.getText()),orderID,cmbPayMethod.getValue(),datePicker.getValue(),paymentStatus);
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

    public void tableMosueClick(MouseEvent mouseEvent) {
        loadSelectedPaytoFIelds();
    }
}
