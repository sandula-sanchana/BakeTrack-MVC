package edu.ijse.baketrack.model;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import edu.ijse.baketrack.dto.DeliveryDto;
import edu.ijse.baketrack.db.DBobject;

import java.sql.Date;

public class DeliveryModel implements DeliveryInterface{
     
      private Connection connection;

    public DeliveryModel() throws ClassNotFoundException, SQLException {
        this.connection= DBobject.getInstance().getConnection();
    }

     public void addDelivery(DeliveryDto deliveryDto) throws SQLException {
        String addSql = "INSERT INTO deliverie (vehicle_id, delivery_date, area) VALUES (?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(addSql);
        statement.setInt(1, deliveryDto.getVehicleID());
        statement.setDate(2, Date.valueOf(deliveryDto.getDeliveryDate()));
        statement.setString(3, deliveryDto.getDeliveryArea());

        int rowsAffected = statement.executeUpdate();
        if (rowsAffected > 0) {
            System.out.println("Delivery added successfully");
        } else {
            System.out.println("Failed to add delivery");
        }
    }

    public void deleteDelivery(int deliveryId) throws SQLException {
        String deleteSql = "DELETE FROM delivery WHERE delivery_id = ?";
        PreparedStatement statement = connection.prepareStatement(deleteSql);
        statement.setInt(1, deliveryId);

        int rowsAffected = statement.executeUpdate();
        if (rowsAffected > 0) {
            System.out.println("Delivery deleted successfully");
        } else {
            System.out.println("Failed to delete delivery");
        }
    }

    public void updateDeliveryStatus(int deliveryId, String status) throws SQLException {
        String updateSql = "UPDATE delivery SET status = ? WHERE delivery_id = ?";
        PreparedStatement statement = connection.prepareStatement(updateSql);
        statement.setString(1, status);
        statement.setInt(2, deliveryId);
    
        int rowsAffected = statement.executeUpdate();
        if (rowsAffected > 0) {
            System.out.println("Delivery status updated successfully");
        } else {
            System.out.println("Failed to update delivery status");
        }
    }

    public void updateDelivery(int deliveryId, DeliveryDto deliveryDto) throws SQLException {
        String updateSql = "UPDATE delivery SET vehicle_id = ?, delivery_date = ?, area = ? WHERE delivery_id = ?";
        PreparedStatement statement = connection.prepareStatement(updateSql);
        statement.setInt(1, deliveryDto.getVehicleID());
        statement.setDate(2, Date.valueOf(deliveryDto.getDeliveryDate()));
        statement.setString(3, deliveryDto.getDeliveryArea());
        statement.setInt(4, deliveryId);

        int rowsAffected = statement.executeUpdate();
        if (rowsAffected > 0) {
            System.out.println("Delivery updated successfully");
        } else {
            System.out.println("Failed to update delivery");
        }
    }

    public String getDeliveryStatusByDeliveryID(DeliveryDto deliveryDto, int delivery_id)throws SQLException{
        String getSql="SELECT status From delivery WHERE delivery_id=?";
        PreparedStatement statement=connection.prepareStatement(getSql);
        statement.setInt(1, delivery_id);
        ResultSet rset=statement.executeQuery();

        if(rset.next()){
            System.out.println(rset.getString("status"));
            return rset.getString("status");
        }
        else{
            return"not found";
        }
      
    }
    

}
