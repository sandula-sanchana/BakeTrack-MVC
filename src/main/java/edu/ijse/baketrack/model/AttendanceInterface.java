package edu.ijse.baketrack.model;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

import edu.ijse.baketrack.dto.AttendanceDto;
import edu.ijse.baketrack.dto.CustomersDto;

public interface AttendanceInterface {

    String addAttendance(AttendanceDto attendance)  throws SQLException;

    void getAttendanceByEmployee(int employee_id, String status) throws SQLException;

    void getAttendanceOnDate(LocalDate date, String status) throws SQLException;

     ArrayList<AttendanceDto> getAllAttendance() throws  SQLException;

    String deleteAttendance(int employeeID, LocalDate attendDate) throws SQLException;

    String updateAttendance(AttendanceDto dto) throws SQLException;

    double getEmployeeTotalOTHours(int employeeId, int month, int year) throws SQLException;
}
