package edu.ijse.baketrack;

import edu.ijse.baketrack.util.WhatsAppSender;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.awt.*;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Parent parent= FXMLLoader.load(getClass().getResource("/View/LogInPage.fxml"));
        Scene scene=new Scene(parent);

        stage.setScene(scene);
        stage.setTitle("baketrack v 1.0");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);

        //;

    }


}
