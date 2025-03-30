package edu.ijse.baketrack.model;

import edu.ijse.baketrack.db.DBobject;
import edu.ijse.baketrack.dto.PayrollDto;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.sql.ResultSet;
import java.util.ArrayList;


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

    public ArrayList<PayrollDto> getAllPayrolls() {
        String sql = "SELECT * FROM payroll";
        ArrayList<PayrollDto> payrollList = new ArrayList<>();

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                PayrollDto payroll = new PayrollDto(
                        resultSet.getInt("payroll_id"),
                        resultSet.getInt("employee_id"),
                        resultSet.getDate("pay_month").toLocalDate(),
                        resultSet.getTime("over_time_hours").toLocalTime(),
                        resultSet.getDouble("base_salary"),
                        resultSet.getDouble("full_salary"),
                        resultSet.getString("status")
                );
                payrollList.add(payroll);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException(e);
        }
        return payrollList;
    }
}
