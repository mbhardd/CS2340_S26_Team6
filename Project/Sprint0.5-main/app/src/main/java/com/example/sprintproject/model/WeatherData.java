package com.example.sprintproject.model;

public class WeatherData {
    private final double temperature;
    private final String condition;
    private final String summary;

    public WeatherData(double temperature, String condition) {
        this.temperature = temperature;
        this.condition = condition;
        this.summary = temperature + "°F, " + condition;
    }

    public double getTemperature() {
        return temperature;
    }

    public String getCondition() {
        return condition;
    }

    public String getSummary() {
        return summary;
    }
}
