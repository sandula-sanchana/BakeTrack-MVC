package edu.ijse.baketrack.controller;

import edu.ijse.baketrack.model.AttendanceInterface;
import edu.ijse.baketrack.model.AttendanceModel;
import edu.ijse.baketrack.model.UsersInterface;
import edu.ijse.baketrack.model.UsersModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.sql.SQLException;

public class LogInPageController {
    private UsersInterface usersInterface;

    public  LogInPageController(){
        try {
            usersInterface=new UsersModel();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private AnchorPane apLoginPage;

    @FXML
    private TextField txtUserNameSignUpPage;

    @FXML
    private TextField txtUserPasswordSignUpPage;

    @FXML
    void btnLogInloginPage(ActionEvent event) {

        String userName=txtUserNameSignUpPage.getText();
        String password=txtUserPasswordSignUpPage.getText();
        String fxmlPath=null;

        try {
            String role=usersInterface.authenticater(userName,password);

            if(role!=null){
                if(role.equals("Admin")){
                    fxmlPath="/View/OwnerDashboard.fxml";
                }else if (role.equals("HRManager")){
                    fxmlPath="/View/HRManagerDashboard.fxml";
                }else if(role.equals("StoreManager")){
                    fxmlPath="/View/StorekeeperDashboard.fxml";
                }


                if(fxmlPath!=null){
                    apLoginPage.getChildren().clear();
                    try {
                        AnchorPane ap=FXMLLoader.load(getClass().getResource(fxmlPath));
                        apLoginPage.getChildren().add(ap);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }else{
                    new Alert(Alert.AlertType.ERROR,"can't load dashboard").showAndWait();
                }

            }else{
                new Alert(Alert.AlertType.ERROR,"Wrong Credentials!! try again").showAndWait();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    void btnSignUploginPage(ActionEvent event) {
//        try {
//            apLoginPage.getChildren().clear();
//            AnchorPane ap= FXMLLoader.load(getClass().getResource("/View/SignupPage.fxml"));
//            apLoginPage.getChildren().add(ap);
//        } catch (Exception e) {
//            new Alert(Alert.AlertType.ERROR,"can't find signup page").show();
//            e.printStackTrace();
//        }
    }

    @FXML
    void hlForgotPwdLPage(ActionEvent event) {


    }

}
