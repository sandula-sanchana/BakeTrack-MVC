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

    public String addVehicle(VehicleDto vehicleDto) throws SQLException {
        String sql = "INSERT INTO vehicle (type, license_plate) VALUES (?, ?)";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, vehicleDto.getType());
        statement.setString(2, vehicleDto.getLicensePlate());

        int rowsAffected = statement.executeUpdate();
        if (rowsAffected > 0) {
            return "Vehicle added successfully";
        } else {
            return "Failed to add vehicle";
        }
    }

    public String deleteVehicle(int vehicleId) throws SQLException {
        String sql = "DELETE FROM vehicle WHERE vehicle_id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, vehicleId);

        int rowsAffected = statement.executeUpdate();
        if (rowsAffected > 0) {
            return "Vehicle deleted successfully";
        } else {
            return "Failed to delete vehicle";
        }
    }

    public String updateVehicle(VehicleDto vehicleDto) throws SQLException {
        String sql = "UPDATE vehicle SET type = ?, license_plate = ?,status=? WHERE vehicle_id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, vehicleDto.getType());
        statement.setString(2, vehicleDto.getLicensePlate());
        statement.setString(3, vehicleDto.getStatus());
        statement.setInt(4,vehicleDto.getVehicle_id());

        int rowsAffected = statement.executeUpdate();
        if (rowsAffected > 0) {
            return "Vehicle updated successfully";
        } else {
            return "Failed to update vehicle";
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


                while(resultSet.next()){
                VehicleDto vehicle = new VehicleDto(
                        resultSet.getInt("vehicle_id"),
                        resultSet.getString("type"),
                        resultSet.getString("license_plate"),
                        resultSet.getString("status"));
                        vehicleList.add(vehicle);
                }

                return vehicleList;

        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException(e);
        }

    }

    public String getLicensePlateById(int vehicleId) throws SQLException {
        String sql = "SELECT license_plate FROM vehicle WHERE vehicle_id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, vehicleId);
        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            return resultSet.getString("license_plate");
        } else {
            return null;
        }
    }


}