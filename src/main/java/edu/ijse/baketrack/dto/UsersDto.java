package edu.ijse.baketrack.dto;

public class UsersDto {
    private int user_id;
    private String user_name;
    private String user_password;
    private String roles;

    public UsersDto(int user_id, String user_name, String user_password, String roles) {
        this.user_id = user_id;
        this.user_name = user_name;
        this.user_password = user_password;
        this.roles = roles;
    }

    public int getUserID() {
        return user_id;
    }

    public void setUserID(int user_id) {
        this.user_id = user_id;
    }

    public String getUserName() {
        return user_name;
    }

    public void setUserName(String user_name) {
        this.user_name = user_name;
    }

    public String getUserPassword() {
        return user_password;
    }

    public void setUserPassword(String user_password) {
        this.user_password = user_password;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }
}
