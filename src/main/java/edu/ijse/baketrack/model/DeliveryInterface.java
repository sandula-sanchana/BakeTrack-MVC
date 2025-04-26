package edu.ijse.baketrack.model;

import java.sql.SQLException;
import java.util.ArrayList;

import edu.ijse.baketrack.dto.DeliveryDto;

public interface DeliveryInterface {
    void addDelivery(DeliveryDto deliveryDto) throws SQLException;

    String deleteDelivery(int deliveryId) throws SQLException;

    void updateDeliveryStatus(int deliveryId, String status) throws SQLException;

    String updateDelivery( DeliveryDto deliveryDto) throws SQLException;

    public int getVehicleIDbyDelID(int delivery_id) throws SQLException;

    ArrayList<DeliveryDto> getAllDelivery() throws SQLException;

    ArrayList<DeliveryDto> getUnassignedDeliveries() throws SQLException;

    public String setDelivery(DeliveryDto deliveryDto,String orderID) throws SQLException;

    public String setEmployeeForDelivery(int del_id,int emp_id) throws SQLException;
}
