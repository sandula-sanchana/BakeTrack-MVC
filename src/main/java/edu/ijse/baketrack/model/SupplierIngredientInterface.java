package edu.ijse.baketrack.model;

import java.sql.SQLException;
import java.util.ArrayList;

import edu.ijse.baketrack.dto.SupplierIngredientDto;

public interface SupplierIngredientInterface {
    String addSupplierIngredient(SupplierIngredientDto supplierIngredientDto) throws SQLException;

    String updateSupplierIngredient(SupplierIngredientDto supplierIngredientDto) throws SQLException;

    String deleteSupplierIngredient(int ingredientId, int supplierId) throws SQLException;

    void getSupplierIngredientByID(int ingredientId, int supplierId) throws SQLException;

    ArrayList<SupplierIngredientDto> getAllSupplierIngredients() throws SQLException;
}
