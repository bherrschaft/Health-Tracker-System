import java.util.Scanner;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class HealthApp {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        DataManager dataManager = new DataManager();
        HealthAnalyzer healthAnalyzer = new HealthAnalyzer();

        System.out.println("Welcome to the Health and Wellness Monitoring System");

        boolean running = true;
        Integer currentUserId = null;

        while (running) {
            System.out.println("1. Create User");
            System.out.println("2. Log In");
            System.out.println("3. Input Data");
            System.out.println("4. View Analysis");
            System.out.println("5. Log Out");
            System.out.println("6. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1: // Create user
                    System.out.print("Enter username: ");
                    String username = scanner.next();
                    boolean userCreated = dataManager.createUser(username);
                    if (userCreated) {
                        System.out.println("User created successfully.");
                    } else {
                        System.out.println("Failed to create user.");
                    }
                    break;
                case 2: // Log in
                    System.out.print("Enter username: ");
                    username = scanner.next();
                    currentUserId = dataManager.loginUser(username);
                    if (currentUserId != null) {
                        System.out.println("Login successful.");
                    } else {
                        System.out.println("Login failed.");
                    }
                    break;
                case 3: // Input data
                    if (currentUserId != null) {
                        System.out.println("1. Input Calorie Intake");
                        System.out.println("2. Input Exercise Activity");
                        System.out.println("3. Input Sleep Record");
                        System.out.print("Choose an option: ");
                        int dataChoice = scanner.nextInt();

                        switch (dataChoice) {
                            case 1: // Input calorie intake
                                System.out.print("Enter food item: ");
                                String foodItem = scanner.next();
                                System.out.print("Enter calories: ");
                                int calories = scanner.nextInt();
                                System.out.print("Enter date (yyyy-mm-dd): ");
                                String dateStr = scanner.next();
                                LocalDate date = LocalDate.parse(dateStr);
                                CalorieIntake intake = new CalorieIntake(foodItem, calories, date);
                                dataManager.insertCalorieIntake(currentUserId, intake);
                                break;
                            case 2: // Input exercise activity
                                System.out.print("Enter exercise type: ");
                                String exerciseType = scanner.next();
                                System.out.print("Enter duration (minutes): ");
                                int duration = scanner.nextInt();
                                System.out.print("Enter calories burned: ");
                                int caloriesBurned = scanner.nextInt();
                                System.out.print("Enter date (yyyy-mm-dd): ");
                                dateStr = scanner.next();
                                date = LocalDate.parse(dateStr);
                                ExerciseActivity exercise = new ExerciseActivity(exerciseType, duration, caloriesBurned, date);
                                dataManager.insertExerciseActivity(currentUserId, exercise);
                                break;
                            case 3: // Input sleep record
                                System.out.print("Enter sleep time (HH:mm): ");
                                String sleepTimeStr = scanner.next();
                                LocalTime sleepTime = LocalTime.parse(sleepTimeStr);
                                System.out.print("Enter wake time (HH:mm): ");
                                String wakeTimeStr = scanner.next();
                                LocalTime wakeTime = LocalTime.parse(wakeTimeStr);
                                SleepRecord sleepRecord = new SleepRecord(sleepTime, wakeTime);
                                dataManager.insertSleepRecord(currentUserId, sleepRecord);
                                break;
                            default:
                                System.out.println("Invalid option. Please try again.");
                                break;
                        }
                    } else {
                        System.out.println("Please log in to input data.");
                    }
                    break;
                case 4: // View analysis
                    if (currentUserId != null) {
                        System.out.println("1. View Daily Caloric Balance");
                        System.out.println("2. View Average Sleep");
                        System.out.println("3. View Exercise Log");
                        System.out.println("4. View Health Summary");
                        System.out.print("Choose an option: ");
                        int analysisChoice = scanner.nextInt();

                        switch (analysisChoice) {
                            case 1: // View daily caloric balance
                                System.out.print("Enter date (yyyy-mm-dd): ");
                                String dateStr = scanner.next();
                                LocalDate date = LocalDate.parse(dateStr);
                                List<CalorieIntake> calorieIntakes = dataManager.getCalorieIntakes(currentUserId);
                                List<ExerciseActivity> exercises = dataManager.getExerciseActivities(currentUserId);
                                int caloricBalance = healthAnalyzer.calculateDailyCaloricBalance(calorieIntakes, exercises, date);
                                System.out.println("Caloric Balance for " + date + ": " + caloricBalance);
                                break;
                            case 2: // View average sleep
                                List<SleepRecord> sleepRecords = dataManager.getSleepRecords(currentUserId);
                                double averageSleep = healthAnalyzer.calculateAverageSleep(sleepRecords);
                                System.out.println("Average Sleep: " + averageSleep + " hours");
                                break;
                            case 3: // View exercise log
                                List<ExerciseActivity> exerciseLog = dataManager.getExerciseActivities(currentUserId);
                                System.out.println("Exercise Log:");
                                exerciseLog.forEach(System.out::println);
                                break;
                            case 4: // View health summary
                                calorieIntakes = dataManager.getCalorieIntakes(currentUserId);
                                exercises = dataManager.getExerciseActivities(currentUserId);
                                sleepRecords = dataManager.getSleepRecords(currentUserId);
                                healthAnalyzer.generateHealthSummary(calorieIntakes, exercises, sleepRecords);
                                break;
                            default:
                                System.out.println("Invalid option. Please try again.");
                                break;
                        }
                    } else {
                        System.out.println("Please log in to view analysis.");
                    }
                    break;
                case 5: // Log out
                    if (currentUserId != null) {
                        currentUserId = null;
                        System.out.println("Successfully logged out.");
                    } else {
                        System.out.println("No user is currently logged in.");
                    }
                    break;
                case 6: // Exit application
                    running = false;
                    System.out.println("Exiting application. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }

        scanner.close();
    }
}
