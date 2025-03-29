package edu.ijse.baketrack.dto;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class IngredientDto {
    private String name;
    private int stock_amount;
    private String unit;
    private double buying_price;
    private LocalDate expire_date;

    public IngredientDto(String name, int stock_amount, String unit, double buying_price, LocalDate expire_date) {
        this.name = name;
        this.stock_amount = stock_amount;
        this.unit = unit;
        this.buying_price = buying_price;
        this.expire_date = expire_date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStockAmount() {
        return stock_amount;
    }

    public void setStockAmount(int stock_amount) {
        this.stock_amount = stock_amount;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public double getBuyingPrice() {
        return buying_price;
    }

    public void setBuyingPrice(double buying_price) {
        this.buying_price = buying_price;
    }

    public LocalDate getExpireDate() {
        return expire_date;
    }

    public void setExpireDate(String expire_date_String) {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate expire_date=LocalDate.parse(expire_date_String,format);
        this.expire_date = expire_date;

    }
}