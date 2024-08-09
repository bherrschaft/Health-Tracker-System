import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Duration;

public class SleepRecord implements Serializable {
    private static final long serialVersionUID = 1L;

    private LocalTime sleepTime;
    private LocalTime wakeTime;
    private LocalDate date;  // Add date attribute

    public SleepRecord(LocalTime sleepTime, LocalTime wakeTime, LocalDate date) {
        this.sleepTime = sleepTime;
        this.wakeTime = wakeTime;
        this.date = date;  // Initialize date
    }

    public double calculateSleepHours() {
        long sleepMinutes;
        if (wakeTime.isBefore(sleepTime) || wakeTime.equals(sleepTime)) {
            sleepMinutes = Duration.between(sleepTime, LocalTime.MAX).toMinutes() + 1;
            sleepMinutes += Duration.between(LocalTime.MIN, wakeTime).toMinutes();
        } else {
            sleepMinutes = Duration.between(sleepTime, wakeTime).toMinutes();
        }

        return sleepMinutes / 60.0;
    }

    public LocalTime getSleepTime() {
        return sleepTime;
    }

    public LocalTime getWakeTime() {
        return wakeTime;
    }

    public LocalDate getDate() {  // Getter for date
        return date;
    }

    @Override
    public String toString() {
        return "Date: " + date + ", Sleep Time: " + sleepTime + ", Wake Time: " + wakeTime;
    }
}
