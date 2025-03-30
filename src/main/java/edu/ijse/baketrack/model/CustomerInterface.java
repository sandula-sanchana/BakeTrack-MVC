package edu.ijse.baketrack.model;

import java.sql.SQLException;
import java.util.ArrayList;

import edu.ijse.baketrack.dto.CustomersDto;

public interface CustomerInterface {

    void addCustomer(CustomersDto customer) throws SQLException;

    void deleteCustomer(int customerId) throws SQLException;

    void updateName(int customerId, String name) throws SQLException;

    void updateContact(int customerId, String contact) throws SQLException;

    void updateAddress(int customerId, String address) throws SQLException;

    ArrayList<CustomersDto> getAllCustomers() throws SQLException;
}
