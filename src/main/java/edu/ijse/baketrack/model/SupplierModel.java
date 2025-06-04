package edu.ijse.baketrack.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import edu.ijse.baketrack.db.DBobject;
import edu.ijse.baketrack.dto.SupplierDto;

public class SupplierModel implements SupplierInterface{

     private Connection connection;

    public SupplierModel() throws ClassNotFoundException, SQLException {
        this.connection= DBobject.getInstance().getConnection();
    }

    public String addSupplier(SupplierDto supplierDto) throws SQLException {
        String sql = "INSERT INTO supplier (name, contact, address, email) VALUES (?, ?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, supplierDto.getName());
        statement.setString(2, supplierDto.getContact());
        statement.setString(3, supplierDto.getAddress());
        statement.setString(4, supplierDto.getEmail());

        int rowsAffected = statement.executeUpdate();
        return (rowsAffected > 0) ? "Supplier added successfully" : "Failed to add supplier";
    }

    public String deleteSupplier(int supplierId) throws SQLException {
        String sql = "DELETE FROM supplier WHERE supplier_id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, supplierId);
        
        int rowsAffected = statement.executeUpdate();
        if (rowsAffected > 0) {
            return "Supplier deleted successfully";
        } else {
            return "Failed to delete supplier";
        }
    }

    public String updateSupplier(SupplierDto supplierDto) throws SQLException {
        String sql = "UPDATE supplier SET name = ?, contact = ?, address = ?, email = ? WHERE supplier_id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, supplierDto.getName());
        statement.setString(2, supplierDto.getContact());
        statement.setString(3, supplierDto.getAddress());
        statement.setString(4, supplierDto.getEmail());
        statement.setInt(5, supplierDto.getSupplier_id());

        int rowsAffected = statement.executeUpdate();
        return (rowsAffected > 0) ? "Supplier updated successfully" : "Failed to update supplier";
    }


    public void getSupplierById(int supplierId) throws SQLException {
        String sql = "SELECT * FROM supplier WHERE supplier_id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, supplierId);
        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
           System.out.println(resultSet.getString("name"));
        }
    }

    public ArrayList<SupplierDto> getAllSuppliers() throws SQLException {
        String sql = "SELECT * FROM supplier";
        ArrayList<SupplierDto> supplierList = new ArrayList<>();

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                SupplierDto supplier = new SupplierDto(
                        resultSet.getInt("supplier_id"),
                        resultSet.getString("name"),
                        resultSet.getString("contact"),
                        resultSet.getString("address"),
                        resultSet.getString("email")
                );
                supplierList.add(supplier);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException(e);
        }
        return supplierList;
    }

    public ArrayList<SupplierDto> getAllSuppliersWIthEmail() throws SQLException {
        String sql = "SELECT * FROM supplier";
        ArrayList<SupplierDto> supplierList = new ArrayList<>();

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                SupplierDto supplier = new SupplierDto(
                        resultSet.getInt("supplier_id"),
                        resultSet.getString("name"),
                        resultSet.getString("contact"),
                        resultSet.getString("address"),
                        resultSet.getString("email")
                );
                supplierList.add(supplier);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException(e);
        }
        return supplierList;
    }
}
