import java.io.Serializable;
import java.time.LocalDate;

public class CalorieIntake implements Serializable {
    private static final long serialVersionUID = 1L;

    private String foodItem;
    private int calories;
    private LocalDate date;

    public CalorieIntake(String foodItem, int calories, LocalDate date) {
        this.foodItem = foodItem;
        this.calories = calories;
        this.date = date;
    }

    public String getFoodItem() {
        return foodItem;
    }

    public int getCalories() {
        return calories;
    }

    public LocalDate getDate() {
        return date;
    }

    @Override
    public String toString() {
        return "Food: " + foodItem + ", Calories: " + calories + ", Date: " + date;
    }
}
