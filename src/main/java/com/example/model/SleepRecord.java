package com.example.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Duration;
import java.time.format.DateTimeFormatter;

public class SleepRecord {
    private LocalTime sleepTime;
    private LocalTime wakeTime;
    private LocalDate date;

    public SleepRecord(String sleepTime, String wakeTime, LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        this.sleepTime = LocalTime.parse(sleepTime, formatter);
        this.wakeTime = LocalTime.parse(wakeTime, formatter);
        this.date = date;
    }

    public LocalTime getSleepTime() {
        return sleepTime;
    }

    public LocalTime getWakeTime() {
        return wakeTime;
    }

    public LocalDate getDate() {
        return date;
    }

    public long calculateSleepHours() {
        long hours = Duration.between(sleepTime, wakeTime).toHours();
        return hours >= 0 ? hours : (24 + hours); // Handle overnight sleep
    }
}
