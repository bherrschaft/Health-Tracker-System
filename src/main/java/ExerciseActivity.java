import java.io.Serializable;
import java.time.LocalDate;

public class ExerciseActivity implements Serializable {
    private static final long serialVersionUID = 1L;

    private String exerciseType;
    private int duration;
    private int caloriesBurned;
    private LocalDate date;

    public ExerciseActivity(String exerciseType, int duration, int caloriesBurned, LocalDate date) {
        this.exerciseType = exerciseType;
        this.duration = duration;
        this.caloriesBurned = caloriesBurned;
        this.date = date;
    }

    public String getExerciseType() {
        return exerciseType;
    }

    public int getDuration() {
        return duration;
    }

    public int getCaloriesBurned() {
        return caloriesBurned;
    }

    public LocalDate getDate() {
        return date;
    }

    @Override
    public String toString() {
        return "Exercise: " + exerciseType + ", Duration: " + duration + " min, Calories Burned: " + caloriesBurned + ", Date: " + date;
    }
}
