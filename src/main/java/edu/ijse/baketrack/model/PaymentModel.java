package edu.ijse.baketrack.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import edu.ijse.baketrack.db.DBobject;
import edu.ijse.baketrack.dto.PaymentsDto;

import java.sql.Date;
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

     public void getPaymentDetailsByOrderId(int orderId) throws SQLException {
        String sql = "SELECT * FROM payments WHERE order_id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, orderId);
        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            System.out.println("Price: " + resultSet.getDouble("price"));
            System.out.println("Payment Method: " + resultSet.getString("payment_method"));
            System.out.println("Payment Date: " + resultSet.getDate("payment_date"));
        } else {
            System.out.println("No payment found for Order ID: " + orderId);
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
                        resultSet.getDate("payment_date").toLocalDate()
                );
                paymentsList.add(payment);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException(e);
        }
        return paymentsList;
    }

}
