package com.example.data;

import com.example.model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DataManager {

    private Connection connection;

    public DataManager() {
        try {
            connection = DatabaseConnection.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean userExists(String username) {
        String sql = "SELECT COUNT(*) FROM users WHERE username = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next() && resultSet.getInt(1) > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void addUser(String username) {
        String sql = "INSERT INTO users (username) VALUES (?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, username);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addCalorieIntake(String username, CalorieIntake intake) {
        String sql = "INSERT INTO calorie_intake (userId, foodItem, calories, date) VALUES (?, ?, ?, ?)";
        int retries = 3;
        while (retries > 0) {
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                int userId = getUserId(username);
                statement.setInt(1, userId);
                statement.setString(2, intake.getFoodItem());
                statement.setInt(3, intake.getCalories());
                statement.setDate(4, Date.valueOf(intake.getDate()));
                statement.executeUpdate();
                break; // Exit loop if successful
            } catch (SQLException e) {
                if (e.getErrorCode() == 5) { // 5 is the code for SQLITE_BUSY
                    retries--;
                    try {
                        Thread.sleep(1000); // Wait before retrying
                    } catch (InterruptedException ie) {
                        Thread.currentThread().interrupt();
                    }
                } else {
                    e.printStackTrace();
                    break; // Exit loop for non-retriable errors
                }
            }
        }
    }

    public void addExerciseActivity(String username, ExerciseActivity activity) {
        String sql = "INSERT INTO exercise_activity (userId, type, duration, caloriesBurned, date) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            int userId = getUserId(username);
            statement.setInt(1, userId);
            statement.setString(2, activity.getType());
            statement.setInt(3, activity.getDuration());
            statement.setInt(4, activity.getCaloriesBurned());
            statement.setDate(5, Date.valueOf(activity.getDate()));
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addSleepRecord(String username, SleepRecord record) {
        String sql = "INSERT INTO sleep_record (userId, sleepTime, wakeTime, date) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            int userId = getUserId(username);
            statement.setInt(1, userId);
            statement.setTime(2, Time.valueOf(record.getSleepTime()));
            statement.setTime(3, Time.valueOf(record.getWakeTime()));
            statement.setDate(4, Date.valueOf(record.getDate()));
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getHealthSummary(String username) {
        StringBuilder summary = new StringBuilder();
        int userId = getUserId(username);

        List<CalorieIntake> intakes = getCalorieIntakes(userId);
        List<ExerciseActivity> activities = getExerciseActivities(userId);
        List<SleepRecord> records = getSleepRecords(userId);

        summary.append("Calories Consumed:\n");
        for (CalorieIntake intake : intakes) {
            summary.append(String.format("Food: %s, Calories: %d, Date: %s\n", intake.getFoodItem(), intake.getCalories(), intake.getDate()));
        }

        summary.append("\nExercise Activities:\n");
        for (ExerciseActivity activity : activities) {
            summary.append(String.format("Type: %s, Duration: %d mins, Calories Burned: %d, Date: %s\n", activity.getType(), activity.getDuration(), activity.getCaloriesBurned(), activity.getDate()));
        }

        summary.append("\nSleep Records:\n");
        for (SleepRecord record : records) {
            summary.append(String.format("Sleep Time: %s, Wake Time: %s, Date: %s, Duration: %d hours\n", record.getSleepTime(), record.getWakeTime(), record.getDate(), record.calculateSleepHours()));
        }

        return summary.toString();
    }

    private int getUserId(String username) {
        String sql = "SELECT id FROM users WHERE username = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // or throw an exception if not found
    }

    private List<CalorieIntake> getCalorieIntakes(int userId) {
        List<CalorieIntake> intakes = new ArrayList<>();
        String sql = "SELECT * FROM calorie_intake WHERE userId = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, userId);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                CalorieIntake intake = new CalorieIntake(
                        rs.getString("foodItem"),
                        rs.getInt("calories"),
                        rs.getDate("date").toLocalDate()
                );
                intakes.add(intake);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return intakes;
    }

    private List<ExerciseActivity> getExerciseActivities(int userId) {
        List<ExerciseActivity> activities = new ArrayList<>();
        String sql = "SELECT * FROM exercise_activity WHERE userId = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, userId);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                ExerciseActivity activity = new ExerciseActivity(
                        rs.getString("type"),
                        rs.getInt("duration"),
                        rs.getInt("caloriesBurned"),
                        rs.getDate("date").toLocalDate()
                );
                activities.add(activity);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return activities;
    }

    private List<SleepRecord> getSleepRecords(int userId) {
        List<SleepRecord> records = new ArrayList<>();
        String sql = "SELECT * FROM sleep_record WHERE userId = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, userId);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                SleepRecord record = new SleepRecord(
                        rs.getTime("sleepTime").toLocalTime().toString(),
                        rs.getTime("wakeTime").toLocalTime().toString(),
                        rs.getDate("date").toLocalDate()
                );
                records.add(record);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return records;
    }
}
