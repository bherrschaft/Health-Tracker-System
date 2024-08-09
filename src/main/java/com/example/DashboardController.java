package com.example;

import com.example.data.DataManager;
import com.example.model.CalorieIntake;
import com.example.model.ExerciseActivity;
import com.example.model.SleepRecord;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;
import java.time.LocalDate;

public class DashboardController {

    @FXML
    private TextField foodItemField, caloriesField, exerciseTypeField, exerciseDurationField, exerciseCaloriesField, sleepTimeField, wakeTimeField;

    @FXML
    private DatePicker dateField, exerciseDateField, sleepDateField;

    @FXML
    private TextArea analysisArea;

    private DataManager dataManager;
    private String currentUser;

    public DashboardController() {
        dataManager = new DataManager();
    }

    public void setCurrentUser(String username) {
        currentUser = username;
        System.out.println("Welcome, " + currentUser);
    }

    @FXML
    private void handleAddCalorieIntake() {
        String foodItem = foodItemField.getText();
        int calories = Integer.parseInt(caloriesField.getText());
        LocalDate date = dateField.getValue();

        CalorieIntake intake = new CalorieIntake(foodItem, calories, date);
        dataManager.addCalorieIntake(currentUser, intake);
        showAlert("Calorie intake added: " + foodItem);
    }

    @FXML
    private void handleAddExerciseActivity() {
        String type = exerciseTypeField.getText();
        int duration = Integer.parseInt(exerciseDurationField.getText());
        int caloriesBurned = Integer.parseInt(exerciseCaloriesField.getText());
        LocalDate date = exerciseDateField.getValue();

        ExerciseActivity activity = new ExerciseActivity(type, duration, caloriesBurned, date);
        dataManager.addExerciseActivity(currentUser, activity);
        showAlert("Exercise activity added: " + type);
    }

    @FXML
    private void handleAddSleepRecord() {
        String sleepTime = sleepTimeField.getText();
        String wakeTime = wakeTimeField.getText();
        LocalDate date = sleepDateField.getValue();

        SleepRecord record = new SleepRecord(sleepTime, wakeTime, date);
        dataManager.addSleepRecord(currentUser, record);
        showAlert("Sleep record added.");
    }

    @FXML
    private void handleViewAnalysis() {
        String analysis = dataManager.getHealthSummary(currentUser);
        analysisArea.setText(analysis);
    }

    @FXML
    private void handleLogout() {
        try {
            // Load the login scene and switch to it
            Parent loginRoot = FXMLLoader.load(getClass().getResource("/login.fxml"));
            Scene loginScene = new Scene(loginRoot, 400, 300);
            Stage stage = (Stage) analysisArea.getScene().getWindow();
            stage.setScene(loginScene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
