package edu.ijse.baketrack.model;

import java.sql.SQLException;

import edu.ijse.baketrack.dto.VehicleDto;

public interface VehicleInterface {
    void addVehicle(VehicleDto vehicleDto) throws SQLException;

    void deleteVehicle(int vehicleId) throws SQLException;

    void updateVehicle(VehicleDto vehicleDto, int vehicleId) throws SQLException;

    void getVehicleById(int vehicleId) throws SQLException;
}
