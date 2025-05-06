package edu.ijse.baketrack.model;

import java.sql.SQLException;
import java.util.ArrayList;

import edu.ijse.baketrack.dto.OrderDetailDto;

public interface OrderDetailInterface {
    void addOrderDetails(OrderDetailDto orderDetailDto) throws SQLException;

    void updateOrderDetail(OrderDetailDto orderDetailDto, int order_id, int product_id) throws SQLException;

    void deleteOrderDetail(int order_id, int product_id) throws SQLException;

    ArrayList<OrderDetailDto> getOrderDetailsByOrderID(int order_id) throws SQLException;

    ArrayList<OrderDetailDto> getAllOrderDetails() throws  SQLException;
}
