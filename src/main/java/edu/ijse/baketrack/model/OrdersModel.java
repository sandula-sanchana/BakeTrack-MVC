package edu.ijse.baketrack.model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import edu.ijse.baketrack.dto.OrderDto;
import edu.ijse.baketrack.db.DBobject;

public class OrdersModel implements OrderInterface{

    private Connection connection;

    public OrdersModel() throws ClassNotFoundException, SQLException {
        this.connection = DBobject.getInstance().getConnection();
    }

    public void addOrders(OrderDto orders) throws SQLException {
        String addSql = "INSERT INTO orders (customer_id,delivery_id,order_date,total_price,status) VALUES (?,?,?,?,?)";
        PreparedStatement statement = connection.prepareStatement(addSql);
        statement.setInt(1, orders.getCustomerID());
        statement.setInt(2, orders.getDeliveryID());
        statement.setDate(3, Date.valueOf(orders.getOrderDate()));
        statement.setDouble(4, orders.getTotalPrice());
        statement.setString(5, orders.getStatus());

        int rowsAffected = statement.executeUpdate();

        if (rowsAffected > 0) {
            System.out.println("orders added successfully");
        } else {
            System.out.println("failed");
        }

    }

    public void updateOrder(OrderDto orderDto, int order_id) throws SQLException {
        String updateSql = "UPDATE orders SET customer_id=?, delivery_id=?, order_date=?, total_price=?, status=? WHERE order_id=?";

        PreparedStatement statement = connection.prepareStatement(updateSql) ;
            statement.setInt(1, orderDto.getCustomerID());
            statement.setInt(2, orderDto.getDeliveryID());
            statement.setDate(3, Date.valueOf(orderDto.getOrderDate()));
            statement.setDouble(4, orderDto.getTotalPrice());
            statement.setString(5, orderDto.getStatus());
            statement.setInt(6, order_id);

            int rowsAffected = statement.executeUpdate();
            System.out.println(rowsAffected > 0 ? "Order updated successfully" : "Failed to update order");
        
    }

    public void deleteOrder(int orderId) throws SQLException {
        String deleteSql = "DELETE FROM orders WHERE order_id = ?";

        PreparedStatement statement = connection.prepareStatement(deleteSql);
            statement.setInt(1, orderId);

            int rowsAffected = statement.executeUpdate();
            System.out.println(rowsAffected > 0 ? "Order deleted successfully" : "Failed to delete order");
        }
    

    public void printAllOrders() throws SQLException {
        String query = "SELECT * FROM orders";

        PreparedStatement statement = connection.prepareStatement(query);
                ResultSet rs = statement.executeQuery(); 

            while (rs.next()) {
                int orderId = rs.getInt("order_id");
                int customerId = rs.getInt("customer_id");
                int deliveryId = rs.getInt("delivery_id");
                LocalDate orderDate = rs.getDate("order_date").toLocalDate();
                double totalPrice = rs.getDouble("total_price");
                String status = rs.getString("status");

                System.out.println("Order ID: " + orderId + ", Customer ID: " + customerId +
                        ", Delivery ID: " + deliveryId + ", Order Date: " + orderDate +
                        ", Total Price: " + totalPrice + ", Status: " + status);
            }
        }
    

}
