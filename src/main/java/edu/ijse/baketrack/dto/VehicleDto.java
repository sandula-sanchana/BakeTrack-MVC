package edu.ijse.baketrack.dto;

public class VehicleDto {

    private String type;
    private String license_plate;

    public VehicleDto(String type, String license_plate) {

        this.type = type;
        this.license_plate = license_plate;
    }

    public String getType() {

        return type;
    }

    public void setType(String type) {

        this.type = type;
    }

    public String getLicensePlate() {

        return license_plate;
    }

    public void setLicensePlate(String license_plate) {

        this.license_plate = license_plate;
    }

}