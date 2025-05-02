package edu.ijse.baketrack.model;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import edu.ijse.baketrack.db.DBobject;
import edu.ijse.baketrack.dto.DeliveryDto;
import edu.ijse.baketrack.dto.EmployeeDto;
import edu.ijse.baketrack.util.SqlExecute;

import java.sql.Connection;
import java.time.LocalDate;
import java.util.ArrayList;

public class EmployeeModel implements EmployeeInterface{
    private Connection connection;

    public EmployeeModel() throws ClassNotFoundException,SQLException{
        this.connection= DBobject.getInstance().getConnection();
    }

    public String addEmployee(EmployeeDto employeeDto) throws SQLException{
        String addSql="INSERT INTO employee (emp_name,emp_address,salary,contact_no,roles) VALUES (?,?,?,?,?)";
        Boolean done=SqlExecute.SqlExecute(addSql, employeeDto.getName(),employeeDto.getAddress(), employeeDto.getSalary(),employeeDto.getContact(),employeeDto.getROle());

        if(done){
           return "updated emp successfully";
        }else{
         return "failed";
        }

    }

    public ArrayList<EmployeeDto> getAllEmployeeNamesAndIds() {
        String sql = "SELECT employee_id, emp_name FROM employee";
        ArrayList<EmployeeDto> empList = new ArrayList<>();

        try {
            ResultSet resultSet=SqlExecute.SqlExecute(sql);

            while (resultSet.next()) {
                EmployeeDto employeeDto = new EmployeeDto(resultSet.getInt("employee_id"),resultSet.getString("emp_name"));
                empList.add(employeeDto);
            }
        } catch (SQLException e) {
            System.err.println("Error fetching employee names and IDs: " + e.getMessage());
            throw new RuntimeException(e);
        }

        return empList;
    }


    public String deleteEmployee(int employee_id)throws SQLException{
         String deleteSql="DELETE FROM employee WHERE employee_id=?";

         Boolean done=SqlExecute.SqlExecute(deleteSql,employee_id);

         if(done){
            return "deleted emp successfully";
         }else{
               return "failed to delete";
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

    public ArrayList<EmployeeDto> getAllEmployee()  {
        String allSql="SELECT * FROM employee";
        ArrayList<EmployeeDto> getall=new ArrayList<>();

        try {
            PreparedStatement statement=connection.prepareStatement(allSql);
            ResultSet resultSet=statement.executeQuery();

            // LocalDate localDate_delivery =resultSet.getDate("attend_date").toLocalDate();
            while (resultSet.next()){
               EmployeeDto employeeDto= new EmployeeDto(  resultSet.getInt("employee_id"),resultSet.getString("emp_name"),resultSet.getString("emp_address"),resultSet.getDouble("salary"), resultSet.getString("contact_no"),resultSet.getString("roles"));
                getall.add(employeeDto);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage() );
            throw new RuntimeException(e);
        }
        return getall;
    }

    public ArrayList<EmployeeDto> getAllAvailableAndNonAssinEmp() throws SQLException {
        ArrayList<EmployeeDto> arrayList=new ArrayList<>();
        String searchSQl="SELECT * \n" +
                "FROM employee e \n" +
                "JOIN attendance a ON e.employee_id = a.employee_id  \n" +
                "LEFT JOIN delivery d ON e.employee_id = d.employee_id \n" +
                "WHERE a.status = 'present' \n" +
                "AND a.attend_date = CURRENT_DATE\n" +
                "AND e.roles = 'Mobile_sellers' \n" +
                "AND d.employee_id IS NULL;\n";

        ResultSet resultSet= null;
        try {
            resultSet = SqlExecute.SqlExecute(searchSQl);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        while (resultSet.next()){
            EmployeeDto employeeDto=new EmployeeDto(  resultSet.getInt("employee_id"),resultSet.getString("emp_name"),resultSet.getString("emp_address"),resultSet.getDouble("salary"), resultSet.getString("contact_no"),resultSet.getString("roles"));

            arrayList.add(employeeDto);
        }

       return arrayList;
    }


    public String updateEmployee(EmployeeDto employeeDto) throws SQLException {
        String updateSql = "UPDATE employee SET emp_name=?, emp_address=?, salary=?, contact_no=?, roles=? WHERE employee_id=?";
        boolean done = SqlExecute.SqlExecute(
                updateSql,
                employeeDto.getName(),
                employeeDto.getAddress(),
                employeeDto.getSalary(),
                employeeDto.getContact(),
                employeeDto.getROle(),
                employeeDto.getEmployee_id()
        );

        return done ? "Updated employee successfully" : "Failed to update employee";
    }



}
