package edu.ijse.baketrack.controller;

import edu.ijse.baketrack.dto.EmployeeDto;
import edu.ijse.baketrack.dto.IngredientDto;
import edu.ijse.baketrack.dto.SupplierDto;
import edu.ijse.baketrack.dto.SupplierIngredientDto;
import edu.ijse.baketrack.dto.tm.EmployeeTM;
import edu.ijse.baketrack.dto.tm.SupplierIngredientTM;
import edu.ijse.baketrack.dto.tm.SupplierTM;
import edu.ijse.baketrack.model.*;
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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class SupplierIngredientCrudPageController implements Initializable {

    private SupplierIngredientInterface supplierIngredientInterface;
    private SupplierInterface supplierInterface;
    private IngredientInterface ingredientInterface;
    private ObservableList<SupplierIngredientTM> supplierIngredientTMObservableList= FXCollections.observableArrayList();
    private ObservableList<SupplierDto> supplierDtoObservableList= FXCollections.observableArrayList();
    private ObservableList<IngredientDto> ingredientDtoObservableList= FXCollections.observableArrayList();

    public SupplierIngredientCrudPageController(){
        try {
            supplierIngredientInterface=new SupplierIngredientsModel();
            supplierInterface=new SupplierModel();
            ingredientInterface=new IngredientModel();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private AnchorPane apSupplierIngredientPage;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnUpdate;

    @FXML
    private ComboBox<IngredientDto> cmbIngredient;

    @FXML
    private ComboBox<SupplierDto> cmbSupplier;

    @FXML
    private TableColumn<SupplierIngredientTM, Integer> colIngredientID;

    @FXML
    private TableColumn<SupplierIngredientTM, LocalDate> colOrderDate;

    @FXML
    private TableColumn<SupplierIngredientTM, Double> colPrice;

    @FXML
    private TableColumn<SupplierIngredientTM, Integer> colSupplierID;

    @FXML
    private TableColumn<SupplierIngredientTM, String> colUnit;

    @FXML
    private DatePicker dateLastOrder;

    @FXML
    private TableView<SupplierIngredientTM> tblSupplierIngredient;

    @FXML
    private TextField txtPricePerUnit;

    @FXML
    private TextField txtUnit;

    @FXML
    void btnDelete(ActionEvent event) {

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "Confirm Deletion", ButtonType.YES, ButtonType.NO);
        confirm.setHeaderText("Are you sure you want to delete this Supplier_ingredients?");
        confirm.setContentText("This action cannot be undone.");

        Optional<ButtonType> result = confirm.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.YES) {
            deleteSupplierIngredient();
        }else{
            new Alert(Alert.AlertType.CONFIRMATION, "Deletion cancelled").showAndWait();
        }

    }

    @FXML
    void btnGoBack(ActionEvent event) {
        try {
            apSupplierIngredientPage.getChildren().clear();
            AnchorPane ap=FXMLLoader.load(getClass().getResource("/View/StorekeeperDashboard.fxml"));
            apSupplierIngredientPage.getChildren().add(ap);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    void btnSave(ActionEvent event) {
        saveSupplierIngredient();
    }

    @FXML
    void btnUpdate(ActionEvent event) {
          updateSupplierIngredient();
    }

    @FXML
    void tableMouseClick(MouseEvent event) {
        SupplierIngredientTM selectedTM = tblSupplierIngredient.getSelectionModel().getSelectedItem();
        if (selectedTM == null) {
            return;
        }

        for (SupplierDto supplier : supplierDtoObservableList) {
            if (supplier.getSupplier_id() == selectedTM.getSupplier_id()) {
                cmbSupplier.setValue(supplier);
                break;
            }
        }

        for (IngredientDto ingredient : ingredientDtoObservableList) {
            if (ingredient.getIngredient_id() == selectedTM.getIngredient_id()) {
                cmbIngredient.setValue(ingredient);
                break;
            }
        }

        txtPricePerUnit.setText(String.valueOf(selectedTM.getPrice_per_unit()));
        txtUnit.setText(selectedTM.getUnit());
        dateLastOrder.setValue(selectedTM.getOrder_date());
        btnUpdate.setDisable(false);
        btnDelete.setDisable(false);
        btnSave.setDisable(true);

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        colSupplierID.setCellValueFactory(new PropertyValueFactory<>("supplier_id"));
        colIngredientID.setCellValueFactory(new PropertyValueFactory<>("ingredient_id"));
        colOrderDate.setCellValueFactory(new PropertyValueFactory<>("order_date"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price_per_unit"));
        colUnit.setCellValueFactory(new PropertyValueFactory<>("unit"));

        loadSupIngToTable();
        setSuplierToCmb();
        setIngredientsToCmb();
        tblSupplierIngredient.setItems(supplierIngredientTMObservableList);
        cmbSupplier.setItems(supplierDtoObservableList);
        cmbIngredient.setItems(ingredientDtoObservableList);


        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);



    }

    public  void loadSupIngToTable(){
        try {
            ArrayList<SupplierIngredientDto> supplierIngredientDtos = supplierIngredientInterface.getAllSupplierIngredients();
            for (SupplierIngredientDto supplierIngredientDto : supplierIngredientDtos) {
                supplierIngredientTMObservableList.add(new SupplierIngredientTM(supplierIngredientDto.getSupplier_id(),supplierIngredientDto.getIngredient_id(),supplierIngredientDto.getPrice_per_unit(),
                        supplierIngredientDto.getUnit(),supplierIngredientDto.getOrder_date()));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void setSuplierToCmb(){
        try {
            ArrayList<SupplierDto> supplierIngredientDtos=supplierInterface.getAllSuppliers();
            if(supplierIngredientDtos!=null){

                    supplierDtoObservableList.addAll(supplierIngredientDtos);

            }else{
                new Alert(Alert.AlertType.ERROR,"not found any Suppliers").showAndWait();
            }
        } catch (SQLException e) {
            System.err.println("sup error get error SICP"  +e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public void setIngredientsToCmb(){
        try {
            ArrayList<IngredientDto> ingredientDtoArrayList=ingredientInterface.getAllIngredients();
            if(ingredientDtoArrayList!=null){

                    ingredientDtoObservableList.addAll(ingredientDtoArrayList);

            }else{
                new Alert(Alert.AlertType.ERROR,"not found any Ing").showAndWait();
            }
        } catch (SQLException e) {
            System.err.println("ing error get error SICP"  +e.getMessage());
            throw new RuntimeException(e);
        }

    }

    public void saveSupplierIngredient() {
        if (cmbSupplier.getValue() == null || cmbIngredient.getValue() == null ||
                txtUnit.getText().isEmpty() || txtPricePerUnit.getText().isEmpty() || dateLastOrder.getValue() == null) {

            new Alert(Alert.AlertType.ERROR, "Please fill in all fields!").showAndWait();
            return;
        }

        SupplierDto selectedSupplier = cmbSupplier.getValue();
        IngredientDto selectedIngredient = cmbIngredient.getValue();
        String unit = txtUnit.getText();
        double pricePerUnit;

        try {
            pricePerUnit = Double.parseDouble(txtPricePerUnit.getText());
        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.ERROR, "Invalid price format!").showAndWait();
            return;
        }

        LocalDate orderDate = dateLastOrder.getValue();

        SupplierIngredientDto dto = new SupplierIngredientDto(
                selectedSupplier.getSupplier_id(),
                selectedIngredient.getIngredient_id(),
                pricePerUnit,
                unit,
                orderDate
        );

        try {
            String resp = supplierIngredientInterface.addSupplierIngredient(dto);
            new Alert(Alert.AlertType.INFORMATION, resp).showAndWait();

            supplierIngredientTMObservableList.clear();
            loadSupIngToTable();
            clearFields();

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to save data: " + e.getMessage()).showAndWait();
        }
    }

    private void clearFields() {
        cmbSupplier.setValue(null);
        cmbIngredient.setValue(null);
        txtUnit.clear();
        txtPricePerUnit.clear();
        dateLastOrder.setValue(LocalDate.now());
    }

    public void updateSupplierIngredient() {

        SupplierIngredientTM selectedTM = tblSupplierIngredient.getSelectionModel().getSelectedItem();
        if (selectedTM == null) {
            new Alert(Alert.AlertType.ERROR, "Select a row first!").showAndWait();
            return;
        }

        if (cmbSupplier.getValue() == null || cmbIngredient.getValue() == null ||
                txtUnit.getText().isEmpty() || txtPricePerUnit.getText().isEmpty() || dateLastOrder.getValue() == null) {

            new Alert(Alert.AlertType.ERROR, "Fill all fields first!").showAndWait();
            return;
        }

        int supplierId = cmbSupplier.getValue().getSupplier_id();
        int ingredientId = cmbIngredient.getValue().getIngredient_id();
        String unit = txtUnit.getText();
        double pricePerUnit;

        try {
            pricePerUnit = Double.parseDouble(txtPricePerUnit.getText());
        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.ERROR, "Invalid price format!").showAndWait();
            return;
        }

        LocalDate orderDate = dateLastOrder.getValue();

        SupplierIngredientDto dto = new SupplierIngredientDto(supplierId, ingredientId, pricePerUnit, unit, orderDate);

        try {
            String resp = supplierIngredientInterface.updateSupplierIngredient(dto);
            new Alert(Alert.AlertType.INFORMATION, resp).showAndWait();

            supplierIngredientTMObservableList.clear();
            loadSupIngToTable();
            clearFields();

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Update failed: " + e.getMessage()).showAndWait();
        }
    }

    public void deleteSupplierIngredient() {
        SupplierIngredientTM selectedTM = tblSupplierIngredient.getSelectionModel().getSelectedItem();
        if (selectedTM == null) {
            new Alert(Alert.AlertType.ERROR, "Select a row first!").showAndWait();
            return;
        }

        int supplierId = selectedTM.getSupplier_id();
        int ingredientId = selectedTM.getIngredient_id();

        try {
            String resp = supplierIngredientInterface.deleteSupplierIngredient( ingredientId,supplierId);
            new Alert(Alert.AlertType.INFORMATION, resp).showAndWait();

            tblSupplierIngredient.getItems().clear();
            loadSupIngToTable();
            clearFields();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public void cmMouseClick(MouseEvent mouseEvent) {
        btnSave.setDisable(false);
        btnUpdate.setDisable(false);
        btnDelete.setDisable(true);
    }
}
