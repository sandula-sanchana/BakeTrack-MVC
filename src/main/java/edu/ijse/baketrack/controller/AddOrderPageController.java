package edu.ijse.baketrack.controller;

import edu.ijse.baketrack.dto.CustomersDto;
import edu.ijse.baketrack.dto.OrderDetailDto;
import edu.ijse.baketrack.dto.OrderDto;
import edu.ijse.baketrack.dto.ProductDto;
import edu.ijse.baketrack.model.*;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AddOrderPageController implements Initializable {

    public Label lblPriceAtOrder;
    public TableColumn<OrderDetailDto,Integer> clmnPID;
    public TableColumn<OrderDetailDto,Integer> clmnQty;
    public TableColumn<OrderDetailDto,Double> clmnPatOrder;
    public Label lblTotalPrice;
    private CustomerInterface customerInterface=new CustomerModel();
    private ProductInterface productInterface=new ProductModel();
    private OrderInterface orderInterface=new OrdersModel();
    private ArrayList<OrderDetailDto> orderDetailDtoList = new ArrayList<>();
    private ArrayList<Double> total_price_array=new ArrayList<>();


    @FXML
    private TableView<OrderDetailDto> addOrderPageTable;

    @FXML
    private Label lblCusData;

    @FXML
    private HBox lblPriceAROrder;

    @FXML
    private Label lblProductData;

    @FXML
    private TextField txtOrderPageCusID;

    @FXML
    private TextField txtOrderPagePid;

    @FXML
    private TextField txtOrderPageQty;

    public AddOrderPageController() throws SQLException, ClassNotFoundException {
    }


    @FXML
    void OrderPageGoBackButton(ActionEvent event) {

    }

    @FXML
    void btnAddOrderDetail(ActionEvent event) {
        addOrderDetailToTable();
        calculateTotalPricePerProduct();
        String y=String.valueOf(getTotalPriceOfOrder());
        lblTotalPrice.setText(y);

    }

    @FXML
    void btnSearchCid(ActionEvent event) throws SQLException {
         getCustomerByID();
    }

    @FXML
    void btnSearchPid(ActionEvent event) throws SQLException {
         getProductByID();
         getPriceAtOrder();
    }

    public void btnPlaceOrder(ActionEvent actionEvent) throws SQLException {
        placeOrder();
    }

    public void getCustomerByID() throws SQLException {
        int id_input=Integer.parseInt(txtOrderPageCusID.getText());
        CustomersDto customer= customerInterface.getCustomerByID(id_input);
        if(customer!=null){
            lblCusData.setText(customer.getName()+"-"+customer.getAddress()+"-"+customer.getContact());
        }
        else {
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setTitle("customer not found");
            alert.showAndWait();
        }
    }

    public void getProductByID() throws SQLException {
        int id_input_product=Integer.parseInt(txtOrderPagePid.getText());
        ProductDto productDto=productInterface.getProductDetailsByProductID(id_input_product);
        if(productDto!=null){
            lblProductData.setText(productDto.getName()+"--"+productDto.getCategory()+"--"+productDto.getDescription()+"--"+productDto.getPrice());
        }
        else {
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setTitle("product not found");
            alert.showAndWait();
        }
    }

    public  void addOrderDetailToTable(){
        OrderDetailDto orderDetailDto=new OrderDetailDto(Integer.parseInt(txtOrderPagePid.getText()),Integer.parseInt(txtOrderPageQty.getText()),Double.parseDouble(lblPriceAtOrder.getText()));
        orderDetailDtoList.add(orderDetailDto);
        addOrderPageTable.getItems().clear();
        addOrderPageTable.getItems().addAll(orderDetailDtoList);


    }



    public void getPriceAtOrder() throws SQLException {
        double price_at_order=productInterface.getPriceAtOrder(Integer.parseInt(txtOrderPagePid.getText()));
        lblPriceAtOrder.setText(Double.toString(price_at_order));


    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        clmnPID.setCellValueFactory(cellData ->
                new SimpleObjectProperty<>(cellData.getValue().getProductID()));

        clmnQty.setCellValueFactory(cellData ->
                new SimpleObjectProperty<>(cellData.getValue().getQuantity()));

        clmnPatOrder.setCellValueFactory(cellData ->
                new SimpleObjectProperty<>(cellData.getValue().getPriceAtOrder()));


        addOrderPageTable.setItems(FXCollections.observableArrayList(orderDetailDtoList));
    }

    public void placeOrder() throws SQLException {
        LocalDate today_date=LocalDate.now();
        Double x=getTotalPriceOfOrder();
        OrderDto orderDto=new OrderDto(Integer.parseInt(txtOrderPageCusID.getText()),today_date,x,"pending");
        String resp=orderInterface.placeOrder(orderDto,orderDetailDtoList);

        Alert alert=new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(resp);
        alert.showAndWait();


    }

    public void calculateTotalPricePerProduct(){
        int Quantity=Integer.parseInt(txtOrderPageQty.getText());
        double price_at_order= Double.parseDouble(lblPriceAtOrder.getText());
        Double totalPricePerProduct=Quantity*price_at_order;
        total_price_array.add(totalPricePerProduct);

    }
    public Double getTotalPriceOfOrder(){
        Double totalPriceOfOrder=0.0;
        for(Double price_per_order : total_price_array){
             totalPriceOfOrder+=price_per_order;
        }

        return totalPriceOfOrder;
    }




    public void lblPriceAtOrder(MouseEvent mouseEvent) {
    }


}
