package edu.ijse.baketrack.db;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DBobject{
     private static DBobject obo;
     private Connection connection;
 
     private DBobject() throws ClassNotFoundException,SQLException{
         Class.forName("com.mysql.cj.jdbc.Driver");
         connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/PROJECT_BAKERY","sandula","92540010");
 
     }
 
     public static DBobject getInstance() throws ClassNotFoundException,SQLException{
          if (obo==null) {
             obo=new DBobject();
          }
          return obo;
     }
 
     public  Connection getConnection(){
         return this.connection;
     }
 
 }