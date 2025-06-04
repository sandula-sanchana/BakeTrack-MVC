package edu.ijse.baketrack.model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;


import edu.ijse.baketrack.dto.PaymentsDto;

public interface PaymentInterface {
 void addPayment(PaymentsDto payment) throws SQLException;
    String deletePayment(int orderId) throws SQLException;
    String updatePayment(PaymentsDto payment) throws SQLException;
    ArrayList<PaymentsDto> getPaymentDetailsByOrderId(int orderId) throws SQLException;
    double getTotRevenue() throws SQLException;
    ArrayList<PaymentsDto> getAllPayments() throws SQLException;
   public String setPayments(PaymentsDto paymentsDto,int vehicle_id)throws SQLException;
    Map<String,Integer> getPaymentCount() throws SQLException;
}
