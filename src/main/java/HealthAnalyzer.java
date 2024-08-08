import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class HealthAnalyzer {

    public int calculateDailyCaloricBalance(List<CalorieIntake> calorieIntakes, List<ExerciseActivity> exercises, LocalDate date) {
        int caloriesConsumed = calorieIntakes.stream()
                .filter(c -> c.getDate().equals(date))
                .mapToInt(CalorieIntake::getCalories)
                .sum();

        int caloriesBurned = exercises.stream()
                .filter(e -> e.getDate().equals(date))
                .mapToInt(ExerciseActivity::getCaloriesBurned)
                .sum();

        return caloriesConsumed - caloriesBurned;
    }

    public double calculateAverageSleep(List<SleepRecord> sleepRecords) {
        return sleepRecords.stream()
                .mapToDouble(SleepRecord::calculateSleepHours)
                .average()
                .orElse(0.0);
    }

    public void generateHealthSummary(List<CalorieIntake> calorieIntakes, List<ExerciseActivity> exercises, List<SleepRecord> sleepRecords, LocalDate startDate, LocalDate endDate) {
        // Filter records by the specified date range
        List<CalorieIntake> filteredCalorieIntakes = calorieIntakes.stream()
                .filter(intake -> !intake.getDate().isBefore(startDate) && !intake.getDate().isAfter(endDate))
                .collect(Collectors.toList());

        List<ExerciseActivity> filteredExercises = exercises.stream()
                .filter(activity -> !activity.getDate().isBefore(startDate) && !activity.getDate().isAfter(endDate))
                .collect(Collectors.toList());

        List<SleepRecord> filteredSleepRecords = sleepRecords.stream()
                .collect(Collectors.toList()); // Sleep records currently do not have a date; you can add a date if needed

        // Calculate totals and averages
        int totalCaloriesConsumed = filteredCalorieIntakes.stream()
                .mapToInt(CalorieIntake::getCalories)
                .sum();

        int totalCaloriesBurned = filteredExercises.stream()
                .mapToInt(ExerciseActivity::getCaloriesBurned)
                .sum();

        double totalSleepHours = filteredSleepRecords.stream()
                .mapToDouble(SleepRecord::calculateSleepHours)
                .sum();

        double averageCaloricBalance = totalCaloriesConsumed - totalCaloriesBurned;

        Map<String, Long> exerciseCounts = filteredExercises.stream()
                .collect(Collectors.groupingBy(ExerciseActivity::getExerciseType, Collectors.counting()));

        String mostCommonExercise = exerciseCounts.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("None");

        // Print the summary
        System.out.println("Health Summary for period " + startDate + " to " + endDate + ":");
        System.out.println("Total Calories Consumed: " + totalCaloriesConsumed);
        System.out.println("Total Calories Burned: " + totalCaloriesBurned);
        System.out.println("Total Sleep Hours: " + totalSleepHours);
        System.out.println("Average Caloric Balance: " + averageCaloricBalance);
        System.out.println("Most Common Exercise: " + mostCommonExercise);
    }

    public String suggestExercise(int caloricBalance, double averageSleep) {
        if (caloricBalance > 0) {
            if (averageSleep < 7) {
                return "Consider low-impact exercises like walking or yoga to balance energy.";
            } else {
                return "Engage in cardio exercises like running or cycling to utilize excess calories.";
            }
        } else {
            if (averageSleep < 7) {
                return "Focus on rest and recovery exercises like stretching or pilates.";
            } else {
                return "Try strength training exercises to build muscle mass.";
            }
        }
    }
}
