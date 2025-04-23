package edu.ijse.baketrack.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class SetMobileSellerController {

    @FXML
    private TableView<?> DeliveryTable;

    @FXML
    private TableView<?> TableMobileSeller;

    @FXML
    private TableColumn<?, ?> clmnArea;

    @FXML
    private TableColumn<?, ?> clmnCno;

    @FXML
    private TableColumn<?, ?> clmnDelDAte;

    @FXML
    private TableColumn<?, ?> clmnDelid;

    @FXML
    private TableColumn<?, ?> clmnEidDel;

    @FXML
    private TableColumn<?, ?> clmnEidE;

    @FXML
    private TableColumn<?, ?> clmnNAme;

    @FXML
    private TableColumn<?, ?> clmnRole;

    @FXML
    private TableColumn<?, ?> clmnVid;

    @FXML
    private VBox txtEmpID;

    @FXML
    private TextField txtPayidInput;

    @FXML
    void OrderPageGoBackButton(ActionEvent event) {

    }

    @FXML
    void btnGetDelAndEmp(ActionEvent event) {
//        getDelDetails();
//        getEmpDetails();

    }

    @FXML
    void btnSetSeller(ActionEvent event) {

    }

    

}
