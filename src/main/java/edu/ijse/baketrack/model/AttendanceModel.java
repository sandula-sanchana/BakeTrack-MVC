package edu.ijse.baketrack.model;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;

import edu.ijse.baketrack.dto.AttendanceDto;
import edu.ijse.baketrack.db.DBobject;
import java.sql.Connection;
import java.sql.Date;

public class AttendanceModel implements AttendanceInterface {
    private Connection connection;

    public AttendanceModel() {
        try {
            this.connection=DBobject.getInstance().getConnection();
        } catch (Exception e) {
            throw new RuntimeException(e);
            //e.printStackTrace();
        }
    }

    public void addAttendance(AttendanceDto attendance) {
        String addSql="INSERT INTO attendance (employee_id,attend_date,check_in_time,check_out_time,status) VALUES (?,?,?,?,?)";
        int rowsAffected= 0;
        try {
            PreparedStatement statement=connection.prepareStatement(addSql);
            statement.setInt(1,attendance.getEmpID());
            statement.setDate(2,Date.valueOf(attendance.getAttendDate()));
            statement.setTime(3,Time.valueOf(attendance.getCheckInTime()));
            statement.setTime(4,Time.valueOf(attendance.getCheckOutTime()));
            statement.setString(5, attendance.getStatus());

            rowsAffected = statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
            //e.printStackTrace();
        }

        if(rowsAffected>0){
            System.out.println("added atten successfully");
        }else{
            System.out.println("failed");
        }

    }

    public void updateCheckInTime(int employee_id, LocalDate attend_date,LocalTime newCheckIn) {
        String sql = "UPDATE attendance SET check_in_time = ? WHERE employee_id = ? AND attend_date=?";
        int updateCheck= 0;
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setTime(1, Time.valueOf(newCheckIn));
            statement.setInt(2, employee_id);
            statement.setDate(3,Date.valueOf(attend_date));

            updateCheck = statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
            //e.printStackTrace();
        }
        if(updateCheck>0){
            System.out.println("Check-in time updated successfully");
        }
    }


    public void updateCheckOutTime(int employee_id, LocalDate attend_date, LocalTime newCheckOut) throws SQLException {
        String sql = "UPDATE attendance SET check_out_time = ? WHERE employee_id = ? AND attend_date=?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setTime(1, Time.valueOf(newCheckOut));
        statement.setInt(2, employee_id);
        statement.setDate(3,Date.valueOf(attend_date));

        int updateCheck=statement.executeUpdate();
        if(updateCheck>0){
            System.out.println("Check-out time updated successfully");
        }
    }

    public void updateStatus(int employee_id, LocalDate attend_date,String status) throws SQLException {
        String sql = "UPDATE attendance SET status = ? WHERE employee_id = ? AND attend_date=?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, status);;
        statement.setInt(2, employee_id);
        statement.setDate(3,Date.valueOf(attend_date));


        int updateCheck=statement.executeUpdate();
        if(updateCheck>0){
            System.out.println("status updated successfully");
        }
    }

    public void getAttendanceByEmployee(int employee_id,String status) throws SQLException {
        String sql = "SELECT * FROM attendance WHERE employee_id = ? AND status=?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, employee_id);
        statement.setString(2, status);

        ResultSet rs = statement.executeQuery();
        while (rs.next()) {
            System.out.println("Date: " + rs.getDate("attend_date") +
                    ", Check-in: " + rs.getTime("check_in_time") +
                    ", Check-out: " + rs.getTime("check_out_time") +
                    ", Status: " + rs.getString(("status")));
        }
    }

    public void getAttendanceOnDate(LocalDate date,String status) throws SQLException {
        String sql = "SELECT a.employee_id, e.emp_name, a.attend_date, a.check_in_time, a.check_out_time, a.status FROM attendance a INNER JOIN employee e ON a.employee_id = e.employee_id WHERE a.attend_date = ? AND a.status=?";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setDate(1, Date.valueOf(date));
        statement.setString(2, status);

        ResultSet rs = statement.executeQuery();
        while (rs.next()) {
            System.out.println("Employee: " + rs.getString("emp_name") +
                    " (ID: " + rs.getInt("employee_id") + ")" +
                    ", Date: " + rs.getDate("attend_date") +
                    ", Check-in: " + rs.getTime("check_in_time") +
                    ", Check-out: " + rs.getTime("check_out_time") +
                    ", Status: " + rs.getString("status"));
        }
    }




}
