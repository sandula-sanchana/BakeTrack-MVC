package edu.ijse.baketrack.model;

import java.sql.SQLException;


import edu.ijse.baketrack.dto.EmployeeDto;

public interface EmployeeInterface {
    void addEmployee(EmployeeDto employeeDto) throws SQLException;

    void deleteEmployee(int employeeId) throws SQLException;

    void updateName(int employeeId, String name) throws SQLException;

    void updateContact(int employeeId, String contact) throws SQLException;

    void updateAddress(int employeeId, String address) throws SQLException;

    void updateSalary(int employeeId, Double salary) throws SQLException;

    void viewAllEmployee() throws SQLException;
}
