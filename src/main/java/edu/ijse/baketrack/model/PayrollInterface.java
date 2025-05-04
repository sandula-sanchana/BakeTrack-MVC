package edu.ijse.baketrack.model;

import java.sql.SQLException;
import java.util.ArrayList;

import edu.ijse.baketrack.dto.PayrollDto;

public interface PayrollInterface {
    String addPayroll(PayrollDto payrollDto) throws SQLException;

    void updateBaseSalary(PayrollDto payrollDto, int payrollId, double baseSalary) throws SQLException;

    void updateOverTimeHours(PayrollDto payrollDto, int payrollId, String overTimeHoursString) throws SQLException;

    String deletePayroll(int payrollId) throws SQLException;

    void getPayrollById(int payrollId) throws SQLException;

    ArrayList<PayrollDto> getAllPayrolls() throws  SQLException;

    String updatePayroll(PayrollDto payrollDto, int payrollId) throws SQLException;
}
