package edu.ijse.baketrack.model;

import java.sql.SQLException;

import edu.ijse.baketrack.dto.PayrollDto;

public interface PayrollInterface {
    void addPayroll(PayrollDto payrollDto) throws SQLException;

    void updateBaseSalary(PayrollDto payrollDto, int payrollId, double baseSalary) throws SQLException;

    void updateOverTimeHours(PayrollDto payrollDto, int payrollId, String overTimeHoursString) throws SQLException;

    void deletePayroll(int payrollId) throws SQLException;

    void getPayrollById(int payrollId) throws SQLException;
}
