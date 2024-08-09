package com.example.model;

import java.time.LocalDate;

public class CalorieIntake {
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
}
