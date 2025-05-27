package edu.ijse.baketrack.model;

import java.sql.SQLException;
import java.util.ArrayList;

import edu.ijse.baketrack.dto.VehicleDto;

public interface VehicleInterface {
    String addVehicle(VehicleDto vehicleDto) throws SQLException;

    String deleteVehicle(int vehicleId) throws SQLException;

    String updateVehicle(VehicleDto vehicleDto) throws SQLException;

    void getVehicleById(int vehicleId) throws SQLException;

    ArrayList<VehicleDto> getAllVehicles() throws SQLException;

    ArrayList<VehicleDto> getAvailableVehicles(String status) throws SQLException;

    String getLicensePlateById(int vehicleId) throws SQLException;
}
