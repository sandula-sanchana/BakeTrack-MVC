package edu.ijse.baketrack.controller;

import edu.ijse.baketrack.dto.OrderDetailDto;
import edu.ijse.baketrack.dto.OrderDto;
import edu.ijse.baketrack.dto.ProductDto;
import edu.ijse.baketrack.dto.ProductIngredientDto;
import edu.ijse.baketrack.dto.tm.IngredientTM;
import edu.ijse.baketrack.dto.tm.OrderDetailTM;
import edu.ijse.baketrack.dto.tm.OrderTM;
import edu.ijse.baketrack.model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public  class setProductionCrudPageController implements Initializable {
    public Label lblCusName;
    public Label lblNo;
    public TableView<OrderDetailTM> tableOrderDetail;
    public TableColumn<OrderDetailTM, Integer> colODetailOid;
    public TableColumn<OrderDetailTM, Integer> OdetailPid;
    public TableColumn<OrderDetailTM, Integer> OdetailQty;
    public TableColumn<OrderDetailTM, Double> OdetailPrice;
    public TableColumn<IngredientTM, String> colPName;
    private ObservableList<OrderTM> orderTMObservableList = FXCollections.observableArrayList();
    private ObservableList<OrderDetailTM> orderDetailTMObservableList = FXCollections.observableArrayList();
    private OrderInterface orderInterface;
    private OrderTM orderTM;
    private OrderDetailTM orderDetailTM;
    private OrderDetailInterface orderDetailInterface;
    private ProductInterface productInterface;
    private ProductIngredientInterface productIngredientInterface;
    private IngredientTM ingredientTM;
    private IngredientModel ingredientModel;
    private ObservableList<IngredientTM> ingredientTMObservableList = FXCollections.observableArrayList();
    private int order_id;


    public setProductionCrudPageController() {
        try {
            orderInterface = new OrdersModel();
            orderDetailInterface = new OrderDetailModel();
            productInterface = new ProductModel();
            productIngredientInterface = new ProductIngredientModel();
            ingredientModel = new IngredientModel();
            productInterface = new ProductModel();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private Button btnClear;

    @FXML
    private Button btnLoadIngredients;

    @FXML
    private Button btnStartProduction;

    @FXML
    private TableColumn<IngredientTM, Integer> colIngId;

    @FXML
    private TableColumn<IngredientTM, String> colIngName;

    @FXML
    private TableColumn<IngredientTM, Integer> colQtyRequired;

    @FXML
    private ComboBox<OrderTM> comboOrderId;

    @FXML
    private TableView<IngredientTM> tblIngredients;

    @FXML
    void btnClearAction(ActionEvent event) {
       clearAll();
    }

    @FXML
    void btnGoBack(ActionEvent event) {

    }


    @FXML
    void btnStartProductionAction(ActionEvent event) {
     startProduction();
    }

    public void loadOrderToCmb() {
        try {
            ArrayList<OrderDto> orderDtoArrayList = orderInterface.getAllPendingOrders();
            if (orderDtoArrayList != null) {
                for (OrderDto orderDto : orderDtoArrayList) {
                    orderTM = new OrderTM(orderDto.getOrder_id(), orderDto.getCustomerID(), orderDto.getOrderDate(), orderDto.getTotalPrice());
                    orderTMObservableList.add(orderTM);
                }
            } else {
                new Alert(Alert.AlertType.ERROR, "not found").showAndWait();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colODetailOid.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        OdetailPid.setCellValueFactory(new PropertyValueFactory<>("productId"));
        OdetailQty.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        OdetailPrice.setCellValueFactory(new PropertyValueFactory<>("priceAtOrder"));

        tableOrderDetail.setItems(orderDetailTMObservableList);

        colIngId.setCellValueFactory(new PropertyValueFactory<>("ingredient_id"));
        colIngName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colQtyRequired.setCellValueFactory(new PropertyValueFactory<>("total_amount_need"));
        colPName.setCellValueFactory(new PropertyValueFactory<>("product_name"));

        tblIngredients.setItems(ingredientTMObservableList);

        loadOrderToCmb();
        comboOrderId.setItems(orderTMObservableList);
    }

    public void loadOrderDetails() {
        ArrayList<OrderDetailDto> orderDetailDtoArrayList;
        orderTM = comboOrderId.getValue();

        if (orderTM != null) {
            try {
                orderDetailDtoArrayList = orderDetailInterface.getOrderDetailsByOrderID(orderTM.getOrder_id());
                if (orderDetailDtoArrayList != null) {
                    for (OrderDetailDto orderDetailDto : orderDetailDtoArrayList) {
                        OrderDetailTM orderDetailTM = new OrderDetailTM(orderDetailDto.getOrderID(), orderDetailDto.getProductID(),
                                orderDetailDto.getQuantity(), orderDetailDto.getPriceAtOrder());
                        orderDetailTMObservableList.add(orderDetailTM);
                    }
                } else {
                    new Alert(Alert.AlertType.ERROR, "not found").showAndWait();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void btnLoadOdetails(ActionEvent actionEvent) {
        loadOrderDetails();
    }

    public void btnGetReqAmount(ActionEvent actionEvent) {
        getReqAmountPerProduct();

    }

    public void getReqAmountPerProduct() {
        orderDetailTM = tableOrderDetail.getSelectionModel().getSelectedItem();

        if (orderDetailTM == null) {
            new Alert(Alert.AlertType.WARNING, "Please select a product from the table first.").showAndWait();
            return;
        }


        try {
            ArrayList<ProductIngredientDto> productIngredientDtoArrayList = productIngredientInterface.getProductIngredientByPid(orderDetailTM.getProductId());
            if (productIngredientDtoArrayList != null) {
                for (ProductIngredientDto pDto : productIngredientDtoArrayList) {
                    String product_name = productInterface.getProductNameById(orderDetailTM.getProductId());
                    int req_amount_per_product = pDto.getQuantity_per_product();
                    int total_needed_products_count = orderDetailTM.getQuantity();
                    int total_ingredient_needed = req_amount_per_product * total_needed_products_count;
                    String ingre_name = ingredientModel.getIngredientNameById(pDto.getIngredient_id());

                    if (ingre_name != null) {
                        ingredientTM = new IngredientTM(product_name, pDto.getIngredient_id(), ingre_name, total_ingredient_needed);
                        ingredientTMObservableList.add(ingredientTM);
                    } else {
                        new Alert(Alert.AlertType.ERROR, " ingredient not found").showAndWait();
                    }
                }
            } else {
                new Alert(Alert.AlertType.ERROR, "product ingredient not found").showAndWait();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void startProduction() {
        if (comboOrderId.getValue() == null) {
            new Alert(Alert.AlertType.WARNING, "Please select an order first.").showAndWait();
            return;
        }

         orderTM=comboOrderId.getValue();
        order_id=orderTM.getOrder_id();

        if (ingredientTMObservableList.isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Please calculate required ingredients before starting production.").showAndWait();
            return;
        }

        for (IngredientTM ing : ingredientTMObservableList) {
            try {
                int stock = ingredientModel.getStockById(ing.getIngredient_id());
                if (stock < ing.getTotal_amount_need()) {
                    new Alert(Alert.AlertType.ERROR, "Not enough stock for ingredient: " + ing.getName()).showAndWait();
                    return;
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        try {
            boolean done=orderInterface.startProductionAndDeductIng(ingredientTMObservableList,order_id);

            if(done){
                new Alert(Alert.AlertType.INFORMATION, "set production successfully").showAndWait();
            }else {
                new Alert(Alert.AlertType.WARNING, "error").showAndWait();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    private void clearAll() {
        comboOrderId.getSelectionModel().clearSelection();
        orderDetailTMObservableList.clear();
        ingredientTMObservableList.clear();
        lblCusName.setText("");
        lblNo.setText("");
    }

}
