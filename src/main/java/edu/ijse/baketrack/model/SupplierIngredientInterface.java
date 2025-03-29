package edu.ijse.baketrack.model;

import java.sql.SQLException;

import edu.ijse.baketrack.dto.SupplierIngredientDto;

public interface SupplierIngredientInterface {
    void addSupplierIngredient(SupplierIngredientDto supplierIngredientDto) throws SQLException;

    void updateSupplierIngredient(SupplierIngredientDto supplierIngredientDto) throws SQLException;

    void deleteSupplierIngredient(int ingredientId, int supplierId) throws SQLException;

    void getSupplierIngredient(int ingredientId, int supplierId) throws SQLException;
}
