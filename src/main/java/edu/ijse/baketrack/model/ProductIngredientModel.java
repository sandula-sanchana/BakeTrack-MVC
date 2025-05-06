package edu.ijse.baketrack.model;

import edu.ijse.baketrack.db.DBobject;
import edu.ijse.baketrack.dto.ProductIngredientDto;
import edu.ijse.baketrack.dto.SupplierDto;
import edu.ijse.baketrack.util.SqlExecute;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProductIngredientModel implements ProductIngredientInterface{

    private Connection connection;

    public ProductIngredientModel() throws ClassNotFoundException, SQLException {
        this.connection= DBobject.getInstance().getConnection();
    }

    public ArrayList<ProductIngredientDto> getProductIngredientByPid(int productID) throws SQLException {
        ArrayList<ProductIngredientDto> productIngredientDtoArrayList=new ArrayList<>();
        String sql="SELECT * FROM product_ingredient WHERE product_id=?";

        ResultSet resultSet= SqlExecute.SqlExecute(sql,productID);

        while (resultSet.next()){
             ProductIngredientDto productIngredientDto=new ProductIngredientDto(resultSet.getInt("id"),resultSet.getInt("product_id"),resultSet.getInt("ingredient_id"),
                     resultSet.getInt("quantity_required"),resultSet.getString("unit"));
             productIngredientDtoArrayList.add(productIngredientDto);
        }


        return productIngredientDtoArrayList;
    }
}
