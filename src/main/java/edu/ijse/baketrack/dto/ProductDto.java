package edu.ijse.baketrack.dto;

public class ProductDto {
    private int product_id;
    private String name;
    private String category;
    private double price;
    private String description;

    public ProductDto(int product_id, String name, String category, double price, String description) {
        this.product_id = product_id;
        this.name = name;
        this.category = category;
        this.price = price;
        this.description = description;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
