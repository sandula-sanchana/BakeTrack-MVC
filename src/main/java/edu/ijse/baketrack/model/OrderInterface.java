package edu.ijse.baketrack.model;

import java.sql.SQLException;

import edu.ijse.baketrack.dto.OrderDto;

public interface OrderInterface {
 void addOrders(OrderDto orders) throws SQLException;
    void updateOrder(OrderDto orderDto, int orderId) throws SQLException;
    void deleteOrder(int orderId) throws SQLException;
    void printAllOrders() throws SQLException;
    //void getOrderById(int orderId) throws SQLException;
}
