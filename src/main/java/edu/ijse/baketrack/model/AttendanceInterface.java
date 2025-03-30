package edu.ijse.baketrack.model;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

import edu.ijse.baketrack.dto.AttendanceDto;
import edu.ijse.baketrack.dto.CustomersDto;

public interface AttendanceInterface {

    void addAttendance(AttendanceDto attendanceDto) throws SQLException;

    void updateCheckInTime(int employee_id, LocalDate attend_date, LocalTime newCheckIn) throws SQLException;

    void updateCheckOutTime(int employee_id, LocalDate attend_date, LocalTime newCheckOut) throws SQLException;

    void updateStatus(int employee_id, LocalDate attend_date, String status) throws SQLException;

    void getAttendanceByEmployee(int employee_id, String status) throws SQLException;

    void getAttendanceOnDate(LocalDate date, String status) throws SQLException;

     ArrayList<AttendanceDto> getAllAttendance() throws  SQLException;
}
