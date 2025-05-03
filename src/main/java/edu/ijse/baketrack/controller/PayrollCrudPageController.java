package edu.ijse.baketrack.controller;

import edu.ijse.baketrack.dto.AttendanceDto;
import edu.ijse.baketrack.dto.EmployeeDto;
import edu.ijse.baketrack.dto.PayrollDto;
import edu.ijse.baketrack.dto.tm.AttendanceTM;
import edu.ijse.baketrack.dto.tm.EmployeeTM;
import edu.ijse.baketrack.dto.tm.PayrollTM;
import edu.ijse.baketrack.model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class PayrollCrudPageController implements Initializable {

    public Label txtOTrate;
    private EmployeeInterface employeeInterface;
    private PayrollInterface payrollInterface;
    private ObservableList<EmployeeTM>  employeeTMObservableList= FXCollections.observableArrayList();
    private ObservableList<PayrollTM> payrollTMObservableList=FXCollections.observableArrayList();
    private EmployeeTM employeeTM;
    private PayrollTM payrollTM;
    private SystemSettingInterface systemSettingInterface;
    private AttendanceInterface attendanceInterface;
    private boolean suppressDatePickerEvent = false;

    public PayrollCrudPageController(){
    try {
        employeeInterface=new EmployeeModel();
       payrollInterface=new PayrollModel();
       systemSettingInterface=new SystemSettingModel();
       attendanceInterface=new AttendanceModel();
    } catch (ClassNotFoundException e) {
        throw new RuntimeException(e);
    } catch (SQLException e) {
        System.err.println("classes not found"+e.getMessage());
        throw new RuntimeException(e);
    }
}

    public Button btnUpdateID;
    public Button btnDeleteID;
    public Button btnSaveID;
    @FXML
    private AnchorPane apPayrollPage;

    @FXML
    private TableColumn<AttendanceTM, Double> colBaseSalary;

    @FXML
    private TableColumn<AttendanceTM,Integer> colEmpID;

    @FXML
    private TableColumn<AttendanceTM, Double> colFullSalary;

    @FXML
    private TableColumn<AttendanceTM, Double> colOvertime;

    @FXML
    private TableColumn<AttendanceTM, LocalDate> colPayDate;

    @FXML
    private TableColumn<AttendanceTM, Integer> colPayrollID;

    @FXML
    private TableColumn<AttendanceTM, String> colStatus;

    @FXML
    private ComboBox<EmployeeTM> comboBoxEmp;

    @FXML
    private ComboBox<String> comboBoxStatus;

    @FXML
    private DatePicker payDatePicker;

    @FXML
    private TableView<PayrollTM> tablePayroll;

    @FXML
    private TextField txtBaseSalary;

    @FXML
    private TextField txtFullSalary;

    @FXML
    private TextField txtOvertimeHour;

    @FXML
    private TextField txtOvertimeMin;

    @FXML
    void btnDelete(ActionEvent event) {

    }

    @FXML
    void btnGoBack(ActionEvent event) {

    }

    @FXML
    void btnSave(ActionEvent event) {

    }

    @FXML
    void btnUpdate(ActionEvent event) {

    }

    @FXML
    void cmbClicked(MouseEvent event) {

    }

    @FXML
    void tableMouseClick(MouseEvent event) {
        setSelectedOneToFields();
        btnSaveID.setDisable(true);
        btnDeleteID.setDisable(false);
        btnUpdateID.setDisable(false);

    }

    public void setEmpToCmb(){
        try {
            ArrayList<EmployeeDto> employeeDtoArrayList=employeeInterface.getAllEmployeeNamesAndIds();
            if(employeeDtoArrayList!=null){
                for (EmployeeDto employeeDto :employeeDtoArrayList) {
                    employeeTM = new EmployeeTM(employeeDto.getEmployee_id(),employeeDto.getName());
                    employeeTMObservableList.addAll(employeeTM);
                }
            }else{
                new Alert(Alert.AlertType.ERROR,"not found any Emp").showAndWait();
            }
        } catch (SQLException e) {
            System.err.println("emp detail error ACPC"  +e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colPayrollID.setCellValueFactory(new PropertyValueFactory<>("Payroll_id"));
        colEmpID.setCellValueFactory(new PropertyValueFactory<>("Employee_id"));
        colPayDate.setCellValueFactory(new PropertyValueFactory<>("Pay_Date"));
        colOvertime.setCellValueFactory(new PropertyValueFactory<>("Over_time_hours"));
        colBaseSalary.setCellValueFactory(new PropertyValueFactory<>("Base_salary"));
        colFullSalary.setCellValueFactory(new PropertyValueFactory<>("Full_salary"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("Status"));

        loadAllPayrollsToTable();
        tablePayroll.setItems(payrollTMObservableList);
         setEmpToCmb();
        comboBoxEmp.setItems(employeeTMObservableList);
        comboBoxStatus.getItems().addAll("paid","not paid");

        btnSaveID.setDisable(false);
        btnDeleteID.setDisable(true);
        btnUpdateID.setDisable(true);
        getOTRate();

        comboBoxEmp.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                getTotalOTtime();
            }
        });
    }

    public void loadAllPayrollsToTable() {

        try {
            ArrayList<PayrollDto> list = payrollInterface.getAllPayrolls();
            for (PayrollDto dto : list) {
                   payrollTM = new PayrollTM(dto.getPayroll_id(),dto.getEmployee_id(),dto.getPay_Date(),
                           dto.getOver_time_hours(),dto.getBase_salary(),dto.getFull_salary(),dto.getStatus());
                payrollTMObservableList.add(payrollTM);
            }

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Error loading attendance data").showAndWait();
            e.printStackTrace();
        }
    }

    public void setSelectedOneToFields(){
        payrollTM = tablePayroll.getSelectionModel().getSelectedItem();
        if (payrollTM != null) {
            for (EmployeeTM etm : employeeTMObservableList) {
                if (etm.getEmployeeID() == payrollTM.getEmployee_id()) {
                    comboBoxEmp.setValue(etm);
                    break;
                }
            }

            comboBoxStatus.setValue(payrollTM.getStatus());

            payDatePicker.setValue(payrollTM.getPay_Date());

            txtOvertimeHour.setText(String.valueOf(payrollTM.getOver_time_hours()));

            txtBaseSalary.setText(String.valueOf(payrollTM.getBase_salary()));

            txtFullSalary.setText(String.valueOf(payrollTM.getFull_salary()));

        }
    }

    public void getOTRate(){
        try {
            int rate=systemSettingInterface.getOTRate();
            if(rate>0) {
                txtOTrate.setText(String.valueOf(rate));
            }else {
                new Alert(Alert.AlertType.ERROR,"rate not found").showAndWait();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void getBaseSalaryOfEmp(){
        employeeTM=comboBoxEmp.getSelectionModel().getSelectedItem();
        int emp_id=employeeTM.getEmployeeID();
        try {
            Double base_salary=employeeInterface.getSalaryById(emp_id);
            if(base_salary!=null){
                txtBaseSalary.setText(String.valueOf(base_salary));
            }else{
                new Alert(Alert.AlertType.ERROR,"baseSalary not found").showAndWait();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void getTotalOTtime(){
        employeeTM=comboBoxEmp.getSelectionModel().getSelectedItem();
        int emp_id=employeeTM.getEmployeeID();
        if(payDatePicker.getValue()!=null) {
              LocalDate date=payDatePicker.getValue();
              int year=date.getYear();
              int month=date.getMonthValue();
            try {
                Double ot_time = attendanceInterface.getEmployeeTotalOTHours(emp_id,month,year);
                if(ot_time>0.0){
                    txtOvertimeHour.setText(String.valueOf(ot_time));
                }else{
                    txtOvertimeHour.setText(String.valueOf(ot_time));
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }else {
            new Alert(Alert.AlertType.ERROR,"select a date first").showAndWait();
        }
    }

    public void empSelected(ActionEvent actionEvent) {
        getBaseSalaryOfEmp();

    }

    public void dateSelected(ActionEvent actionEvent) {
        if (suppressDatePickerEvent) {
            return;
        }
        if(comboBoxEmp.getSelectionModel().getSelectedItem()!=null) {
            getTotalOTtime();
        }else {
            new Alert(Alert.AlertType.ERROR,"select a Emp first").showAndWait();
            suppressDatePickerEvent = true;
            payDatePicker.setValue(null);
            suppressDatePickerEvent = false;
        }
    }

}
