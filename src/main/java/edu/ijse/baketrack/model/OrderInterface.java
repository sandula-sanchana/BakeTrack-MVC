package edu.ijse.baketrack.model;

import java.sql.SQLException;
import java.util.ArrayList;

import edu.ijse.baketrack.dto.OrderDetailDto;
import edu.ijse.baketrack.dto.OrderDto;

public interface OrderInterface {
 void addOrders(OrderDto orders) throws SQLException;
    void updateOrder(OrderDto orderDto, int orderId) throws SQLException;
    void deleteOrder(int orderId) throws SQLException;
    ArrayList<OrderDto> getAllOrders() throws SQLException;
    String placeOrder(OrderDto orderDto, ArrayList<OrderDetailDto> orderDetailDtos) throws SQLException;
}
