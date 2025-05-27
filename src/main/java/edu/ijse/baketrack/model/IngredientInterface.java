package edu.ijse.baketrack.model;

import java.sql.SQLException;
import java.util.ArrayList;

import edu.ijse.baketrack.dto.EmployeeDto;
import edu.ijse.baketrack.dto.IngredientDto;

public interface IngredientInterface {
    String addIngredient(IngredientDto ingredientDto) throws SQLException;
    String deleteIngredient(int ingredientId) throws SQLException;
    String updateIngredient( IngredientDto ingredientDto) throws SQLException;
    void getIngredientById(int ingredientId) throws SQLException;
    ArrayList<IngredientDto> getAllIngredients() throws SQLException;
    String getIngredientNameById(int ingredientId) throws SQLException;
   int getStockById(int ingredientId) throws SQLException;
    int countIng() throws SQLException;
}
