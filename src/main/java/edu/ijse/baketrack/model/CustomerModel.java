package edu.ijse.baketrack.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import edu.ijse.baketrack.dto.CustomersDto;
import edu.ijse.baketrack.db.DBobject;

import java.sql.Connection;

public class CustomerModel implements CustomerInterface {
    private Connection connection;

    public CustomerModel() throws ClassNotFoundException, SQLException {
        this.connection= DBobject.getInstance().getConnection();
    }

    public void addCustomer(CustomersDto customersDto) throws SQLException {
        String addSql = "INSERT INTO customer (name,contact_no,address) VALUES (?,?,?)";
        PreparedStatement statement = connection.prepareStatement(addSql);
        statement.setString(1, customersDto.getName());
        statement.setString(2, customersDto.getContact());
        statement.setString(3, customersDto.getAddress());

        int rowsAffected = statement.executeUpdate();

        if (rowsAffected > 0) {
            System.out.println("updated successfully");
        } else {
            System.out.println("failed");
        }

    }

    public void deleteCustomer(int customer_id) throws SQLException {
        String deleteSql = "DELETE FROM customer WHERE customer_id=?";
        PreparedStatement statement = connection.prepareStatement(deleteSql);
        statement.setInt(1, customer_id);
        int rowsAffected = statement.executeUpdate();

        if (rowsAffected > 0) {
            System.out.println("deleted successfully");
        } else {
            System.out.println("failed to delete");
        }

    }

    public void updateName(int id, String name) throws SQLException {
        String updateSql = "UPDATE customer SET name=? WHERE Customer_id=?";
        PreparedStatement updatestm = connection.prepareStatement(updateSql);
        updatestm.setString(1, name);
        updatestm.setInt(2, id);

        int rowsAffected = updatestm.executeUpdate();
        if (rowsAffected > 0) {
            System.out.println("done");
        } else {
            System.out.println("failed");
        }

    }

    public void updateContact(int id, String contact) throws SQLException {
        String updateSql = "UPDATE customer SET contact_no=? WHERE Customer_id=?";
        PreparedStatement updatestm = connection.prepareStatement(updateSql);
        updatestm.setString(1, contact);
        updatestm.setInt(2, id);

        int rowsAffected = updatestm.executeUpdate();
        if (rowsAffected > 0) {
            System.out.println("done");
        } else {
            System.out.println("failed");
        }

    }

    public void updateAddress(int id, String address) throws SQLException {
        String updateSql = "UPDATE customer SET address=? WHERE Customer_id=?";
        PreparedStatement updatestm = connection.prepareStatement(updateSql);
        updatestm.setString(1, address);
        updatestm.setInt(2, id);

        int rowsAffected = updatestm.executeUpdate();
        if (rowsAffected > 0) {
            System.out.println("address done");
        } else {
            System.out.println("failed");
        }

    }

    public void viewAllCustomers() throws SQLException {
        String updateSql = "SELECT * FROM customer";
        PreparedStatement viewstm = connection.prepareStatement(updateSql);

        ResultSet rowsAffected = viewstm.executeQuery();
        while (rowsAffected.next()) {
            System.out.println(rowsAffected.getString("name") + "  " + rowsAffected.getString("address"));

        }
    }
}
