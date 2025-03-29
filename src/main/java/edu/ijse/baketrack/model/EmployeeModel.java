package edu.ijse.baketrack.model;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import edu.ijse.baketrack.dto.EmployeeDto;
import edu.ijse.baketrack.db.DBobject;

import java.sql.Connection;

public class EmployeeModel implements EmployeeInterface{
    private Connection connection;

    public EmployeeModel() throws ClassNotFoundException,SQLException{
        this.connection= DBobject.getInstance().getConnection();
    }

    public void addEmployee(EmployeeDto employeeDto) throws SQLException{
        String addSql="INSERT INTO employee (emp_name,emp_address,salary,roles) VALUES (?,?,?,?)";
        PreparedStatement statement=connection.prepareStatement(addSql);
        statement.setString(1, employeeDto.getName());
        statement.setString(2, employeeDto.getAddress());
        statement.setDouble(3, employeeDto.getSalary());
        statement.setString(4, employeeDto.getROle());

        int rowsAffected=statement.executeUpdate();

        if(rowsAffected>0){
           System.out.println("updated emp successfully");
        }else{
          System.out.println("failed");
        }

    }

    public void deleteEmployee(int employee_id)throws SQLException{
         String deleteSql="DELETE FROM employee WHERE employee_id=?";
         PreparedStatement statement=connection.prepareStatement(deleteSql);
         statement.setInt(1,employee_id);
         int rowsAffected=statement.executeUpdate();

         if(rowsAffected>0){
            System.out.println("deleted emp successfully");
         }else{
           System.out.println("failed to delete");
         }

    }

    public void updateName(int id,String name) throws SQLException{
         String updateSql="UPDATE employee SET emp_name=? WHERE employee_id=?";
         PreparedStatement updatestm=connection.prepareStatement(updateSql);
         updatestm.setString(1,name);
         updatestm.setInt(2, id);
         
         int rowsAffected=updatestm.executeUpdate();
         if(rowsAffected>0){
             System.out.println("done emp name");
         }else{
             System.out.println("failed");
         }

    }

    public void updateContact(int id,String contact) throws SQLException{
      String updateSql="UPDATE employee SET contact_no=? WHERE employee_id=?";
      PreparedStatement updatestm=connection.prepareStatement(updateSql);
      updatestm.setString(1,contact);
      updatestm.setInt(2, id);
      
      int rowsAffected=updatestm.executeUpdate();
      if(rowsAffected>0){
          System.out.println("done emp contact");
      }else{
          System.out.println("failed");
      }

 }

 public void updateAddress(int id,String address) throws SQLException{
  String updateSql="UPDATE employee SET emp_address=? WHERE employee_id=?";
  PreparedStatement updatestm=connection.prepareStatement(updateSql);
  updatestm.setString(1,address);
  updatestm.setInt(2, id);
  
  int rowsAffected=updatestm.executeUpdate();
  if(rowsAffected>0){
      System.out.println("emp address done");
  }else{
      System.out.println("failed");
  }

}

public void updateSalary(int id,Double salary) throws SQLException{
    String updateSql="UPDATE employee SET salary=? WHERE employee_id=?";
    PreparedStatement updatestm=connection.prepareStatement(updateSql);
    updatestm.setDouble(1,salary);
    updatestm.setInt(2, id);
    
    int rowsAffected=updatestm.executeUpdate();
    if(rowsAffected>0){
        System.out.println("emp SALARY done");
    }else{
        System.out.println("failed");
    }
  
  }

public void viewAllEmployee() throws SQLException{
  String updateSql="SELECT * FROM employee";
  PreparedStatement viewstm=connection.prepareStatement(updateSql);
  
  ResultSet rowsAffected=viewstm.executeQuery();
  while(rowsAffected.next()){
      System.out.println(rowsAffected.getString("emp_name")+"  " +rowsAffected.getString("emp_address"));
  

}
}      
}
