package edu.ijse.baketrack.model;

import java.sql.SQLException;
import java.util.ArrayList;

import edu.ijse.baketrack.dto.ProductDto;

public interface ProductInterface {
    void addProduct(ProductDto productDto) throws SQLException;

    void deleteProduct(int productID) throws SQLException;

    void updateProduct(ProductDto productDto, int productID) throws SQLException;

    ProductDto getProductDetailsByProductID(int productID) throws SQLException;

    ArrayList<ProductDto> getAllProducts() throws SQLException;

    public double getPriceAtOrder(int product_id) throws SQLException;
}
