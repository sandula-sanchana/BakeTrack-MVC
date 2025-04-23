package edu.ijse.baketrack.dto.tm;

public class OrderDetailTM {
    private int product_id;
    private int order_id;
    private int quantity;
    private double price_at_order;

    public OrderDetailTM(int product_id,int quantity, double price_at_order) {
        this.product_id = product_id;
        this.order_id = order_id;
        this.quantity = quantity;
        this.price_at_order = price_at_order;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice_at_order() {
        return price_at_order;
    }

    public void setPrice_at_order(double price_at_order) {
        this.price_at_order = price_at_order;
    }
}
