package edu.ijse.baketrack.model;

import java.sql.SQLException;

import edu.ijse.baketrack.dto.OrderDetail;

public interface OrderDetailInterface {
    void addOrderDetails(OrderDetail orderDetail) throws SQLException;

    void updateOrderDetail(OrderDetail orderDetail, int order_id, int product_id) throws SQLException;

    void deleteOrderDetail(int order_id, int product_id) throws SQLException;

    void printOrderDetailsByOrderID(int order_id) throws SQLException;
}
