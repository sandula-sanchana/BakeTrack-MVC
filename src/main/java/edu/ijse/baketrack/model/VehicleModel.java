package edu.ijse.baketrack.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import edu.ijse.baketrack.db.DBobject;
import edu.ijse.baketrack.dto.VehicleDto;

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

    public ArrayList<VehicleDto> getAllVehicles() throws SQLException {
        String sql = "SELECT * FROM vehicle";
        ArrayList<VehicleDto> vehicleList = new ArrayList<>();

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                VehicleDto vehicle = new VehicleDto(
                        resultSet.getInt("vehicle_id"),
                        resultSet.getString("type"),
                        resultSet.getString("license_plate"),
                        resultSet.getString("status")
                );
                vehicleList.add(vehicle);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException(e);
        }
        return vehicleList;
    }

    public ArrayList<VehicleDto> getAvailableVehicles(String status) throws SQLException {
        String sql = "SELECT * FROM vehicle WHERE status=?";
        ArrayList<VehicleDto> vehicleList = new ArrayList<>();

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1,status);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                VehicleDto vehicle = new VehicleDto(
                        resultSet.getInt("vehicle_id"),
                        resultSet.getString("type"),
                        resultSet.getString("license_plate"),
                        resultSet.getString("status")
                );
                vehicleList.add(vehicle);
                return vehicleList;
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException(e);
        }
        return null;
    }

}