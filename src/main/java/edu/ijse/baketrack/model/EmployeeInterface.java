package edu.ijse.baketrack.model;

import java.sql.SQLException;
import java.util.ArrayList;


import edu.ijse.baketrack.dto.DeliveryDto;
import edu.ijse.baketrack.dto.EmployeeDto;

public interface EmployeeInterface {
    String addEmployee(EmployeeDto employeeDto) throws SQLException;

    String deleteEmployee(int employee_id)throws SQLException;

    void updateName(int employeeId, String name) throws SQLException;

    void updateContact(int employeeId, String contact) throws SQLException;

    void updateAddress(int employeeId, String address) throws SQLException;

    void updateSalary(int employeeId, Double salary) throws SQLException;

    ArrayList<EmployeeDto> getAllEmployee()  throws SQLException;

    ArrayList<EmployeeDto> getAllAvailableAndNonAssinEmp() throws SQLException;

    String updateEmployee(EmployeeDto employeeDto) throws SQLException;

    ArrayList<EmployeeDto> getAllEmployeeNamesAndIds() throws SQLException;
}
