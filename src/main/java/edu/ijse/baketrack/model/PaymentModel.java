package edu.ijse.baketrack.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import edu.ijse.baketrack.db.DBobject;
import edu.ijse.baketrack.dto.PaymentsDto;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;

public class PaymentModel implements PaymentInterface{

    private Connection connection;

    public PaymentModel() throws ClassNotFoundException, SQLException {
        this.connection= DBobject.getInstance().getConnection();
    }

    public void addPayment(PaymentsDto payment) throws SQLException {
        String sql = "INSERT INTO payments (order_id, price, payment_method, payment_date) VALUES (?, ?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, payment.getOrderID());
        statement.setDouble(2, payment.getPrice());
        statement.setString(3, payment.getPaymentMethod());
        statement.setDate(4, Date.valueOf(payment.getPaymentDate()));

        int rowsAffected = statement.executeUpdate();
        if (rowsAffected > 0) {
            System.out.println("Payment added successfully");
        } else {
            System.out.println("Failed to add payment");
        }
    }

    public void deletePayment(int orderId) throws SQLException {
        String sql = "DELETE FROM payments WHERE order_id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, orderId);

        int rowsAffected = statement.executeUpdate();
        if (rowsAffected > 0) {
            System.out.println("Payment deleted successfully");
        } else {
            System.out.println("Failed to delete payment");
        }
    }

    public void updatePayment(PaymentsDto payment, int order_id) throws SQLException {
        String sql = "UPDATE payments SET price = ?, payment_method = ?, payment_date = ? WHERE order_id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setDouble(1, payment.getPrice());
        statement.setString(2, payment.getPaymentMethod());
        statement.setDate(3, java.sql.Date.valueOf(payment.getPaymentDate()));
        statement.setInt(4, order_id);

        int rowsAffected = statement.executeUpdate();
        if (rowsAffected > 0) {
            System.out.println("Payment updated successfully");
        } else {
            System.out.println("Failed to update payment");
        }
    }

     public ArrayList<PaymentsDto> getPaymentDetailsByOrderId(int orderId) {
        String sql = "SELECT * FROM payments WHERE order_id = ?";
        ArrayList<PaymentsDto> paymentsDtos=new ArrayList<>();


         try {
             PreparedStatement statement = connection.prepareStatement(sql);
             statement.setInt(1, orderId);
             ResultSet resultSet = statement.executeQuery();


             if (resultSet.next()) {
                 LocalDate pay_date=null;
                 PaymentsDto paymentsDto=new PaymentsDto(resultSet.getInt("payment_id"),
                         resultSet.getInt("order_id"),
                         resultSet.getDouble("price"),
                         resultSet.getString("payment_method"),
                         resultSet.getDate("payment_date").toLocalDate(),
                         resultSet.getString("status"));
                 paymentsDtos.add(paymentsDto);
                 return paymentsDtos;



            } else {
               return  null;
            }
         } catch (SQLException e) {
             throw new RuntimeException(e);
         }
     }

    public double getTotRevenue() throws SQLException {
        Double no_sales=0.0;
        String sql = "SELECT SUM(price) as Total_Revenue From payments";
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            return resultSet.getDouble("total_revenue");
        }
        return no_sales;
    }

    public ArrayList<PaymentsDto> getAllPayments() {
        String sql = "SELECT * FROM payments";
        ArrayList<PaymentsDto> paymentsList = new ArrayList<>();

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                PaymentsDto payment = new PaymentsDto(
                        resultSet.getInt("payment_id"),
                        resultSet.getInt("order_id"),
                        resultSet.getDouble("price"),
                        resultSet.getString("payment_method"),
                        resultSet.getDate("payment_date").toLocalDate(),
                        resultSet.getString("status")
                );
                paymentsList.add(payment);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException(e);
        }
        return paymentsList;
    }

    public String setPayments(PaymentsDto paymentsDto,int vehicle_id){
          connection=DBobject.getInstance().getConnection();


          try {
              PreparedStatement statement= null;
              try {
                  connection.setAutoCommit(false);
                  String PayUpdate="UPDATE payments SET payment_method=?,payment_date=?,status=? WHERE payment_id=?";
                  statement = connection.prepareStatement(PayUpdate);
                  statement.setString(1,paymentsDto.getPaymentMethod());
                  statement.setDate(2,Date.valueOf(paymentsDto.getPaymentDate()));
                  statement.setString(3,paymentsDto.getStatus());
                  statement.setInt(4,paymentsDto.getPayment_id());
              } catch (SQLException e) {
                  throw new RuntimeException(e);
              }

              if(statement.executeUpdate()>0){
                  PreparedStatement orderStatement= null;
                  try {
                      String orderStatus="UPDATE orders SET status=? WHERE order_id=?";
                      orderStatement = connection.prepareStatement(orderStatus);
                      orderStatement.setString(1,"delivered");
                      orderStatement.setInt(2,paymentsDto.getOrderID());
                  } catch (SQLException e) {
                      throw new RuntimeException(e);
                  }
                  if(orderStatement.executeUpdate()>0){
                      PreparedStatement vehiStatement= null;
                      try {
                          String vehicleSql="UPDATE vehicle SET status=? WHERE vehicle_id=?";
                          vehiStatement = connection.prepareStatement(vehicleSql);
                          vehiStatement.setString(1,"available");
                          vehiStatement.setInt(2,vehicle_id);
                      } catch (SQLException e) {
                          throw new RuntimeException(e);
                      }

                      if(vehiStatement.executeUpdate()>0){
                             connection.commit();
                             return "set Payment Done";
                         }else {
                             connection.rollback();
                             return "vehicle status error";
                         }

                   }else{
                       connection.rollback();
                       return "order Status Update error";
                   }
              }else{
                 connection.rollback();
                 return "update payment error";
              }

          } catch (Exception e) {
              throw new RuntimeException(e);
          } finally {
              try {
                  connection.setAutoCommit(true);
              } catch (SQLException e) {
                  throw new RuntimeException(e);
              }
          }
    }

}
