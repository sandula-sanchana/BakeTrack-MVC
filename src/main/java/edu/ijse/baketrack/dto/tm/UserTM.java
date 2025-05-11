package edu.ijse.baketrack.dto.tm;

public class UserTM {
    private int user_id;
    private String user_name;
    private String roles;
    private String email;

    public UserTM(int user_id, String user_name, String roles, String email) {
        this.user_id = user_id;
        this.user_name = user_name;
        this.roles = roles;
        this.email = email;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
