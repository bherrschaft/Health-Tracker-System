package com.example.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnection {

    private static final String URL = "jdbc:sqlite:Database.db";

    public static Connection getConnection() throws SQLException {
        Connection connection = DriverManager.getConnection(URL);
        initializeDatabase(connection);
        return connection;
    }

    private static void initializeDatabase(Connection connection) {
        try (Statement statement = connection.createStatement()) {
            // Create users table
            String createUserTable = "CREATE TABLE IF NOT EXISTS users (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "username TEXT UNIQUE NOT NULL)";
            statement.execute(createUserTable);

            // Create calorie_intake table
            String createCalorieIntakeTable = "CREATE TABLE IF NOT EXISTS calorie_intake (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "userId INTEGER," +
                    "foodItem TEXT," +
                    "calories INTEGER," +
                    "date DATE," +
                    "FOREIGN KEY (userId) REFERENCES users(id))";
            statement.execute(createCalorieIntakeTable);

            // Create exercise_activity table
            String createExerciseActivityTable = "CREATE TABLE IF NOT EXISTS exercise_activity (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "userId INTEGER," +
                    "type TEXT," +
                    "duration INTEGER," +
                    "caloriesBurned INTEGER," +
                    "date DATE," +
                    "FOREIGN KEY (userId) REFERENCES users(id))";
            statement.execute(createExerciseActivityTable);

            // Create sleep_record table
            String createSleepRecordTable = "CREATE TABLE IF NOT EXISTS sleep_record (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "userId INTEGER," +
                    "sleepTime TIME," +
                    "wakeTime TIME," +
                    "date DATE," +
                    "FOREIGN KEY (userId) REFERENCES users(id))";
            statement.execute(createSleepRecordTable);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
