package edu.ijse.baketrack.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import edu.ijse.baketrack.db.DBobject;
import edu.ijse.baketrack.dto.CustomersDto;
import edu.ijse.baketrack.util.SqlExecute;

import java.sql.Connection;
import java.util.ArrayList;

public class CustomerModel implements CustomerInterface {
    private Connection connection;

    public CustomerModel() throws ClassNotFoundException, SQLException {
        this.connection= DBobject.getInstance().getConnection();
    }

    public String addCustomer(CustomersDto customersDto) throws SQLException {
        String addSql = "INSERT INTO customer (name,contact_no,address) VALUES (?,?,?)";
       Boolean done= SqlExecute.SqlExecute(addSql,customersDto.getName(),customersDto.getContact(),customersDto.getAddress());

        if (done) {
           return "updated successfully";
        } else {
           return "failed";
        }

    }

    public String updateCustomer(CustomersDto customersDto) throws SQLException {
        String addSql = "UPDATE customer SET name=?,address=?,contact_no=? WHERE customer_id=?";
        Boolean done= SqlExecute.SqlExecute(addSql,customersDto.getName(),customersDto.getAddress(),customersDto.getContact(),customersDto.getCustomerID());

        if (done) {
            return "updated successfully";
        } else {
            return "failed";
        }

    }



    public String deleteCustomer(int customer_id) throws SQLException {
        String deleteSql = "DELETE FROM customer WHERE customer_id=?";
        Boolean done=SqlExecute.SqlExecute(deleteSql,customer_id);

        if (done) {
            return "deleted successfully";
        } else {
           return "failed to delete";
        }

    }

    public void updateName(int id, String name) throws SQLException {
        String updateSql = "UPDATE customer SET name=? WHERE Customer_id=?";
        PreparedStatement updatestm = connection.prepareStatement(updateSql);
        updatestm.setString(1, name);
        updatestm.setInt(2, id);

        int rowsAffected = updatestm.executeUpdate();
        if (rowsAffected > 0) {
            System.out.println("done");
        } else {
            System.out.println("failed");
        }

    }

    public void updateContact(int id, String contact) throws SQLException {
        String updateSql = "UPDATE customer SET contact_no=? WHERE Customer_id=?";
        PreparedStatement updatestm = connection.prepareStatement(updateSql);
        updatestm.setString(1, contact);
        updatestm.setInt(2, id);

        int rowsAffected = updatestm.executeUpdate();
        if (rowsAffected > 0) {
            System.out.println("done");
        } else {
            System.out.println("failed");
        }

    }

    public void updateAddress(int id, String address) throws SQLException {
        String updateSql = "UPDATE customer SET address=? WHERE Customer_id=?";
        PreparedStatement updatestm = connection.prepareStatement(updateSql);
        updatestm.setString(1, address);
        updatestm.setInt(2, id);

        int rowsAffected = updatestm.executeUpdate();
        if (rowsAffected > 0) {
            System.out.println("address done");
        } else {
            System.out.println("failed");
        }

    }



    public ArrayList<CustomersDto> getAllCustomers()  {
        String allSql="SELECT * FROM customer";
        ArrayList<CustomersDto> getall=new ArrayList<>();

        try {
            PreparedStatement statement=connection.prepareStatement(allSql);
            ResultSet resultSet=statement.executeQuery();


            while (resultSet.next()){
                CustomersDto customersDto=new CustomersDto(resultSet.getInt("customer_id"), resultSet.getString("name"),resultSet.getString("address"),resultSet.getString("contact_no"));
                getall.add(customersDto);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage() );
            throw new RuntimeException(e);
        }
        return getall;
    }

    public CustomersDto getCustomerByCOn(int cus_no) throws SQLException {
        connection=DBobject.getInstance().getConnection();
        String sql="SELECT * FROM customer WHERE contact_no=?";
        PreparedStatement statement=connection.prepareStatement(sql);

        statement.setInt(1,cus_no);

        ResultSet resultSet=statement.executeQuery();

        if(resultSet.next()){
            return new CustomersDto(resultSet.getInt("customer_id"), resultSet.getString("name"),resultSet.getString("address"),resultSet.getString("contact_no"));
        }
        return null;
    }
}
