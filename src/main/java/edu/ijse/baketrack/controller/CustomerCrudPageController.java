package edu.ijse.baketrack.controller;

import edu.ijse.baketrack.dto.CustomersDto;
import edu.ijse.baketrack.dto.CustomersDto;
import edu.ijse.baketrack.dto.tm.CustomersTM;
import edu.ijse.baketrack.dto.tm.CustomersTM;
import edu.ijse.baketrack.model.CustomerInterface;
import edu.ijse.baketrack.model.CustomerModel;
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
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class CustomerCrudPageController implements Initializable {

    private final String namePattern = "^[A-Za-z ]+$";
    private final String phonePattern = "^(\\+94\\d{9}|94\\d{9}|0\\d{9})$";
    private final String addressPattern = "^[A-Za-z0-9.,/\\-\\s]+$";

    private CustomerInterface customerInterface;
    private ArrayList<CustomersDto> customerDtoArrayList = new ArrayList<>();
    private ObservableList<CustomersTM> customerTMObservableList = FXCollections.observableArrayList();

    @FXML
    private AnchorPane apCustomerPage;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnUpdate;

    @FXML
    private TableColumn<CustomersTM, String> colCustomerAddress;

    @FXML
    private TableColumn<CustomersTM, String> colCustomerContact;

    @FXML
    private TableColumn<CustomersTM, Integer> colCustomerId;

    @FXML
    private TableColumn<CustomersTM, String> colCustomerName;

    @FXML
    private TableView<CustomersTM> customerTable;

    @FXML
    private TextField txtCustomerAddress;

    @FXML
    private TextField txtCustomerContact;

    @FXML
    private TextField txtCustomerName;

    public CustomerCrudPageController() {
        try {
            customerInterface = new CustomerModel();
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colCustomerId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colCustomerName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colCustomerAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colCustomerContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        loadAllCustomersToTable();
        customerTable.setItems(customerTMObservableList);
        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);
    }

    @FXML
    void btnSaveAction(ActionEvent event) {
       saveCustomer();
    }

    @FXML
    void btnUpdateAction(ActionEvent event) {
       updateCustomer();
    }

    @FXML
    void btnDeleteAction(ActionEvent event) {
       deleteCustomer();
    }

    @FXML
    void onTableClick(MouseEvent event) {
        CustomersTM selected = customerTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            txtCustomerName.setText(selected.getName());
            txtCustomerAddress.setText(selected.getAddress());
            txtCustomerContact.setText(selected.getContact());
            btnUpdate.setDisable(false);
            btnDelete.setDisable(false);
            btnSave.setDisable(true);
        }
    }

    private void loadAllCustomersToTable() {
        try {
            customerDtoArrayList = customerInterface.getAllCustomers();
            for (CustomersDto dto : customerDtoArrayList) {
                customerTMObservableList.add(new CustomersTM(dto.getCustomerID(), dto.getName(), dto.getAddress(), dto.getContact()));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void refreshTable() {
        customerTMObservableList.clear();
        loadAllCustomersToTable();
    }

    private void clearFields() {
        txtCustomerName.clear();
        txtCustomerAddress.clear();
        txtCustomerContact.clear();
        btnSave.setDisable(false);
        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);
    }

    @FXML
    void btnGoBack(ActionEvent event) {

    }

    public void deleteCustomer(){
        CustomersTM selected = customerTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "Confirm Deletion", ButtonType.YES, ButtonType.NO);
            confirm.setHeaderText("Are you sure you want to delete this customer?");
            confirm.setContentText("This action cannot be undone.");

            Optional<ButtonType> result = confirm.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.YES) {
                try {
                    String response = customerInterface.deleteCustomer(selected.getId());
                    new Alert(Alert.AlertType.INFORMATION, response).showAndWait();
                    refreshTable();
                    clearFields();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        } else {
            new Alert(Alert.AlertType.WARNING, "Please select a customer to delete.").showAndWait();
        }

    }

    public  void updateCustomer(){
        CustomersTM selected = customerTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            CustomersDto dto = new CustomersDto(
                    selected.getId(),
                    txtCustomerName.getText(),
                    txtCustomerAddress.getText(),
                    txtCustomerContact.getText()
            );
            try {
                String response = customerInterface.updateCustomer(dto);
                new Alert(Alert.AlertType.INFORMATION, response).showAndWait();
                refreshTable();
                clearFields();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else {
            new Alert(Alert.AlertType.WARNING, "Please select a customer to update.").showAndWait();
        }
    }

    public void saveCustomer(){
        String name = txtCustomerName.getText();
        String address = txtCustomerAddress.getText();
        String contact = txtCustomerContact.getText();

        boolean validName = name.matches(namePattern);
        boolean validAddress = address.matches(addressPattern);
        boolean validContact = contact.matches(phonePattern);

        if (!validName) txtCustomerName.setStyle("-fx-border-color: red;");
        if (!validAddress) txtCustomerAddress.setStyle("-fx-border-color: red;");
        if (!validContact) txtCustomerContact.setStyle("-fx-border-color: red;");

        if (validName && validAddress && validContact) {
            CustomersDto dto = new CustomersDto(name, address, contact);
            try {
                String response = customerInterface.addCustomer(dto);
                new Alert(Alert.AlertType.INFORMATION, response).showAndWait();
                refreshTable();
                clearFields();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else {
            new Alert(Alert.AlertType.ERROR, "Please enter valid data!").showAndWait();
        }
    }
}
