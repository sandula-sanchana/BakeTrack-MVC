package edu.ijse.baketrack.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;

import edu.ijse.baketrack.dto.SupplierIngredientDto;
import edu.ijse.baketrack.db.DBobject;

public class SupplierIngredientsModel implements SupplierIngredientInterface {
    private Connection connection;

    public SupplierIngredientsModel() throws ClassNotFoundException, SQLException {
        this.connection= DBobject.getInstance().getConnection();
    }

    public void addSupplierIngredient(SupplierIngredientDto supplierIngredientDto) throws SQLException {
        String sql = "INSERT INTO supplier_ingredient (ingredient_id, supplier_id, price_per_unit, unit, last_order_date) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, supplierIngredientDto.getIngredientID());
        statement.setInt(2, supplierIngredientDto.getSupplierID());
        statement.setDouble(3, supplierIngredientDto.getPricePerUnit());
        statement.setString(4, supplierIngredientDto.getUnit());
        statement.setDate(5, Date.valueOf(supplierIngredientDto.getLastOrderDate()));

        int rowsAffected = statement.executeUpdate();
        System.out.println(
                rowsAffected > 0 ? "Supplier-Ingredient added successfully" : "Failed to add Supplier-Ingredient");
    }

    public void updateSupplierIngredient(SupplierIngredientDto supplierIngredientDto) throws SQLException {
        String sql = "UPDATE supplier_ingredient SET price_per_unit = ?, unit = ?, last_order_date = ? WHERE ingredient_id = ? AND supplier_id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setDouble(1, supplierIngredientDto.getPricePerUnit());
        statement.setString(2, supplierIngredientDto.getUnit());
        statement.setDate(3, Date.valueOf(supplierIngredientDto.getLastOrderDate()));
        statement.setInt(4, supplierIngredientDto.getIngredientID());
        statement.setInt(5, supplierIngredientDto.getSupplierID());

        int rowsAffected = statement.executeUpdate();
        System.out.println(
                rowsAffected > 0 ? "Supplier-Ingredient updated successfully" : "Failed to update Supplier-Ingredient");
    }

    public void deleteSupplierIngredient(int ingredientId, int supplierId) throws SQLException {
        String sql = "DELETE FROM supplier_ingredient WHERE ingredient_id = ? AND supplier_id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, ingredientId);
        statement.setInt(2, supplierId);

        int rowsAffected = statement.executeUpdate();
        System.out.println(rowsAffected > 0 ? "Supplier-Ingredient deleted successfully" : "Failed to delete Supplier-Ingredient");
    }

      public void getSupplierIngredient(int ingredientId, int supplierId) throws SQLException {
        String sql = "SELECT * FROM supplier_ingredient WHERE ingredient_id = ? AND supplier_id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, ingredientId);
        statement.setInt(2, supplierId);
        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            System.out.println("Ingredient ID: " + resultSet.getInt("ingredient_id"));
            System.out.println("Supplier ID: " + resultSet.getInt("supplier_id"));
            System.out.println("Price per Unit: " + resultSet.getDouble("price_per_unit"));
            System.out.println("Unit: " + resultSet.getString("unit"));
            System.out.println("Last Order Date: " + resultSet.getDate("last_order_date"));
        } else {
            System.out.println("No record found for ingredient_id: " + ingredientId + " and supplier_id: " + supplierId);
        }
    }


}
