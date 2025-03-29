package edu.ijse.baketrack.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import edu.ijse.baketrack.dto.SupplierDto;
import edu.ijse.baketrack.db.DBobject;

public class SupplierModel implements SupplierInterface{

     private Connection connection;

    public SupplierModel() throws ClassNotFoundException, SQLException {
        this.connection= DBobject.getInstance().getConnection();
    }

     public void addSupplier(SupplierDto supplierDto) throws SQLException {
        String sql = "INSERT INTO supplier (name, contact, address) VALUES (?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, supplierDto.getName());
        statement.setString(2, supplierDto.getContact());
        statement.setString(3, supplierDto.getAddress());
        
        int rowsAffected = statement.executeUpdate();
        if (rowsAffected > 0) {
            System.out.println("Supplier added successfully");
        } else {
            System.out.println("Failed to add supplier");
        }
    }

    public void deleteSupplier(int supplierId) throws SQLException {
        String sql = "DELETE FROM supplier WHERE supplier_id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, supplierId);
        
        int rowsAffected = statement.executeUpdate();
        if (rowsAffected > 0) {
            System.out.println("Supplier deleted successfully");
        } else {
            System.out.println("Failed to delete supplier");
        }
    }

    public void updateSupplier(int supplierId, SupplierDto supplierDto) throws SQLException {
        String sql = "UPDATE supplier SET name = ?, contact = ?, address = ? WHERE supplier_id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, supplierDto.getName());
        statement.setString(2, supplierDto.getContact());
        statement.setString(3, supplierDto.getAddress());
        statement.setInt(4, supplierId);
        
        int rowsAffected = statement.executeUpdate();
        if (rowsAffected > 0) {
            System.out.println("Supplier updated successfully");
        } else {
            System.out.println("Failed to update supplier");
        }
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
}
