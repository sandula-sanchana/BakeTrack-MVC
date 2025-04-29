package edu.ijse.baketrack.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import edu.ijse.baketrack.db.DBobject;
import edu.ijse.baketrack.dto.ProductDto;
import edu.ijse.baketrack.util.SqlExecute;

public class ProductModel implements ProductInterface{
    private Connection connection;

    public ProductModel() throws ClassNotFoundException, SQLException {
        this.connection= DBobject.getInstance().getConnection();
    }

    public void addProduct(ProductDto productDto) throws SQLException {
        String sql = "INSERT INTO product (name, category, price, description) VALUES (?, ?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, productDto.getName());
        statement.setString(2, productDto.getCategory());
        statement.setDouble(3, productDto.getPrice());
        statement.setString(4, productDto.getDescription());

        int rowsAffected = statement.executeUpdate();
        if (rowsAffected > 0) {
            System.out.println("Product added successfully");
        } else {
            System.out.println("Failed to add product");
        }
    }

    public void deleteProduct(int productID) throws SQLException {
        String sql = "DELETE FROM product WHERE product_id=?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, productID);

        int rowsAffected = statement.executeUpdate();
        if (rowsAffected > 0) {
            System.out.println("Product deleted successfully");
        } else {
            System.out.println("Failed to delete product");
        }
    }

    public void updateProduct(ProductDto productDto, int product_id) throws SQLException {
        String sql = "UPDATE product SET name=?, category = ?, price = ?, description = ? WHERE product_id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, productDto.getName());
        statement.setString(2, productDto.getCategory());
        statement.setDouble(3, productDto.getPrice());
        statement.setString(4, productDto.getDescription());
        statement.setInt(5, product_id);

        int rowsAffected = statement.executeUpdate();
        if (rowsAffected > 0) {
            System.out.println("Product updated successfully");
        } else {
            System.out.println("Failed to update product");
        }
    }

    public ProductDto getProductDetailsByProductID(int productID) throws SQLException {
        String sql = "SELECT * FROM product WHERE product_id=?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1,productID);
        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            return new ProductDto(resultSet.getInt("product_id"),resultSet.getString("name"), resultSet.getString("category"),resultSet.getDouble("price"),resultSet.getString("description"));
        } else {
           return  null;
        }
    }

    public int getQtyByPid(int ProductID) throws SQLException {
        String sql = "SELECT total_quantity FROM product WHERE product_id=?";
        ResultSet resultSet= null;
        try {
            resultSet = SqlExecute.SqlExecute(sql,ProductID);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return resultSet.getInt("total_quantity");

    }

    public ArrayList<ProductDto> getAllProducts() throws SQLException {
        String sql = "SELECT * FROM product";
        ArrayList<ProductDto> productList = new ArrayList<>();

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                ProductDto product = new ProductDto(
                        resultSet.getInt("product_id"),
                        resultSet.getString("name"),
                        resultSet.getString("category"),
                        resultSet.getDouble("price"),
                        resultSet.getString("description")
                );
                productList.add(product);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException(e);
        }
        return productList;
    }

    public double getPriceAtOrder(int product_id) throws SQLException {
        String sql="SELECT price FROM product WHERE product_id=?";

        try {
            PreparedStatement statement=connection.prepareStatement(sql);

            statement.setInt(1,product_id);

            ResultSet resultSet=statement.executeQuery();

            if (resultSet.next()){
                return resultSet.getDouble("price");
            }

        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException(e);
        }
        return 0.0;
    }
}
