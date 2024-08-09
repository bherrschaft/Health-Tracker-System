package com.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class HealthTrackerApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Load the login FXML file
        Parent root = FXMLLoader.load(getClass().getResource("/login.fxml"));

        // Set the scene and show the stage
        Scene scene = new Scene(root, 400, 300);
        scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
        primaryStage.setTitle("Health Tracker App");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
