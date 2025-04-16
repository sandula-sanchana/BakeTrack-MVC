package edu.ijse.baketrack.model;

import java.sql.SQLException;
import java.util.ArrayList;

import edu.ijse.baketrack.dto.VehicleDto;

public interface VehicleInterface {
    void addVehicle(VehicleDto vehicleDto) throws SQLException;

    void deleteVehicle(int vehicleId) throws SQLException;

    void updateVehicle(VehicleDto vehicleDto, int vehicleId) throws SQLException;

    void getVehicleById(int vehicleId) throws SQLException;

    ArrayList<VehicleDto> getAllVehicles() throws SQLException;

    ArrayList<VehicleDto> getAvailableVehicles(String status) throws SQLException;
}
