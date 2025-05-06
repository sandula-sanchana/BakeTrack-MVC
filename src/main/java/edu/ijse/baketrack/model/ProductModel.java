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

    public String addProduct(ProductDto productDto) throws SQLException {
        String sql = "INSERT INTO product (name, category, price,total_quantity, description) VALUES (?, ?, ?, ?,?)";
        Boolean done=SqlExecute.SqlExecute(sql,productDto.getName(),productDto.getCategory(),productDto.getPrice(),productDto.getQuantity(),productDto.getDescription());

        if (done) {
            return "Product added successfully";
        } else {
            return "Failed to add product";
        }
    }

    public String deleteProduct(int productID) throws SQLException {
        String sql = "DELETE FROM product WHERE product_id=?";
        Boolean done=SqlExecute.SqlExecute(sql,productID);

        if (done) {
            return "Product deleted successfully";
        } else {
            return "Failed to delete product";
        }
    }

    public String updateProduct(ProductDto productDto) throws SQLException {
        String sql = "UPDATE product SET name=?, category = ?, price = ?,total_quantity=?, description = ? WHERE product_id = ?";


        Boolean done=SqlExecute.SqlExecute(sql,productDto.getName(),productDto.getCategory(),productDto.getPrice(),
                productDto.getQuantity(),productDto.getDescription(),productDto.getProduct_id());


        if (done) {
            return "Product updated successfully";
        } else {
            return "Failed to update product";
        }
    }

    public ProductDto getProductDetailsByProductID(int productID) throws SQLException {
        String sql = "SELECT * FROM product WHERE product_id=?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1,productID);
        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            return new ProductDto(resultSet.getInt("product_id"),resultSet.getString("name"), resultSet.getString("category"),resultSet.getDouble("price"),resultSet.getInt("total_quantity"),
                    resultSet.getString("description"));
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
                        resultSet.getInt("total_quantity"),
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

    public String getProductNameById(int productId) throws SQLException {
        String sql = "SELECT name FROM product WHERE product_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, productId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getString("name");
                } else {
                    return null;
                }
            }
        }
    }

}
