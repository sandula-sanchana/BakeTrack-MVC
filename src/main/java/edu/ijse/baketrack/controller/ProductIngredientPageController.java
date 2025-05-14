package edu.ijse.baketrack.controller;

import edu.ijse.baketrack.dto.*;
import edu.ijse.baketrack.dto.tm.EmployeeTM;
import edu.ijse.baketrack.dto.tm.ProductIngredientTM;
import edu.ijse.baketrack.dto.tm.UserTM;
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
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class ProductIngredientPageController implements Initializable {

    private ProductIngredientInterface productIngredientInterface;
    private ProductInterface productInterface;
    private IngredientInterface ingredientInterface;
    private ObservableList<ProductIngredientTM> productIngredientTMObservableList= FXCollections.observableArrayList();
    private ObservableList<ProductDto>productDtoObservableList= FXCollections.observableArrayList();
    private ObservableList<IngredientDto> ingredientDtoObservableList= FXCollections.observableArrayList();

    public ProductIngredientPageController(){
        try {
            productIngredientInterface=new ProductIngredientModel();
            productInterface=new ProductModel();
            ingredientInterface=new IngredientModel();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private AnchorPane apProductIngredientPage;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnUpdate;

    @FXML
    private ComboBox<IngredientDto> cmbIngredient;

    @FXML
    private ComboBox<ProductDto> cmbProduct;

    @FXML
    private TableColumn<ProductIngredientTM,Integer> colID;

    @FXML
    private TableColumn<ProductIngredientTM,Integer> colIngredient;

    @FXML
    private TableColumn<ProductIngredientTM,Integer> colProduct;

    @FXML
    private TableColumn<ProductIngredientTM,Double> colQuantity;

    @FXML
    private TableColumn<ProductIngredientTM,String> colUnit;

    @FXML
    private TableView<ProductIngredientTM> tableProductIngredient;

    @FXML
    private TextField txtQuantityPerProduct;

    @FXML
    private TextField txtUnit;

    @FXML
    void btnDelete(ActionEvent event) {
        Alert alert=new Alert(Alert.AlertType.CONFIRMATION,"Confirm Deletion", ButtonType.YES,ButtonType.NO);
        alert.setHeaderText("Are you sure you want to delete this Product_ingredient?");
        alert.setContentText("This action cannot be undone.");

        Optional<ButtonType> resp=alert.showAndWait();
        if(resp.isPresent() && resp.get()==ButtonType.YES){
           deleteIngredient();
        }else{
            new Alert(Alert.AlertType.ERROR, "delete customer Cancelled.").show();
        }
    }

    @FXML
    void btnGoBack(ActionEvent event) {
        apProductIngredientPage.getChildren().clear();
        try {
            AnchorPane ap= FXMLLoader.load(getClass().getResource("/View/StorekeeperDashboard.fxml"));
            apProductIngredientPage.getChildren().add(ap);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnSave(ActionEvent event) {
        saveProductIngredient();
    }

    @FXML
    void btnUpdate(ActionEvent event) {
          updateProductIngredient();
    }

    @FXML
    void cmbMouseClick(MouseEvent event) {
        btnSave.setDisable(false);
        btnDelete.setDisable(true);
        btnUpdate.setDisable(false);

    }

    @FXML
    void tableMouseClick(MouseEvent event) {
         clickOnTable();
        btnSave.setDisable(true);
        btnDelete.setDisable(false);
        btnUpdate.setDisable(false);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
           colID.setCellValueFactory(new PropertyValueFactory<>("id"));
           colIngredient.setCellValueFactory(new PropertyValueFactory<>("ingredient_id"));
            colProduct.setCellValueFactory(new PropertyValueFactory<>("product_id"));
            colQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity_per_product"));
          colUnit.setCellValueFactory(new PropertyValueFactory<>("unit"));

          loadProductIngredientsToTable();
          tableProductIngredient.setItems(productIngredientTMObservableList);
          loadProductToCMb();
          loadIngredientsToCMb();
          cmbProduct.setItems(productDtoObservableList);
          cmbIngredient.setItems(ingredientDtoObservableList);


        btnSave.setDisable(false);
        btnDelete.setDisable(true);
        btnUpdate.setDisable(true);

    }


    public  void loadProductIngredientsToTable(){
        try {
            ArrayList<ProductIngredientDto> productIngredientDtoArrayList =productIngredientInterface.getAll();
            for (ProductIngredientDto productIngredientDto : productIngredientDtoArrayList) {
               productIngredientTMObservableList.add(new ProductIngredientTM(productIngredientDto.getId(),productIngredientDto.getProduct_id(),
                       productIngredientDto.getIngredient_id(),productIngredientDto.getQuantity_per_product(),productIngredientDto.getUnit()));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void loadProductToCMb(){
        try {
            ArrayList<ProductDto> productDtoArrayList=productInterface.getAllProducts();
            if(productDtoArrayList!=null){

                  productDtoObservableList.addAll(productDtoArrayList);

            }else{
                new Alert(Alert.AlertType.ERROR,"not found any product").showAndWait();
            }
        } catch (SQLException e) {
            System.err.println("product detail error PIPC"  +e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public void loadIngredientsToCMb(){
        try {
            ArrayList<IngredientDto> ingredientDtoArrayList=ingredientInterface.getAllIngredients();
            if(ingredientDtoArrayList!=null){

                ingredientDtoObservableList.addAll(ingredientDtoArrayList);

            }else{
                new Alert(Alert.AlertType.ERROR,"not found any Ing").showAndWait();
            }
        } catch (SQLException e) {
            System.err.println("Ing detail error PIPC"  +e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public void clickOnTable(){
        ProductIngredientTM selectedTM = tableProductIngredient.getSelectionModel().getSelectedItem();
        if (selectedTM != null) {

            for (ProductDto product : productDtoObservableList) {
                if (product.getProduct_id() == selectedTM.getProduct_id()) {
                    cmbProduct.setValue(product);
                    break;
                }
            }


            for (IngredientDto ingredient : ingredientDtoObservableList) {
                if (ingredient.getIngredient_id() == selectedTM.getIngredient_id()) {
                    cmbIngredient.setValue(ingredient);
                    break;
                }
            }

            txtQuantityPerProduct.setText(String.valueOf(selectedTM.getQuantity_per_product()));
            txtUnit.setText(selectedTM.getUnit());
        }
    }

    public void saveProductIngredient() {

        if (cmbProduct.getValue() == null || cmbIngredient.getValue() == null || txtQuantityPerProduct.getText().isEmpty() || txtUnit.getText().isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Please fill all required fields").showAndWait();
            return;
        }

        ProductDto selectedProduct = cmbProduct.getValue();
        IngredientDto selectedIngredient = cmbIngredient.getValue();

        int productId = selectedProduct.getProduct_id();
        int ingredientId = selectedIngredient.getIngredient_id();
        int quantityRequired;

        try {
            quantityRequired = Integer.parseInt(txtQuantityPerProduct.getText().trim());
        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.ERROR, "Quantity must be a number").showAndWait();
            return;
        }

        String unit = txtUnit.getText().trim();

        ProductIngredientDto dto = new ProductIngredientDto(productId, ingredientId, quantityRequired, unit);

        try {
            String response = productIngredientInterface.addProductIngredient(dto);
            new Alert(Alert.AlertType.INFORMATION, response).showAndWait();

            tableProductIngredient.getItems().clear();
            loadProductIngredientsToTable();
            clearFields();

        } catch (SQLException e) {
            System.err.println("Failed to save product ingredient: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public void clearFields() {
        cmbProduct.setValue(null);
        cmbIngredient.setValue(null);
        txtQuantityPerProduct.clear();
        txtUnit.clear();
    }

    public void updateProductIngredient(){


        if (cmbProduct.getValue() == null || cmbIngredient.getValue() == null || txtQuantityPerProduct.getText().isEmpty() || txtUnit.getText().isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Please fill all required fields").showAndWait();
            return;
        }

        ProductIngredientTM selected=tableProductIngredient.getSelectionModel().getSelectedItem();

        if(selected!=null){
            ProductDto selectedProduct = cmbProduct.getValue();
            IngredientDto selectedIngredient = cmbIngredient.getValue();

            int productId = selectedProduct.getProduct_id();
            int ingredientId = selectedIngredient.getIngredient_id();
            int quantityRequired;

            try {
                quantityRequired = Integer.parseInt(txtQuantityPerProduct.getText().trim());
            } catch (NumberFormatException e) {
                new Alert(Alert.AlertType.ERROR, "Quantity must be a number").showAndWait();
                return;
            }

            String unit = txtUnit.getText().trim();

            ProductIngredientDto dto = new ProductIngredientDto(selected.getId(),productId, ingredientId, quantityRequired, unit);

            try {
                String response = productIngredientInterface.updateProductIngredient(dto);
                new Alert(Alert.AlertType.INFORMATION, response).showAndWait();

                tableProductIngredient.getItems().clear();
                loadProductIngredientsToTable();
                clearFields();

            } catch (Exception e) {
                System.err.println("Failed to update product ingredient: " + e.getMessage());
                throw new RuntimeException(e);


            }
        }

    }

    public void deleteIngredient(){

        ProductIngredientTM selected=tableProductIngredient.getSelectionModel().getSelectedItem();

        if(selected!=null){
            try {
                String resp=productIngredientInterface.deleteProductIngredient(selected.getId());
                new Alert(Alert.AlertType.INFORMATION, resp).showAndWait();
                tableProductIngredient.getItems().clear();
                loadProductIngredientsToTable();
                clearFields();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }else{
            new Alert(Alert.AlertType.INFORMATION, "select from the table first").showAndWait();
        }

    }


}
