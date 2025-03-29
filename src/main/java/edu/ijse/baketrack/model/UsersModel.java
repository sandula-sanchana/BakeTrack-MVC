package edu.ijse.baketrack.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import edu.ijse.baketrack.dto.UsersDto;
import edu.ijse.baketrack.db.DBobject;

public class UsersModel implements UsersInterface {

    private Connection connection;

    public UsersModel() throws ClassNotFoundException, SQLException {
        this.connection= DBobject.getInstance().getConnection();
    }

    public void addUser(UsersDto usersDto) throws SQLException {
        String sql = "INSERT INTO users (user_name,user_password,roles) VALUES user_name=?,user_password=?,roles=?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, usersDto.getUserName());
        statement.setString(2, usersDto.getUserPassword());
        statement.setString(3, usersDto.getRoles());

        int updates = statement.executeUpdate();
        if (updates > 0) {
            System.out.println("user added");
        } else {
            System.out.println("fail");
        }
    }

    //public void updateUser() throws SQLException {

    //}

    public void deleteUser(int userId) throws SQLException {
        String sql = "DELETE FROM users WHERE user_id=?";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, userId);
        int rowsAffected = statement.executeUpdate();
        System.out.println(rowsAffected > 0 ? "User deleted successfully." : "Failed to delete user.");
    }

    public String authenticater(String user_name, String user_password) throws SQLException {
        String authenticateSQL = "SELECT roles,user_password as storedPassword FROM users WHERE user_name=?";
        PreparedStatement statement = connection.prepareStatement(authenticateSQL);
        statement.setString(1, user_name);

        ResultSet results = statement.executeQuery();

        if (results.next()) {

            String sp = results.getString("storedPassword");
            if (sp.equals(user_password)) {
                return results.getString("roles");
            } else {
                return "invalid password";
            }

        }
        return "invalid";
    }

}
