package edu.ijse.baketrack.model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import edu.ijse.baketrack.dto.OrderDetailDto;
import edu.ijse.baketrack.dto.OrderDto;
import edu.ijse.baketrack.dto.OrderTrendDto;
import edu.ijse.baketrack.dto.tm.IngredientTM;
import javafx.collections.ObservableList;

public interface OrderInterface {
 void addOrders(OrderDto orders) throws SQLException;
    public String updateOrder(OrderDto orderDto) throws SQLException;
    public String deleteOrder(int orderId) throws SQLException;
    ArrayList<OrderDto> getAllOrders() throws SQLException;
    String placeOrder(OrderDto orderDto, ArrayList<OrderDetailDto> orderDetailDtos) throws SQLException;
    ArrayList<OrderDto> getOrderByID(int orderID) throws SQLException;
    public ArrayList<OrderDto> getOrderByDelID(String delID)throws SQLException;
    ArrayList<OrderDto> getAllPendingOrders()throws SQLException;

   boolean startProductionAndDeductIng(ObservableList<IngredientTM> ingredientTMObservableList, int order_id)throws SQLException;

    List<OrderTrendDto> getOrderTrends() throws SQLException;
}
