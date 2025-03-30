package edu.ijse.baketrack.model;

import java.sql.SQLException;
import java.util.ArrayList;

import edu.ijse.baketrack.dto.EmployeeDto;
import edu.ijse.baketrack.dto.IngredientDto;

public interface IngredientInterface {
 void addIngredient(IngredientDto ingredientDto) throws SQLException;
    void deleteIngredient(int ingredientId) throws SQLException;
    void updateIngredient(int ingredientId, IngredientDto ingredientDto) throws SQLException;
    void getIngredientById(int ingredientId) throws SQLException;
    ArrayList<IngredientDto> getAllIngredients() throws SQLException;
}
