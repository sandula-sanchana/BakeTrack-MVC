package edu.ijse.baketrack.model;

import java.sql.SQLException;

import edu.ijse.baketrack.dto.UsersDto;

public interface UsersInterface {
    void addUser(UsersDto usersDto) throws SQLException;
    void deleteUser(int user_id) throws SQLException;
    //void updateUser() throws SQLException;
    String authenticater(String u_name,String u_password) throws SQLException;
}
