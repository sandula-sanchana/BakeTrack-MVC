package edu.ijse.baketrack.model;

import java.sql.SQLException;
import java.util.ArrayList;

import edu.ijse.baketrack.dto.ProductDto;

public interface ProductInterface {
    String addProduct(ProductDto productDto) throws SQLException;

    String deleteProduct(int productID) throws SQLException;

    String updateProduct(ProductDto productDto) throws SQLException;

    ProductDto getProductDetailsByProductID(int productID) throws SQLException;

    ArrayList<ProductDto> getAllProducts() throws SQLException;

    public double getPriceAtOrder(int product_id) throws SQLException;

    public int getQtyByPid(int ProductID) throws SQLException;

    String getProductNameById(int productId) throws SQLException;

    int countProducts() throws SQLException;
}
