package com.example;

import com.example.data.DataManager;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;

public class LoginController {

    @FXML
    private TextField usernameField;

    private DataManager dataManager;

    public LoginController() {
        dataManager = new DataManager();
    }

    @FXML
    private void handleLogin() {
        String username = usernameField.getText().trim();
        if (username.isEmpty()) {
            showAlert("Please enter a username.");
            return;
        }

        if (dataManager.userExists(username)) {
            System.out.println("Logged in as: " + username);
            // Load and switch to the dashboard scene
            loadDashboard(username);
        } else {
            showAlert("User not found. Please create an account.");
        }
    }

    @FXML
    private void handleCreateUser() {
        String username = usernameField.getText().trim();
        if (username.isEmpty()) {
            showAlert("Username cannot be empty.");
            return;
        }

        if (!dataManager.userExists(username)) {
            dataManager.addUser(username);
            System.out.println("User created: " + username);
            loadDashboard(username);
        } else {
            showAlert("User already exists.");
        }
    }

    private void loadDashboard(String username) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/dashboard.fxml"));
            Parent dashboardRoot = loader.load();
            DashboardController controller = loader.getController();
            controller.setCurrentUser(username);
            Scene dashboardScene = new Scene(dashboardRoot, 600, 400);
            Stage stage = (Stage) usernameField.getScene().getWindow();
            stage.setScene(dashboardScene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
