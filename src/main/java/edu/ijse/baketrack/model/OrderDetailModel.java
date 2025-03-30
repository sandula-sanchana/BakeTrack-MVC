package edu.ijse.baketrack.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import edu.ijse.baketrack.db.DBobject;
import edu.ijse.baketrack.dto.OrderDetailDto;

public class OrderDetailModel implements OrderDetailInterface {

    private Connection connection;

    public OrderDetailModel() throws ClassNotFoundException, SQLException {
        this.connection= DBobject.getInstance().getConnection();
    }

    public void addOrderDetails(OrderDetailDto orderDetailDto) throws SQLException {
        String sql = "INSERT INTO order_details (product_id, order_id, quantity, price_at_order) VALUES (?, ?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, orderDetailDto.getProductID());
        statement.setInt(2, orderDetailDto.getOrderID());
        statement.setInt(3, orderDetailDto.getQuantity());
        statement.setDouble(4, orderDetailDto.getPriceAtOrder());

        int rowsAffected = statement.executeUpdate();
        System.out.println(rowsAffected > 0 ? "Order detail added successfully" : "Failed to add order detail");
    }

    public void updateOrderDetail(OrderDetailDto orderDetailDto, int order_id, int product_id) throws SQLException {
        String sql = "UPDATE order_details SET quantity=?, price_at_order=? WHERE order_id=? AND product_id=?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, orderDetailDto.getQuantity());
        statement.setDouble(2, orderDetailDto.getPriceAtOrder());
        statement.setInt(3, order_id);
        statement.setInt(4, product_id);

        int rowsAffected = statement.executeUpdate();
        System.out.println(rowsAffected > 0 ? "Order detail updated successfully" : "Failed to update order detail");
    }

    public void deleteOrderDetail(int order_id, int product_id) throws SQLException {
        String sql = "DELETE FROM order_details WHERE order_id=? AND product_id=?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, order_id);
        statement.setInt(2, product_id);

        int rowsAffected = statement.executeUpdate();
        System.out.println(rowsAffected > 0 ? "Order detail deleted successfully" : "Failed to delete order detail");
    }


     public void printOrderDetailsByOrderID(int order_id) throws SQLException {
        String sql = "SELECT * FROM order_details WHERE order_id=?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, order_id);
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            int productId = resultSet.getInt("product_id");
            int quantity = resultSet.getInt("quantity");
            double priceAtOrder = resultSet.getDouble("price_at_order");

            System.out.println("Order ID: " + order_id + ", Product ID: " + productId +
                    ", Quantity: " + quantity + ", Price at Order: " + priceAtOrder);
        }
    }

    public ArrayList<OrderDetailDto> getAllOrderDetails() {
        String sql = "SELECT * FROM order_detail";
        ArrayList<OrderDetailDto> orderDetailsList = new ArrayList<>();

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                OrderDetailDto orderDetail = new OrderDetailDto(
                        resultSet.getInt("product_id"),
                        resultSet.getInt("order_id"),

                        resultSet.getInt("quantity"),
                        resultSet.getDouble("price_at_order")
                );
                orderDetailsList.add(orderDetail);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException(e);
        }
        return orderDetailsList;
    }

}
