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
import edu.ijse.baketrack.util.SqlExecute;

public class IngredientModel implements IngredientInterface {
     
    private Connection connection;

    public IngredientModel() throws ClassNotFoundException, SQLException {
        this.connection= DBobject.getInstance().getConnection();
    }

     public String addIngredient(IngredientDto ingredientDto) throws SQLException {
        String sql = "INSERT INTO ingredient (name, stock_amount, unit, buying_price, expire_date) VALUES (?, ?, ?, ?, ?)";

        Boolean done= SqlExecute.SqlExecute(sql,ingredientDto.getName(),ingredientDto.getStockAmount(),ingredientDto.getUnit(),
                ingredientDto.getBuyingPrice(),Date.valueOf(ingredientDto.getExpireDate()));

        if (done) {
            return "Ingredient added successfully";
        } else {
            return "Failed to add ingredient";
        }
    }

    public String deleteIngredient(int ingredientId) throws SQLException {
        String sql = "DELETE FROM ingredient WHERE ingredient_id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, ingredientId);

        int rowsAffected = statement.executeUpdate();
        if (rowsAffected > 0) {
            return "Ingredient deleted successfully";
        } else {
            return "Failed to delete ingredient";
        }
    }

    public String updateIngredient( IngredientDto ingredientDto) throws SQLException {
        String sql = "UPDATE ingredient SET name = ?, stock_amount = ?, unit = ?, buying_price = ?, expire_date = ? WHERE ingredient_id = ?";

        Boolean done= SqlExecute.SqlExecute(sql,ingredientDto.getName(),ingredientDto.getStockAmount(),ingredientDto.getUnit(),
                ingredientDto.getBuyingPrice(),Date.valueOf(ingredientDto.getExpireDate()),ingredientDto.getIngredient_id());
        if (done) {
            return "Ingredient updated successfully";
        } else {
            return "Failed to update ingredient";
        }
    }

    public void getIngredientById(int ingredientId) throws SQLException {
        String sql = "SELECT * FROM ingredient WHERE ingredient_id = ?";
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


            while (resultSet.next()){
               IngredientDto ingredientDto= new IngredientDto(  resultSet.getInt("ingredient_id"),resultSet.getString("name"), resultSet.getInt("stock_amount"),resultSet.getString("unit"), resultSet.getDouble("buying_price"),resultSet.getDate("expire_date").toLocalDate());
                getall.add(ingredientDto);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage() );
            throw new RuntimeException(e);
        }
        return getall;
    }

    public String getIngredientNameById(int ingredientId) throws SQLException {
        String sql = "SELECT name FROM ingredient WHERE ingredient_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, ingredientId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getString("name");
                } else {
                    return null;
                }
            }
        }
    }

    public int getStockById(int ingredientId) throws SQLException {
        String sql = "SELECT stock_amount FROM ingredient WHERE ingredient_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, ingredientId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("stock_amount");
                } else {
                    System.out.println("Ingredient not found");
                    return -1;
                }
            }
        }
    }




}
