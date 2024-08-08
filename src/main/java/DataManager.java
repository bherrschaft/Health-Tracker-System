import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class DataManager {
    private static final String URL = "jdbc:sqlite:Database.db"; // Database connection URL

    public DataManager() {
        initializeDatabase();
    }

    private void initializeDatabase() {
        try (Connection conn = DriverManager.getConnection(URL)) {
            if (conn != null) {
                // Create tables if they don't exist
                String createUserTable = "CREATE TABLE IF NOT EXISTS Users ("
                        + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                        + "username TEXT UNIQUE NOT NULL"
                        + ");";
                String createCalorieTable = "CREATE TABLE IF NOT EXISTS CalorieIntake ("
                        + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                        + "userId INTEGER,"
                        + "foodItem TEXT NOT NULL,"
                        + "calories INTEGER,"
                        + "date TEXT,"
                        + "FOREIGN KEY(userId) REFERENCES Users(id)"
                        + ");";
                String createExerciseTable = "CREATE TABLE IF NOT EXISTS ExerciseActivity ("
                        + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                        + "userId INTEGER,"
                        + "exerciseType TEXT NOT NULL,"
                        + "duration INTEGER,"
                        + "caloriesBurned INTEGER,"
                        + "date TEXT,"
                        + "FOREIGN KEY(userId) REFERENCES Users(id)"
                        + ");";
                String createSleepTable = "CREATE TABLE IF NOT EXISTS SleepRecord ("
                        + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                        + "userId INTEGER,"
                        + "sleepTime TEXT,"
                        + "wakeTime TEXT,"
                        + "FOREIGN KEY(userId) REFERENCES Users(id)"
                        + ");";
                try (Statement stmt = conn.createStatement()) {
                    stmt.execute(createUserTable);
                    stmt.execute(createCalorieTable);
                    stmt.execute(createExerciseTable);
                    stmt.execute(createSleepTable);
                }
                System.out.println("Database and tables initialized.");
            }
        } catch (SQLException e) {
            System.out.println("Error initializing database: " + e.getMessage());
        }
    }

    public boolean createUser(String username) {
        String sql = "INSERT INTO Users(username) VALUES(?)";

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.executeUpdate();
            return true; // Return true if user creation was successful
        } catch (SQLException e) {
            System.out.println("Error creating user: " + e.getMessage());
            return false; // Return false if there was an error
        }
    }

    public Integer loginUser(String username) {
        String sql = "SELECT id FROM Users WHERE username = ?";

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            } else {
                return null;
            }
        } catch (SQLException e) {
            System.out.println("Error logging in user: " + e.getMessage());
            return null;
        }
    }

    public void insertCalorieIntake(int userId, CalorieIntake intake) {
        String sql = "INSERT INTO CalorieIntake(userId, foodItem, calories, date) VALUES(?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            pstmt.setString(2, intake.getFoodItem());
            pstmt.setInt(3, intake.getCalories());
            pstmt.setString(4, intake.getDate().toString());
            pstmt.executeUpdate();
            System.out.println("Calorie intake recorded: " + intake);
        } catch (SQLException e) {
            System.out.println("Error inserting calorie intake: " + e.getMessage());
        }
    }

    public void insertExerciseActivity(int userId, ExerciseActivity exercise) {
        String sql = "INSERT INTO ExerciseActivity(userId, exerciseType, duration, caloriesBurned, date) VALUES(?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            pstmt.setString(2, exercise.getExerciseType());
            pstmt.setInt(3, exercise.getDuration());
            pstmt.setInt(4, exercise.getCaloriesBurned());
            pstmt.setString(5, exercise.getDate().toString());
            pstmt.executeUpdate();
            System.out.println("Exercise activity recorded: " + exercise);
        } catch (SQLException e) {
            System.out.println("Error inserting exercise activity: " + e.getMessage());
        }
    }

    public void insertSleepRecord(int userId, SleepRecord sleepRecord) {
        String sql = "INSERT INTO SleepRecord(userId, sleepTime, wakeTime) VALUES(?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            pstmt.setString(2, sleepRecord.getSleepTime().toString());
            pstmt.setString(3, sleepRecord.getWakeTime().toString());
            pstmt.executeUpdate();
            System.out.println("Sleep record recorded: " + sleepRecord);
        } catch (SQLException e) {
            System.out.println("Error inserting sleep record: " + e.getMessage());
        }
    }

    public List<CalorieIntake> getCalorieIntakes(int userId) {
        List<CalorieIntake> intakes = new ArrayList<>();
        String sql = "SELECT * FROM CalorieIntake WHERE userId = ?";

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                CalorieIntake intake = new CalorieIntake(
                        rs.getString("foodItem"),
                        rs.getInt("calories"),
                        LocalDate.parse(rs.getString("date")));
                intakes.add(intake);
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving calorie intakes: " + e.getMessage());
        }
        return intakes;
    }

    public List<ExerciseActivity> getExerciseActivities(int userId) {
        List<ExerciseActivity> activities = new ArrayList<>();
        String sql = "SELECT * FROM ExerciseActivity WHERE userId = ?";

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                ExerciseActivity exercise = new ExerciseActivity(
                        rs.getString("exerciseType"),
                        rs.getInt("duration"),
                        rs.getInt("caloriesBurned"),
                        LocalDate.parse(rs.getString("date")));
                activities.add(exercise);
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving exercise activities: " + e.getMessage());
        }
        return activities;
    }

    public List<SleepRecord> getSleepRecords(int userId) {
        List<SleepRecord> sleepRecords = new ArrayList<>();
        String sql = "SELECT * FROM SleepRecord WHERE userId = ?";

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                SleepRecord sleepRecord = new SleepRecord(
                        LocalTime.parse(rs.getString("sleepTime")),
                        LocalTime.parse(rs.getString("wakeTime")));
                sleepRecords.add(sleepRecord);
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving sleep records: " + e.getMessage());
        }
        return sleepRecords;
    }
}
