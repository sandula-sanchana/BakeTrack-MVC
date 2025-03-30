package edu.ijse.baketrack.dto;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;;

public class PayrollDto {
    private int payroll_id;
    private int employee_id;
    private LocalDate pay_month;
    private LocalTime over_time_hours;
    private double base_salary;
    private double full_salary;
    private String status;

    public PayrollDto(int payroll_id, int employee_id, LocalDate pay_month, LocalTime over_time_hours, double base_salary, double full_salary, String status) {
        this.payroll_id = payroll_id;
        this.employee_id = employee_id;
        this.pay_month = pay_month;
        this.over_time_hours = over_time_hours;
        this.base_salary = base_salary;
        this.full_salary = full_salary;
        this.status = status;
        calculateFullSalary();
    }

    public int getPayroll_id() {
        return payroll_id;
    }

    public void setPayroll_id(int payroll_id) {
        this.payroll_id = payroll_id;
    }

    public int getEmployeeID() {
        return employee_id;
    }

    public void setEmployeeID(int employee_id) {
        this.employee_id = employee_id;
    }

    public LocalDate getPayMonth() {
        return pay_month;
    }

    public void setPayMonth(String pay_month_String) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate pay_month = LocalDate.parse(pay_month_String, df);

        this.pay_month = pay_month;
    }

    public LocalTime getOverTimeHours() {
        return over_time_hours;
    }

    public void setOverTimeHours(String over_time_hours_String) {
        DateTimeFormatter dft = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime over_time_hours = LocalTime.parse(over_time_hours_String, dft);
        this.over_time_hours = over_time_hours;
        calculateFullSalary();
    }

    public double getBaseSalary() {
        return base_salary;
    }

    public void setBaseSalary(double base_salary) {
        this.base_salary = base_salary;
        calculateFullSalary();
    }

    public double getFullSalary() {
        return full_salary;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void calculateFullSalary() {
        int over_time_hours_in_minutes = (this.over_time_hours.getHour() * 60) + this.over_time_hours.getMinute();
        this.full_salary = this.base_salary + (over_time_hours_in_minutes * 3.33);
    }
}