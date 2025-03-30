package edu.ijse.baketrack.model;

import java.sql.SQLException;
import java.util.ArrayList;


import edu.ijse.baketrack.dto.PaymentsDto;

public interface PaymentInterface {
 void addPayment(PaymentsDto payment) throws SQLException;
    void deletePayment(int orderId) throws SQLException;
    void updatePayment(PaymentsDto payment, int orderId) throws SQLException;
    void getPaymentDetailsByOrderId(int orderId) throws SQLException;
    double getTotRevenue() throws SQLException;
    ArrayList<PaymentsDto> getAllPayments() throws SQLException;
}
