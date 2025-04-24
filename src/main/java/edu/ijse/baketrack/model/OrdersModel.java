package edu.ijse.baketrack.model;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import edu.ijse.baketrack.db.DBobject;
import edu.ijse.baketrack.dto.OrderDetailDto;
import edu.ijse.baketrack.dto.OrderDto;
import edu.ijse.baketrack.util.SqlExecute;

import java.sql.Types.*;

public class OrdersModel implements OrderInterface{

    private Connection connection;

    public OrdersModel() throws ClassNotFoundException, SQLException {
        this.connection = DBobject.getInstance().getConnection();
    }

    public void addOrders(OrderDto orders) throws SQLException {
        String addSql = "INSERT INTO orders (customer_id,delivery_id,order_date,total_price,status) VALUES (?,?,?,?,?)";
        PreparedStatement statement = connection.prepareStatement(addSql);
        statement.setInt(1, orders.getCustomerID());
        statement.setInt(2,orders.getDeliveryID());
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

    public String updateOrder(OrderDto orderDto) throws SQLException {
        String updateSql = "UPDATE orders SET customer_id=?, delivery_id=?, order_date=?, total_price=?, status=? WHERE order_id=?";

        PreparedStatement statement = connection.prepareStatement(updateSql) ;
            statement.setInt(1, orderDto.getCustomerID());
            statement.setInt(2, orderDto.getDeliveryID());
            statement.setDate(3, Date.valueOf(orderDto.getOrderDate()));
            statement.setDouble(4, orderDto.getTotalPrice());
            statement.setString(5, orderDto.getStatus());
            statement.setInt(6, orderDto.getOrder_id());

            int rowsAffected = statement.executeUpdate();
            return (rowsAffected > 0 ? "Order updated successfully" : "Failed to update order");
        
    }

    public String deleteOrder(int orderId) throws SQLException {
        String deleteSql = "DELETE FROM orders WHERE order_id = ?";

        PreparedStatement statement = connection.prepareStatement(deleteSql);
            statement.setInt(1, orderId);

            int rowsAffected = statement.executeUpdate();
           return rowsAffected > 0 ? "Order & order_Detail,Payments deleted successfully" : "Failed to delete order";
        }


    public ArrayList<OrderDto> getAllOrders() {
        String query = "SELECT * FROM orders";
        ArrayList<OrderDto> ordersList = new ArrayList<>();

        try {
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                OrderDto order = new OrderDto(
                        rs.getInt("order_id"),
                        rs.getInt("customer_id"),
                        rs.getInt("delivery_id"),
                        rs.getDate("order_date").toLocalDate(),
                        rs.getDouble("total_price"),
                        rs.getString("status")// my database status need to be ENUM , I will fix it later :)
                );
                ordersList.add(order);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException(e);
        }
        return ordersList;
    }


    public String placeOrder(OrderDto orderDto, ArrayList<OrderDetailDto> orderDetail) throws SQLException {

        connection=DBobject.getInstance().getConnection();

           try{
               connection.setAutoCommit(false);

               String addSql = "INSERT INTO orders (customer_id,delivery_id,order_date,total_price,status) VALUES (?,?,?,?,?)";
               PreparedStatement statement = connection.prepareStatement(addSql, Statement.RETURN_GENERATED_KEYS);
               statement.setInt(1, orderDto.getCustomerID());
               statement.setNull(2,-1);
               statement.setDate(3, Date.valueOf(orderDto.getOrderDate()));
               statement.setDouble(4, orderDto.getTotalPrice());
               statement.setString(5, orderDto.getStatus());
               statement.executeUpdate();

               ResultSet resultSet=statement.getGeneratedKeys();
               int orderId=-1;
               if(resultSet.next()){
                       orderId=resultSet.getInt(1);
                   String orderDetailsql = "INSERT INTO order_detail (product_id, order_id, quantity, price_at_order) VALUES (?, ?, ?, ?)";
                   boolean orderDetailsResult=true;
                   for(OrderDetailDto orderDetailDto : orderDetail) {
                       Boolean done=SqlExecute.SqlExecute(orderDetailsql,orderDetailDto.getProductID(), orderId,
                               orderDetailDto.getQuantity(),orderDetailDto.getPriceAtOrder());
                       if (!done){
                           orderDetailsResult=false;
                       }
                   }

                   if (orderDetailsResult){
                       String Quantitysql = "UPDATE product SET total_quantity=(total_quantity-?) WHERE product_id = ?";
                       boolean quantitySaved=true;
                       for (OrderDetailDto orderDetailDto: orderDetail) {
                           Boolean UpdateDone=SqlExecute.SqlExecute(Quantitysql,orderDetailDto.getQuantity(),orderDetailDto.getProductID());
                         if(!UpdateDone){
                             quantitySaved=false;
                         }
                       }
                       if(quantitySaved){
                             String paymentSql="INSERT INTO payments (order_id,price,payment_method,payment_date,status) VALUES (?,?,?,?,?)";
//                             PreparedStatement paymentStatement=connection.prepareStatement(paymentSql);
//                             paymentStatement.setInt(1,orderId);
//                             paymentStatement.setDouble(2,orderDto.getTotalPrice());
//                             paymentStatement.setNull(3,-1);
//                             paymentStatement.setNull(4, Types.DATE);
//                             paymentStatement.setString(5,"pending");
                             Boolean paymentDone=SqlExecute.SqlExecute(paymentSql,orderId,orderDto.getTotalPrice(),null,null,"pending");

                             if(paymentDone){
                                 connection.commit();
                                 return "setOrder done, payment pending";
                             }else {
                                 connection.rollback();
                                 return "payment table set error";
                             }
                       }else{
                           connection.rollback();
                           return "Quantity update failed";
                       }
                   }else{
                       connection.rollback();
                       return  "order detail update error";
                   }
               }
               else {
                   connection.rollback();
                   return "generated key not found";
               }


           } catch (Exception e) {
               connection.rollback();
               throw new RuntimeException(e);
           }
           finally {
                connection.setAutoCommit(true);
           }
    }

    public ArrayList<OrderDto> getOrderByID(int orderID) throws SQLException{
        String query = "SELECT * FROM orders WHERE order_id=?";
        ArrayList<OrderDto> ordersList = new ArrayList<>();

        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1,orderID);

            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                OrderDto order = new OrderDto(
                        rs.getInt("order_id"),
                        rs.getInt("customer_id"),
                        rs.getInt("delivery_id"),
                        rs.getDate("order_date").toLocalDate(),
                        rs.getDouble("total_price"),
                        rs.getString("status")// my database status need to be ENUM , I will fix it later :)
                );
                ordersList.add(order);
                return ordersList;
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException(e);
        }
        return null;
    }

    public ArrayList<OrderDto> getOrderByDelID(String delID) {
        String query = "SELECT * FROM orders WHERE delivery_id=?";
        ArrayList<OrderDto> ordersList = new ArrayList<>();

        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1,Integer.parseInt(delID));

            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                OrderDto order = new OrderDto(
                        rs.getInt("order_id"),
                        rs.getInt("customer_id"),
                        rs.getInt("delivery_id"),
                        rs.getDate("order_date").toLocalDate(),
                        rs.getDouble("total_price"),
                        rs.getString("status")
                );
                ordersList.add(order);
                return ordersList;
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException(e);
        }
        return null;
    }

}
