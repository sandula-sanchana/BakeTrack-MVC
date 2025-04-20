package edu.ijse.baketrack.model;

import java.sql.SQLException;
import java.util.ArrayList;

import edu.ijse.baketrack.dto.DeliveryDto;

public interface DeliveryInterface {
    void addDelivery(DeliveryDto deliveryDto) throws SQLException;

    void deleteDelivery(int deliveryId) throws SQLException;

    void updateDeliveryStatus(int deliveryId, String status) throws SQLException;

    void updateDelivery(int deliveryId, DeliveryDto deliveryDto) throws SQLException;

    public int getVehicleIDbyDelID(int delivery_id) throws SQLException;

    ArrayList<DeliveryDto> getAllDelivery() throws SQLException;

    public String setDelivery(DeliveryDto deliveryDto,String orderID) throws SQLException;
}
