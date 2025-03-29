package edu.ijse.baketrack.model;

import edu.ijse.baketrack.dto.PayrollDto;
import edu.ijse.baketrack.db.DBobject;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.sql.ResultSet;


public class PayrollModel implements PayrollInterface{
    private Connection connection;

    public PayrollModel() throws ClassNotFoundException, SQLException {
        this.connection= DBobject.getInstance().getConnection();
    }

    public void addPayroll(PayrollDto payrollDto) throws SQLException {
        String addSql = "INSERT INTO payroll (employee_id,pay_month,over_time_hours,base_salary,full_salary,status) VALUES (?,?,?,?,?,?)";
        PreparedStatement statement = connection.prepareStatement(addSql);
        statement.setInt(1, payrollDto.getEmployeeID());
        statement.setDate(2, Date.valueOf(payrollDto.getPayMonth()));
        statement.setTime(3, Time.valueOf(payrollDto.getOverTimeHours()));
        statement.setDouble(4, payrollDto.getBaseSalary());
        statement.setDouble(5, payrollDto.getFullSalary());
        statement.setString(6, payrollDto.getStatus());

        int rowsAffected = statement.executeUpdate();

        if (rowsAffected > 0) {
            System.out.println("payroll updated successfully");
        } else {
            System.out.println("failed");
        }

    }

    public void updateBaseSalary(PayrollDto payrollDto, int payrollId, double baseSalary) throws SQLException {
        String updateSql = "UPDATE payroll SET base_salary=?, full_salary=? WHERE payroll_id=?";
        PreparedStatement updatestm = connection.prepareStatement(updateSql);
        
        payrollDto.setBaseSalary(baseSalary);
        updatestm.setDouble(1, baseSalary);
        updatestm.setDouble(2, payrollDto.getFullSalary());
        updatestm.setInt(3, payrollId);
    
        int rowsAffected = updatestm.executeUpdate();
        if (rowsAffected > 0) {
            System.out.println("Payroll base salary updated successfully.");
        } else {
            System.out.println("Failed to update payroll base salary.");
        }
    }

    public void updateOverTimeHours(PayrollDto payrollDto, int id, String overTimeHoursString) throws SQLException {
        String updateSql = "UPDATE payroll SET over_time_hours=?, full_salary=? WHERE payroll_id=?";
        PreparedStatement updatestm = connection.prepareStatement(updateSql);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime overTimeHours = LocalTime.parse(overTimeHoursString, formatter);

        payrollDto.setOverTimeHours(overTimeHoursString);

        updatestm.setTime(1, Time.valueOf(overTimeHours));
        updatestm.setDouble(2, payrollDto.getFullSalary());
        updatestm.setInt(3, id);

        int rowsAffected = updatestm.executeUpdate();

        if (rowsAffected > 0) {
            System.out.println("Overtime hours updated successfully");
        } else {
            System.out.println("Failed to update overtime hours");
        }
    }

    public void deletePayroll(int payrollId) throws SQLException {
        String deleteSql = "DELETE FROM payroll WHERE payroll_id=?";

        try (PreparedStatement statement = connection.prepareStatement(deleteSql)) {
            statement.setInt(1, payrollId);
            int rowsAffected = statement.executeUpdate();
            System.out.println(rowsAffected > 0 ? "Payroll deleted successfully" : "Failed to delete payroll");
        }
    }

    public void getPayrollById(int payrollId) throws SQLException {
        String selectSql = "SELECT * FROM payroll WHERE payroll_id=?";

        try (PreparedStatement statement = connection.prepareStatement(selectSql)) {
            statement.setInt(1, payrollId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                System.out.println(resultSet.getString("payroll_id") + "  " + resultSet.getString("full_salary"));
            }
        }
    }
}
