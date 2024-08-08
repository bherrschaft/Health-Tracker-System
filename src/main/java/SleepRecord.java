import java.io.Serializable;
import java.time.LocalTime;
import java.time.Duration;

public class SleepRecord implements Serializable {
    private static final long serialVersionUID = 1L;

    private LocalTime sleepTime;
    private LocalTime wakeTime;

    public SleepRecord(LocalTime sleepTime, LocalTime wakeTime) {
        this.sleepTime = sleepTime;
        this.wakeTime = wakeTime;
    }

    public double calculateSleepHours() {
        // Calculate total sleep minutes
        long sleepMinutes;
        if (wakeTime.isBefore(sleepTime) || wakeTime.equals(sleepTime)) {
            // Handle overnight sleep
            sleepMinutes = Duration.between(sleepTime, LocalTime.MAX).toMinutes() + 1; // Minutes till midnight
            sleepMinutes += Duration.between(LocalTime.MIN, wakeTime).toMinutes(); // Minutes from midnight
        } else {
            // Handle normal sleep within the same day
            sleepMinutes = Duration.between(sleepTime, wakeTime).toMinutes();
        }

        return sleepMinutes / 60.0; // Convert minutes to hours
    }

    public LocalTime getSleepTime() {
        return sleepTime;
    }

    public LocalTime getWakeTime() {
        return wakeTime;
    }

    @Override
    public String toString() {
        return "Sleep Time: " + sleepTime + ", Wake Time: " + wakeTime;
    }
}
