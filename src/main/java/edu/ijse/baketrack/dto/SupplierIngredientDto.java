package edu.ijse.baketrack.dto;


import java.time.LocalDate;

public class SupplierIngredientDto {
    private int ingredient_id;
    private int supplier_id;
    private double price_per_unit;
    private String unit;
    private LocalDate last_order_date;

    public SupplierIngredientDto(int ingredient_id, int supplier_id, double price_per_unit, String unit, LocalDate last_order_date) {
        this.ingredient_id = ingredient_id;
        this.supplier_id = supplier_id;
        this.price_per_unit = price_per_unit;
        this.unit = unit;
        this.last_order_date = last_order_date;
    }

    public int getIngredientID() {
        return ingredient_id;
    }

    public void setIngredientID(int ingredient_id) {
        this.ingredient_id = ingredient_id;
    }

    public int getSupplierID() {
        return supplier_id;
    }

    public void setSupplierID(int supplier_id) {
        this.supplier_id = supplier_id;
    }

    public double getPricePerUnit() {
        return price_per_unit;
    }

    public void setPricePerUnit(double price_per_unit) {
        this.price_per_unit = price_per_unit;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public LocalDate getLastOrderDate() {
        return last_order_date;
    }

    public void setLastOrderDate(LocalDate last_order_date) {
        this.last_order_date = last_order_date;
    }
}