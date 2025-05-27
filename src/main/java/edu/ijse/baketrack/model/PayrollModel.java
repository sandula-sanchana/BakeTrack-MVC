package edu.ijse.baketrack.model;

import edu.ijse.baketrack.db.DBobject;
import edu.ijse.baketrack.dto.PayrollDto;
import edu.ijse.baketrack.util.SqlExecute;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class PayrollModel implements PayrollInterface{
    private Connection connection;

    public PayrollModel() throws ClassNotFoundException, SQLException {
        this.connection= DBobject.getInstance().getConnection();
    }

    public String addPayroll(PayrollDto payrollDto){
        String addSql = "INSERT INTO payroll (employee_id,pay_date,over_time_hours,base_salary,full_salary,status) VALUES (?,?,?,?,?,?)";


        Boolean done= null;
        try {
            done = SqlExecute.SqlExecute(addSql,payrollDto.getEmployee_id(), Date.valueOf(payrollDto.getPay_Date()),
                    payrollDto.getOver_time_hours(),payrollDto.getBase_salary(),payrollDto.getFull_salary(),payrollDto.getStatus());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        if (done) {
            return  "payroll updated successfully";
        } else {
            return "failed";
        }

    }

    @Override
    public void updateBaseSalary(PayrollDto payrollDto, int payrollId, double baseSalary) throws SQLException {

    }

    @Override
    public void updateOverTimeHours(PayrollDto payrollDto, int payrollId, String overTimeHoursString) throws SQLException {

    }

//    public void updateBaseSalary(PayrollDto payrollDto, int payrollId, double baseSalary) throws SQLException {
//        String updateSql = "UPDATE payroll SET base_salary=?, full_salary=? WHERE payroll_id=?";
//        PreparedStatement updatestm = connection.prepareStatement(updateSql);
//
//        payrollDto.setBaseSalary(baseSalary);
//        updatestm.setDouble(1, baseSalary);
//        updatestm.setDouble(2, payrollDto.getFullSalary());
//        updatestm.setInt(3, payrollId);
//
//        int rowsAffected = updatestm.executeUpdate();
//        if (rowsAffected > 0) {
//            System.out.println("Payroll base salary updated successfully.");
//        } else {
//            System.out.println("Failed to update payroll base salary.");
//        }
//    }

//    public void updateOverTimeHours(PayrollDto payrollDto, int id, String overTimeHoursString) throws SQLException {
//        String updateSql = "UPDATE payroll SET over_time_hours=?, full_salary=? WHERE payroll_id=?";
//        PreparedStatement updatestm = connection.prepareStatement(updateSql);
//
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
//        LocalTime overTimeHours = LocalTime.parse(overTimeHoursString, formatter);
//
//        payrollDto.setOverTimeHours(overTimeHoursString);
//
//        updatestm.setTime(1, Time.valueOf(overTimeHours));
//        updatestm.setDouble(2, payrollDto.getFullSalary());
//        updatestm.setInt(3, id);
//
//        int rowsAffected = updatestm.executeUpdate();
//
//        if (rowsAffected > 0) {
//            System.out.println("Overtime hours updated successfully");
//        } else {
//            System.out.println("Failed to update overtime hours");
//        }
//    }

    public String deletePayroll(int payrollId) throws SQLException {
        String deleteSql = "DELETE FROM payroll WHERE payroll_id=?";

        try (PreparedStatement statement = connection.prepareStatement(deleteSql)) {
           Boolean done=SqlExecute.SqlExecute(deleteSql,payrollId);
            return (done? "Payroll deleted successfully" : "Failed to delete payroll");
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
                        resultSet.getDate("pay_date").toLocalDate(),
                        resultSet.getDouble("over_time_hours"),
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

    public String updatePayroll(PayrollDto payrollDto, int payrollId) {
        String updateSql = "UPDATE payroll SET employee_id=?,pay_date=?,  over_time_hours=?,base_salary=?, full_salary=?, status=? WHERE payroll_id=?";

        try  {

            boolean done=SqlExecute.SqlExecute(updateSql,payrollDto.getEmployee_id(),payrollDto.getPay_Date(),payrollDto.getOver_time_hours(),
                    payrollDto.getBase_salary(),payrollDto.getFull_salary(),payrollDto.getStatus(),payrollId);
            if (done) {
                return "Payroll updated successfully";
            } else {
                return "Failed to update payroll";
            }
              } catch (Exception e) {
               System.err.println("Error updating payroll: " + e.getMessage());
                throw e;
        }


    }

    public Map<String,Integer> getPayrollStatus(){
        Map<String,Integer> statusCountMap=new HashMap<>();
        String sql="SELECT status, COUNT(*) as count\n" +
                "FROM payroll\n" +
                "WHERE MONTH(pay_date) = MONTH(CURRENT_DATE())\n" +
                "  AND YEAR(pay_date) = YEAR(CURRENT_DATE())\n" +
                "GROUP BY status;\n";

        try {
            ResultSet rs=SqlExecute.SqlExecute(sql);
            while (rs.next()){
                String status=rs.getString("status");
                Integer count=rs.getInt("count");
                statusCountMap.put(status,count);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return statusCountMap;
    }

}
