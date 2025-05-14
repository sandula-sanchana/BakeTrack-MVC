package edu.ijse.baketrack.model;

import edu.ijse.baketrack.dto.ProductIngredientDto;
import edu.ijse.baketrack.dto.SupplierDto;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ProductIngredientInterface {

    ArrayList<ProductIngredientDto> getProductIngredientByPid(int productID) throws SQLException;

    ArrayList<ProductIngredientDto> getAll() throws SQLException;

    String addProductIngredient(ProductIngredientDto dto) throws SQLException;

    String updateProductIngredient(ProductIngredientDto productIngredientDto);

    String deleteProductIngredient(int id) throws SQLException;
}
