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
            System.out.println("5. Get Exercise Suggestions");
            System.out.println("6. Log Out");
            System.out.println("7. Exit");
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
                                System.out.print("Enter sleep date (yyyy-mm-dd): ");  // Prompt for date
                                String sleepDateStr = scanner.next();
                                LocalDate sleepDate = LocalDate.parse(sleepDateStr);
                                System.out.print("Enter sleep time (HH:mm): ");
                                String sleepTimeStr = scanner.next();
                                LocalTime sleepTime = LocalTime.parse(sleepTimeStr);
                                System.out.print("Enter wake time (HH:mm): ");
                                String wakeTimeStr = scanner.next();
                                LocalTime wakeTime = LocalTime.parse(wakeTimeStr);
                                SleepRecord sleepRecord = new SleepRecord(sleepTime, wakeTime, sleepDate);
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
                                System.out.print("Enter start date (yyyy-mm-dd): ");
                                String sleepStartDateStr = scanner.next();
                                LocalDate sleepStartDate = LocalDate.parse(sleepStartDateStr);
                                System.out.print("Enter end date (yyyy-mm-dd): ");
                                String sleepEndDateStr = scanner.next();
                                LocalDate sleepEndDate = LocalDate.parse(sleepEndDateStr);

                                List<SleepRecord> sleepRecords = dataManager.getSleepRecords(currentUserId);
                                double averageSleep = healthAnalyzer.calculateAverageSleepOverPeriod(sleepRecords, sleepStartDate, sleepEndDate);
                                System.out.println("Average Sleep: " + averageSleep + " hours");
                                break;
                            case 3: // View exercise log
                                List<ExerciseActivity> exerciseLog = dataManager.getExerciseActivities(currentUserId);
                                System.out.println("Exercise Log:");
                                exerciseLog.forEach(System.out::println);
                                break;
                            case 4: // View health summary
                                System.out.print("Enter start date (yyyy-mm-dd): ");
                                String startDateStr = scanner.next();
                                LocalDate startDate = LocalDate.parse(startDateStr);
                                System.out.print("Enter end date (yyyy-mm-dd): ");
                                String endDateStr = scanner.next();
                                LocalDate endDate = LocalDate.parse(endDateStr);

                                calorieIntakes = dataManager.getCalorieIntakes(currentUserId);
                                exercises = dataManager.getExerciseActivities(currentUserId);
                                sleepRecords = dataManager.getSleepRecords(currentUserId);
                                healthAnalyzer.generateHealthSummary(calorieIntakes, exercises, sleepRecords, startDate, endDate);
                                break;
                            default:
                                System.out.println("Invalid option. Please try again.");
                                break;
                        }
                    } else {
                        System.out.println("Please log in to view analysis.");
                    }
                    break;
                case 5: // Get exercise suggestions
                    if (currentUserId != null) {
                        System.out.print("Enter start date (yyyy-mm-dd) for analysis: ");
                        String startDateStr = scanner.next();
                        LocalDate startDate = LocalDate.parse(startDateStr);

                        System.out.print("Enter end date (yyyy-mm-dd) for analysis: ");
                        String endDateStr = scanner.next();
                        LocalDate endDate = LocalDate.parse(endDateStr);

                        List<CalorieIntake> calorieIntakes = dataManager.getCalorieIntakes(currentUserId);
                        List<ExerciseActivity> exercises = dataManager.getExerciseActivities(currentUserId);
                        List<SleepRecord> sleepRecords = dataManager.getSleepRecords(currentUserId);

                        // Calculate averages over the selected period
                        double averageCaloricBalance = healthAnalyzer.calculateCaloricBalanceOverPeriod(calorieIntakes, exercises, startDate, endDate);
                        double averageSleep = healthAnalyzer.calculateAverageSleepOverPeriod(sleepRecords, startDate, endDate);

                        // Get exercise suggestion based on averages
                        String exerciseSuggestion = healthAnalyzer.suggestExercise(averageCaloricBalance, averageSleep);
                        System.out.println("Exercise Suggestion: " + exerciseSuggestion);
                    } else {
                        System.out.println("Please log in to get exercise suggestions.");
                    }
                    break;
                case 6: // Log out
                    if (currentUserId != null) {
                        currentUserId = null;
                        System.out.println("Successfully logged out.");
                    } else {
                        System.out.println("No user is currently logged in.");
                    }
                    break;
                case 7: // Exit application
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
