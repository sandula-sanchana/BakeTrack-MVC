package edu.ijse.baketrack.model;

import java.sql.SQLException;
import java.util.ArrayList;

import edu.ijse.baketrack.dto.UsersDto;

public interface UsersInterface {
    String addUser(UsersDto usersDto) throws SQLException;
    String deleteUser(int user_id) throws SQLException;
    String updateUser(UsersDto usersDto) throws SQLException;
    String authenticater(String u_name,String u_password) throws SQLException;

    ArrayList<UsersDto> getAllUsers() throws SQLException;
}
