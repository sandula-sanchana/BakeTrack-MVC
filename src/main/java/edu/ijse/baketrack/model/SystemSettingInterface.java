package edu.ijse.baketrack.model;

import edu.ijse.baketrack.dto.AttendanceDto;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public interface SystemSettingInterface {

    int getOTRate() throws SQLException;

    String setOTRate(int rate) throws SQLException;
}
