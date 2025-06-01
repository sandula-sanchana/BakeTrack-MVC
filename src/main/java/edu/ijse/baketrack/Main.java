package edu.ijse.baketrack;

import edu.ijse.baketrack.util.WhatsAppSender;
import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.awt.*;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);

    }
    public void start(Stage stage) throws Exception {
        Parent parent= FXMLLoader.load(getClass().getResource("/View/loadingScreen.fxml"));
        Scene scene=new Scene(parent);
        stage.setTitle("baketrack v 1.0");
        stage.setScene(scene);

        stage.show();


        Task<Scene> task= new Task<Scene>() {
            @Override
            protected Scene call() throws Exception {
                Thread.sleep(1000);
                Parent parent= FXMLLoader.load(getClass().getResource("/View/LogInPage.fxml"));
                return new Scene(parent);
            }
        };

        task.setOnSucceeded(event ->{
            Scene mainScene=task.getValue();
            stage.setScene(mainScene);
            stage.setTitle("baketrack v 1.0");
        });

        task.setOnFailed(event -> {
            System.err.println("Failed to load main application: " + task.getException().getMessage());
          task.getException().printStackTrace();
            stage.close();
        });

        new Thread(task).start();
    }






}
