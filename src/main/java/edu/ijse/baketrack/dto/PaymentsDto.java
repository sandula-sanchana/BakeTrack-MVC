package edu.ijse.baketrack.dto;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class PaymentsDto {
    private int order_id;
    private double price;
    private String payment_method;
    private LocalDate payment_date;

    public PaymentsDto(int order_id, double price, String payment_method, LocalDate payment_date) {
        this.order_id = order_id;
        this.price = price;
        this.payment_method = payment_method;
        this.payment_date = payment_date;
    }


    public int getOrderID() {
        return order_id;
    }

    public void setOrderID(int order_id) {
        this.order_id = order_id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getPaymentMethod() {
        return payment_method;
    }

    public void setPaymentMethod(String payment_method) {
        this.payment_method = payment_method;
    }

    public LocalDate getPaymentDate() {
        return payment_date;
    }

    public void setPaymentDate(String payment_date_String) {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        this.payment_date = LocalDate.parse(payment_date_String, format);
    }
}

