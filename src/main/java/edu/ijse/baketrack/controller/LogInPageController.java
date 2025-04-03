package edu.ijse.baketrack.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class LogInPageController {

    @FXML
    private AnchorPane apLoginPage;

    @FXML
    private TextField txtUserNameSignUpPage;

    @FXML
    private TextField txtUserPasswordSignUpPage;

    @FXML
    void btnLogInloginPage(ActionEvent event) {

    }

    @FXML
    void btnSignUploginPage(ActionEvent event) {
        try {
            apLoginPage.getChildren().clear();
            AnchorPane ap= FXMLLoader.load(getClass().getResource("/View/SignupPage.fxml"));
            apLoginPage.getChildren().add(ap);
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR,"can't find signup page").show();
            e.printStackTrace();
        }
    }

    @FXML
    void hlForgotPwdLPage(ActionEvent event) {

    }

}
