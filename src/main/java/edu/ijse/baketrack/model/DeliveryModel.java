package edu.ijse.baketrack.model;
import java.sql.*;

import edu.ijse.baketrack.db.DBobject;
import edu.ijse.baketrack.dto.CustomersDto;
import edu.ijse.baketrack.dto.DeliveryDto;

import java.time.LocalDate;
import java.util.ArrayList;

public class DeliveryModel implements DeliveryInterface{
     
      private Connection connection;

    public DeliveryModel() {
        try {
            this.connection= DBobject.getInstance().getConnection();
        } catch (Exception e) {
            System.err.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

     public void addDelivery(DeliveryDto deliveryDto)  {
        String addSql = "INSERT INTO delivery (vehicle_id, delivery_date, area) VALUES (?, ?, ?)";
         int rowsAffected = 0;
         try {
             PreparedStatement statement = connection.prepareStatement(addSql);
             statement.setInt(1, deliveryDto.getVehicleID());
             statement.setDate(2, Date.valueOf(deliveryDto.getDeliveryDate()));
             statement.setString(3, deliveryDto.getDeliveryArea());

             rowsAffected = statement.executeUpdate();
         } catch (SQLException e) {
             System.err.println(e.getMessage());
             throw new RuntimeException(e);
         }
         if (rowsAffected > 0) {
            System.out.println("Delivery added successfully");
        } else {
            System.out.println("Failed to add delivery");
        }
    }

    public void deleteDelivery(int deliveryId)  {
        String deleteSql = "DELETE FROM delivery WHERE delivery_id = ?";
        int rowsAffected = 0;
        try {
            PreparedStatement statement = connection.prepareStatement(deleteSql);
            statement.setInt(1, deliveryId);

            rowsAffected = statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException(e);
        }
        if (rowsAffected > 0) {
            System.out.println("Delivery deleted successfully");
        } else {
            System.out.println("Failed to delete delivery");
        }
    }

    public void updateDeliveryStatus(int deliveryId, String status) {
        String updateSql = "UPDATE delivery SET status = ? WHERE delivery_id = ?";
        int rowsAffected = 0;
        try {
            PreparedStatement statement = connection.prepareStatement(updateSql);
            statement.setString(1, status);
            statement.setInt(2, deliveryId);

            rowsAffected = statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException(e);
        }
        if (rowsAffected > 0) {
            System.out.println("Delivery status updated successfully");
        } else {
            System.out.println("Failed to update delivery status");
        }
    }

    public void updateDelivery(int deliveryId, DeliveryDto deliveryDto){
        String updateSql = "UPDATE delivery SET vehicle_id = ?, delivery_date = ?, area = ? WHERE delivery_id = ?";
        int rowsAffected = 0;
        try {
            PreparedStatement statement = connection.prepareStatement(updateSql);
            statement.setInt(1, deliveryDto.getVehicleID());
            statement.setDate(2, Date.valueOf(deliveryDto.getDeliveryDate()));
            statement.setString(3, deliveryDto.getDeliveryArea());
            statement.setInt(4, deliveryId);

            rowsAffected = statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException(e);
        }
        if (rowsAffected > 0) {
            System.out.println("Delivery updated successfully");
        } else {
            System.out.println("Failed to update delivery");
        }
    }

    public String getDeliveryStatusByDeliveryID(DeliveryDto deliveryDto, int delivery_id){
        String getSql="SELECT status From delivery WHERE delivery_id=?";
        ResultSet rset= null;
        try {
            PreparedStatement statement=connection.prepareStatement(getSql);
            statement.setInt(1, delivery_id);
            rset = statement.executeQuery();


        if(rset.next()){
            System.out.println(rset.getString("status"));
            return rset.getString("status");
        }
        else{
            return"not found";
        }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException(e);
        }
      
    }

    public ArrayList<DeliveryDto> getAllDelivery()  {
        String allSql="SELECT * FROM delivery";
        ArrayList<DeliveryDto> getall=new ArrayList<>();

        try {
            PreparedStatement statement=connection.prepareStatement(allSql);
            ResultSet resultSet=statement.executeQuery();

            LocalDate localDate_delivery =resultSet.getDate("attend_date").toLocalDate();
            while (resultSet.next()){
                DeliveryDto deliveryDto= new DeliveryDto(  resultSet.getInt("delivery_id"),resultSet.getInt("vehicle_id"), localDate_delivery, resultSet.getString("area"));
                getall.add(deliveryDto);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage() );
            throw new RuntimeException(e);
        }
        return getall;
    }

    public String setDelivery(DeliveryDto deliveryDto,String orderID) throws SQLException {
         try {
             connection.setAutoCommit(false);

             String addSql = "INSERT INTO delivery (vehicle_id, delivery_date, area) VALUES (?, ?, ?)";//here i set the delivery to delivery table :)
                                                                                                       //next i will update the delivery id in order table
             try {
                 PreparedStatement statement = connection.prepareStatement(addSql, Statement.RETURN_GENERATED_KEYS);
                 statement.setInt(1, deliveryDto.getVehicleID());
                 statement.setDate(2, Date.valueOf(deliveryDto.getDeliveryDate()));
                 statement.setString(3, deliveryDto.getDeliveryArea());
                 statement.executeUpdate();

                 ResultSet gen_key=statement.getGeneratedKeys();

                 if( gen_key.next()){
                     try {
                         int key=gen_key.getInt(1);
                         String orderUpdateSql="UPDATE orders SET delivery_id=?,status=? WHERE order_id=?";//delivery id and order status updating here
                         PreparedStatement orderStatement=connection.prepareStatement(orderUpdateSql);
                         orderStatement.setInt(1,key);
                         orderStatement.setString(2,"in transit");
                         orderStatement.setInt(3,Integer.parseInt(orderID));

                         if( orderStatement.executeUpdate()>0){
                                String vehicleAvailableSql="UPDATE vehicle SET status=? WHERE vehicle_id=?";
                             try {
                                 PreparedStatement vehicleStatement=connection.prepareStatement(vehicleAvailableSql);
                                 vehicleStatement.setString(1,"not available");
                                 vehicleStatement.setInt(2,deliveryDto.getVehicleID());

                                 if(vehicleStatement.executeUpdate()>0){
                                     connection.commit();
                                     return "setDelivery Successfully done";
                                 }else{
                                      connection.rollback();
                                      return "Vehicle status status Update error";
                                 }
                             } catch (SQLException e) {
                                 throw new RuntimeException(e);
                             }


                         }else{
                             connection.rollback();
                             return "delivery id and status on order Table Update error";
                         }
                     } catch (SQLException e) {
                         throw new RuntimeException(e);
                     } catch (NumberFormatException e) {
                         throw new RuntimeException(e);
                     }
                 }else {
                     connection.rollback();
                     return "set Delivery to table error";
                 }
             } catch (SQLException e) {
                 System.err.println(e.getMessage());
                 throw new RuntimeException(e);
             }


         } catch (Exception e) {
             throw new RuntimeException(e);
         }finally {
              connection.setAutoCommit(true);
         }
    }

}
