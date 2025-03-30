package edu.ijse.baketrack.dto;

public class SupplierDto {
    private int supplier_id;
    private String name;
    private String contact;
    private String address;

    public SupplierDto(int supplier_id, String name, String contact, String address) {
        this.supplier_id = supplier_id;
        this.name = name;
        this.contact = contact;
        this.address = address;
    }

    public int getSupplier_id() {
        return supplier_id;
    }

    public void setSupplier_id(int supplier_id) {
        this.supplier_id = supplier_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
