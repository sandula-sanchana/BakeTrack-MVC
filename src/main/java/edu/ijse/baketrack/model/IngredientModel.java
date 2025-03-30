package edu.ijse.baketrack.model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import edu.ijse.baketrack.db.DBobject;
import edu.ijse.baketrack.dto.EmployeeDto;
import edu.ijse.baketrack.dto.IngredientDto;

public class IngredientModel implements IngredientInterface {
     
    private Connection connection;

    public IngredientModel() throws ClassNotFoundException, SQLException {
        this.connection= DBobject.getInstance().getConnection();
    }

     public void addIngredient(IngredientDto ingredientDto) throws SQLException {
        String sql = "INSERT INTO ingredients (name, stock_amount, unit, buying_price, expire_date) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, ingredientDto.getName());
        statement.setInt(2, ingredientDto.getStockAmount());
        statement.setString(3, ingredientDto.getUnit());
        statement.setDouble(4, ingredientDto.getBuyingPrice());
        statement.setDate(5, Date.valueOf(ingredientDto.getExpireDate()));

        int rowsAffected = statement.executeUpdate();
        if (rowsAffected > 0) {
            System.out.println("Ingredient added successfully");
        } else {
            System.out.println("Failed to add ingredient");
        }
    }

    public void deleteIngredient(int ingredientId) throws SQLException {
        String sql = "DELETE FROM ingredients WHERE ingredient_id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, ingredientId);

        int rowsAffected = statement.executeUpdate();
        if (rowsAffected > 0) {
            System.out.println("Ingredient deleted successfully");
        } else {
            System.out.println("Failed to delete ingredient");
        }
    }

    public void updateIngredient(int ingredientId, IngredientDto ingredientDto) throws SQLException {
        String sql = "UPDATE ingredients SET name = ?, stock_amount = ?, unit = ?, buying_price = ?, expire_date = ? WHERE ingredient_id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, ingredientDto.getName());
        statement.setInt(2, ingredientDto.getStockAmount());
        statement.setString(3, ingredientDto.getUnit());
        statement.setDouble(4, ingredientDto.getBuyingPrice());
        statement.setDate(5, Date.valueOf(ingredientDto.getExpireDate()));
        statement.setInt(6, ingredientId);

        int rowsAffected = statement.executeUpdate();
        if (rowsAffected > 0) {
            System.out.println("Ingredient updated successfully");
        } else {
            System.out.println("Failed to update ingredient");
        }
    }

    public void getIngredientById(int ingredientId) throws SQLException {
        String sql = "SELECT * FROM ingredients WHERE ingredient_id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, ingredientId);
        ResultSet resultSet = statement.executeQuery();

       while ((resultSet.next())) {
            System.out.print(resultSet.getString("name")+"|");
            System.out.println(resultSet.getString("expire_date"));
       }
    }

    public ArrayList<IngredientDto> getAllIngredients()  {
        String allSql="SELECT * FROM ingredient";
        ArrayList<IngredientDto> getall=new ArrayList<>();

        try {
            PreparedStatement statement=connection.prepareStatement(allSql);
            ResultSet resultSet=statement.executeQuery();

            LocalDate localDate_expire =resultSet.getDate("expire_date").toLocalDate();
            while (resultSet.next()){
               IngredientDto ingredientDto= new IngredientDto(  resultSet.getInt("employee_id"),resultSet.getString("emp_name"), resultSet.getInt("employee_id"),resultSet.getString("emp_name"), resultSet.getDouble("salary"),localDate_expire);
                getall.add(ingredientDto);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage() );
            throw new RuntimeException(e);
        }
        return getall;
    }


}
