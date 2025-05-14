package edu.ijse.baketrack.controller;

import edu.ijse.baketrack.dto.CustomersDto;
import edu.ijse.baketrack.dto.SupplierDto;
import edu.ijse.baketrack.dto.VehicleDto;
import edu.ijse.baketrack.dto.tm.CustomersTM;
import edu.ijse.baketrack.dto.tm.SupplierTM;
import edu.ijse.baketrack.dto.tm.VehicleTM;
import edu.ijse.baketrack.model.SupplierInterface;
import edu.ijse.baketrack.model.SupplierModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public  class SupplierCrudPageController implements Initializable {

    private final String namePattern = "^[A-Za-z ]+$";
    private final String phonePattern = "^(\\+94\\d{9}|94\\d{9}|0\\d{9})$";
    private final String addressPattern = "^[A-Za-z0-9.,/\\-\\s]+$";

    private SupplierInterface supplierInterface;
    private ObservableList<SupplierTM> supplierTMObservableList= FXCollections.observableArrayList();

    public SupplierCrudPageController(){
        try {
            supplierInterface=new SupplierModel();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private AnchorPane apSupplierPage;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnUpdate;

    @FXML
    private TableColumn<SupplierTM, String> colAddress;

    @FXML
    private TableColumn<SupplierTM, String> colContact;

    @FXML
    private TableColumn<SupplierTM, String> colName;

    @FXML
    private TableColumn<SupplierTM, String> colSupplierId;

    @FXML
    private TableView<SupplierTM> supplierTable;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtContact;

    @FXML
    private TextField txtName;

    @FXML
    void btnDeleteAction(ActionEvent event) {
         deleteSupplier();
    }

    @FXML
    void btnGoBack(ActionEvent event) {
        try {
            apSupplierPage.getChildren().clear();
            AnchorPane ap= FXMLLoader.load(getClass().getResource("/View/OwnerDashboard.fxml"));
            apSupplierPage.getChildren().add(ap);
        } catch (IOException e) {
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setTitle("not found");
            alert.showAndWait();
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnSaveAction(ActionEvent event) {
            saveSupplier();
    }

    @FXML
    void btnUpdateAction(ActionEvent event) {
          updateSupplier();
    }

    @FXML
    void clickText(MouseEvent event) {
        btnSave.setDisable(false);
        btnUpdate.setDisable(false);
        btnDelete.setDisable(true);

    }

    @FXML
    void onTableClick(MouseEvent event) {
        SupplierTM selected = supplierTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            txtName.setText(selected.getName());
             txtAddress.setText(selected.getAddress());
            txtContact.setText(selected.getContact());
            btnUpdate.setDisable(false);
            btnDelete.setDisable(false);
            btnSave.setDisable(true);
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
         colSupplierId.setCellValueFactory(new PropertyValueFactory<>("supplier_id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));

        loadSuppliersToTable();
        supplierTable.setItems(supplierTMObservableList);
        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);
        btnSave.setDisable(false);

    }

    public void loadSuppliersToTable(){
        try {
            ArrayList<SupplierDto> supplierDtoArrayList = supplierInterface.getAllSuppliers();
            for (SupplierDto supplierDto : supplierDtoArrayList) {
                supplierTMObservableList.add(new SupplierTM(supplierDto.getSupplier_id(),supplierDto.getName(),supplierDto.getContact(),supplierDto.getAddress()));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveSupplier(){
        String name = txtName.getText();
        String address = txtAddress.getText();
        String contact = txtContact.getText();

        boolean validName = name.matches(namePattern);
        boolean validAddress = address.matches(addressPattern);
        boolean validContact = contact.matches(phonePattern);

        if (!validName) txtName.setStyle("-fx-border-color: red;");
        if (!validAddress) txtAddress.setStyle("-fx-border-color: red;");
        if (!validContact) txtContact.setStyle("-fx-border-color: red;");

        if (validName && validAddress && validContact) {
            SupplierDto dto = new SupplierDto(name, contact,address);
            try {
                String response = supplierInterface.addSupplier(dto);
                new Alert(Alert.AlertType.INFORMATION, response).showAndWait();
                supplierTMObservableList.clear();
                loadSuppliersToTable();
                clearFields();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else {
            new Alert(Alert.AlertType.ERROR, "Please enter valid data!").showAndWait();
        }
    }

    public void clearFields(){
        txtName.clear();
        txtAddress.clear();
        txtContact.clear();
    }

    public void updateSupplier(){
        SupplierTM selected = supplierTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            SupplierDto dto = new SupplierDto(
                   selected.getSupplier_id(),
                    txtName.getText(),txtContact.getText(),txtAddress.getText()
            );
            try {
                String response = supplierInterface.updateSupplier(dto);
                new Alert(Alert.AlertType.INFORMATION, response).showAndWait();
                supplierTMObservableList.clear();
                loadSuppliersToTable();
                clearFields();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else {
            new Alert(Alert.AlertType.WARNING, "Please select a Supplier to update.").showAndWait();
        }
    }

    public void deleteSupplier(){
        SupplierTM selected = supplierTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "Confirm Deletion", ButtonType.YES, ButtonType.NO);
            confirm.setHeaderText("Are you sure you want to delete this customer?");
            confirm.setContentText("This action cannot be undone.");

            Optional<ButtonType> result = confirm.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.YES) {
                try {
                    String response = supplierInterface.deleteSupplier(selected.getSupplier_id());
                    new Alert(Alert.AlertType.INFORMATION, response).showAndWait();
                    supplierTMObservableList.clear();
                    loadSuppliersToTable();
                    clearFields();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        } else {
            new Alert(Alert.AlertType.WARNING, "Please select a customer to delete.").showAndWait();
        }
    }
}
