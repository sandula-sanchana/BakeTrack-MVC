package edu.ijse.baketrack.model;

import edu.ijse.baketrack.db.DBobject;
import edu.ijse.baketrack.dto.DeliveryDto;
import edu.ijse.baketrack.util.SqlExecute;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.sql.ResultSet;
import java.sql.Statement;

public class DeliveryModel implements DeliveryInterface {

    private Connection connection;

    public DeliveryModel() {
        try {
            this.connection = DBobject.getInstance().getConnection();
        } catch (Exception e) {
            System.err.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public void addDelivery(DeliveryDto deliveryDto) {
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

    public String deleteDelivery(int deliveryId) {
        String deleteSql = "DELETE FROM delivery WHERE delivery_id = ?";

        Boolean delete = SqlExecute.SqlExecute(deleteSql, deliveryId);
        if (delete) {
            return "Delivery deleted successfully";
        } else {
            return "Failed to delete delivery";
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
    public ArrayList<DeliveryDto> getUnassignedDeliveries() {
        String query = "SELECT * FROM delivery WHERE employee_id IS NULL";
        ArrayList<DeliveryDto> unassignedDeliveries = new ArrayList<>();

        try {
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                DeliveryDto deliveryDto = new DeliveryDto(
                        resultSet.getInt("delivery_id"),
                        resultSet.getInt("vehicle_id"),
                        0,
                        resultSet.getDate("delivery_date").toLocalDate(),
                        resultSet.getString("area")
                );
                unassignedDeliveries.add(deliveryDto);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException(e);
        }

        return unassignedDeliveries;
    }


    public String updateDelivery( DeliveryDto deliveryDto) {
        String updateSql = "UPDATE delivery SET vehicle_id = ?,employee_id=?, delivery_date = ?, area = ? WHERE delivery_id = ?";
        Boolean updateDone = SqlExecute.SqlExecute(updateSql, deliveryDto.getVehicleID(),
                deliveryDto.getEmployee_id(), Date.valueOf(deliveryDto.getDeliveryDate()), deliveryDto.getDeliveryArea(), deliveryDto.getDelivery_id());

        if (updateDone) {
            return "Delivery updated successfully";
        }
        return "Failed to update delivery";
    }

    @Override
    public int getVehicleIDbyDelID(int delivery_id) throws SQLException {
        return 0;
    }

    public ArrayList<DeliveryDto> getAllDelivery()  {
        String allSql="SELECT * FROM delivery";
        ArrayList<DeliveryDto> getall=new ArrayList<>();

        try {
            PreparedStatement statement=connection.prepareStatement(allSql);
            ResultSet resultSet=statement.executeQuery();

            while (resultSet.next()){
                DeliveryDto deliveryDto= new DeliveryDto( resultSet.getInt("delivery_id"),resultSet.getInt("vehicle_id"),resultSet.getInt("employee_id"),resultSet.getDate("delivery_date").toLocalDate(), resultSet.getString("area"));
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

            String addSql = "INSERT INTO delivery (vehicle_id,employee_id, delivery_date, area) VALUES (?, ?, ?,?)";//here i set the delivery to delivery table :)
            //next i will update the delivery id in order table
            try {
                PreparedStatement statement = connection.prepareStatement(addSql, Statement.RETURN_GENERATED_KEYS);
                statement.setInt(1, deliveryDto.getVehicleID());
                statement.setNull(2,-1);
                statement.setDate(3, Date.valueOf(deliveryDto.getDeliveryDate()));
                statement.setString(4, deliveryDto.getDeliveryArea());
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
    public String setEmployeeForDelivery(int del_id,int emp_id){
        Boolean done= null;
        try {
            String empSql="UPDATE delivery SET employee_id=? WHERE delivery_id=?";
            done = SqlExecute.SqlExecute(empSql,emp_id,del_id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        if(done){
            return "successfully set the employee";
        }
        return "error while set Employee";
    }

}
