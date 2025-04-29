package edu.ijse.baketrack.controller;

import edu.ijse.baketrack.dto.CustomersDto;
import edu.ijse.baketrack.dto.OrderDetailDto;
import edu.ijse.baketrack.dto.OrderDto;
import edu.ijse.baketrack.dto.ProductDto;
import edu.ijse.baketrack.model.*;
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
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;
import edu.ijse.baketrack.dto.tm.OrderDetailTM;


public class AddOrderPageController implements Initializable {

    public Label lblPriceAtOrder;
    public TableColumn<OrderDetailTM,Integer> clmnPID;
    public TableColumn<OrderDetailTM,Integer> clmnQty;
    public TableColumn<OrderDetailTM,Double> clmnPatOrder;
    public Label lblTotalPrice;
    public AnchorPane apOrderPage;
    private CustomerInterface customerInterface=new CustomerModel();
    private ProductInterface productInterface=new ProductModel();
    private OrderInterface orderInterface=new OrdersModel();
    private ArrayList<OrderDetailDto> orderDetailDtoList = new ArrayList<>();
    private Double total_price=0.0;
    private ObservableList<OrderDetailTM> orderDetailTMs = FXCollections.observableArrayList();
    OrderDetailTM newItem;



    @FXML
    private TableView<OrderDetailTM> addOrderPageTable;

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
        try {
            apOrderPage.getChildren().clear();
            AnchorPane ap= FXMLLoader.load(getClass().getResource("/View/OwnerDashboard.fxml"));
            apOrderPage.getChildren().add(ap);
        } catch (IOException e) {
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setTitle("not found");
            alert.showAndWait();
            throw new RuntimeException(e);
        }

    }

//    @FXML
//    void btnAddOrderDetail(ActionEvent event) throws SQLException {
//        addOrderDetailToTable();
//        calculateTotalPricePerProduct();
//        String y=String.valueOf(getTotalPriceOfOrder());
//        lblTotalPrice.setText(y);
//
//    }

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

    @FXML
    void btnAddOrderDetail(ActionEvent event) {
        try {
            int pid = Integer.parseInt(txtOrderPagePid.getText());
            int qty = Integer.parseInt(txtOrderPageQty.getText());
            double price = Double.parseDouble(lblPriceAtOrder.getText());

            boolean found = false;
            for (OrderDetailTM item : orderDetailTMs) {
                if (item.getProductId() == pid) {
                    item.setQuantity(item.getQuantity() + qty);
                    found = true;
                    break;
                }
            }


            if (!found) {
                newItem = new OrderDetailTM(pid, qty, price);
                orderDetailTMs.add(newItem);
            }

            addOrderPageTable.refresh();
            updateTotalPriceLabel();


        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Error: " + e.getMessage()).show();
            e.printStackTrace();
        }
    }



    public void getPriceAtOrder() throws SQLException {
        double price_at_order=productInterface.getPriceAtOrder(Integer.parseInt(txtOrderPagePid.getText()));
        lblPriceAtOrder.setText(Double.toString(price_at_order));

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        clmnPID.setCellValueFactory(new PropertyValueFactory<>("productId"));
        clmnQty.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        clmnPatOrder.setCellValueFactory(new PropertyValueFactory<>("priceAtOrder"));

        orderDetailTMs = FXCollections.observableArrayList();
        addOrderPageTable.getItems().clear();
        addOrderPageTable.setItems(orderDetailTMs);
        addOrderPageTable.refresh();
    }

    public void placeOrder() throws SQLException {

        orderDetailDtoList.clear();
        for (OrderDetailTM tm : orderDetailTMs) {
            orderDetailDtoList.add(new OrderDetailDto(tm.getProductId(), tm.getQuantity(), tm.getPriceAtOrder()));
        }

        LocalDate today_date=LocalDate.now();
        OrderDto orderDto=new OrderDto(Integer.parseInt(txtOrderPageCusID.getText()),today_date,total_price,"pending");
        String resp=orderInterface.placeOrder(orderDto,orderDetailDtoList);

        Alert alert=new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(resp);
        alert.showAndWait();


    }

    public void updateTotalPriceLabel() {
        total_price = 0.0;
        for (OrderDetailTM item : orderDetailTMs) {
            total_price += item.getQuantity() * item.getPriceAtOrder();
        }
        lblTotalPrice.setText(String.valueOf(total_price));
    }



    public void lblPriceAtOrder(MouseEvent mouseEvent) {
    }


}
