package edu.ijse.baketrack.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class AttendanceDto {
    private int employee_id;
    private LocalDate attend_date;
    private LocalTime check_in_time;
    private LocalTime check_out_time;
    private String status;

    public AttendanceDto(int employee_id, LocalDate attend_date, LocalTime check_in_time, LocalTime check_out_time, String status){
           this.employee_id=employee_id;
           this.attend_date=attend_date;
           this.check_in_time=check_in_time;
           this.check_out_time=check_out_time;
           this.status=status;
    }
    public int getEmpID(){
        return this.employee_id;
    }
    public LocalDate getAttendDate(){
        return this.attend_date;
   }
   public LocalTime getCheckInTime(){
        return this.check_in_time;
   }
   public LocalTime getCheckOutTime(){
        return this.check_out_time;
   }
   public String getStatus(){
        return this.status;
   }

   public void setEmpID(int employee_id){
       this.employee_id=employee_id;
   }
   public void setAttendDate(String attend_date_String){
     DateTimeFormatter format=DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate attend_date=LocalDate.parse(attend_date_String,format);
        
        this.attend_date=attend_date;
   }
    public void setCheckInTime(String check_in_time_String){
      DateTimeFormatter format=DateTimeFormatter.ofPattern("HH:mm:ss");
      LocalTime check_in_time=LocalTime.parse(check_in_time_String,format);
     
         this.check_in_time=check_in_time;
   }
   public void setCheckOutTime(String check_out_time_String){
      DateTimeFormatter format=DateTimeFormatter.ofPattern("HH:mm:ss");
      LocalTime check_out_time=LocalTime.parse(check_out_time_String,format);
     
        this.check_out_time=check_out_time;
   }
   public void setStatus(String status){
    this.status=status;
   }

   public String toString() {
       return "Attendance{" +
               "employee_id=" + employee_id +
               ", attend_date=" + attend_date +
               ", check_in_time=" + check_in_time +
               ", check_out_time=" + check_out_time +
               ", status=" + status +
               '}';
   }
   

   

}
