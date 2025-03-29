package edu.ijse.baketrack.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import edu.ijse.baketrack.dto.VehicleDto;
import edu.ijse.baketrack.db.DBobject;

public class VehicleModel implements VehicleInterface {
    private Connection connection;

    public VehicleModel() throws ClassNotFoundException, SQLException {
        this.connection= DBobject.getInstance().getConnection();
    }

    public void addVehicle(VehicleDto vehicleDto) throws SQLException {
        String sql = "INSERT INTO vehicle (type, license_plate) VALUES (?, ?)";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, vehicleDto.getType());
        statement.setString(2, vehicleDto.getLicensePlate());

        int rowsAffected = statement.executeUpdate();
        if (rowsAffected > 0) {
            System.out.println("Vehicle added successfully");
        } else {
            System.out.println("Failed to add vehicle");
        }
    }

    public void deleteVehicle(int vehicleId) throws SQLException {
        String sql = "DELETE FROM vehicle WHERE vehicle_id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, vehicleId);

        int rowsAffected = statement.executeUpdate();
        if (rowsAffected > 0) {
            System.out.println("Vehicle deleted successfully");
        } else {
            System.out.println("Failed to delete vehicle");
        }
    }

    public void updateVehicle(VehicleDto vehicleDto, int vehicle_id) throws SQLException {
        String sql = "UPDATE vehicle SET type = ?, license_plate = ? WHERE vehicle_id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, vehicleDto.getType());
        statement.setString(2, vehicleDto.getLicensePlate());
        statement.setInt(3, vehicle_id);

        int rowsAffected = statement.executeUpdate();
        if (rowsAffected > 0) {
            System.out.println("Vehicle updated successfully");
        } else {
            System.out.println("Failed to update vehicle");
        }
    }

     public void getVehicleById(int vehicleId) throws SQLException {
        String sql = "SELECT * FROM vehicle WHERE vehicle_id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, vehicleId);
        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
           System.out.println(resultSet.getString("type"));
        }
    }

}