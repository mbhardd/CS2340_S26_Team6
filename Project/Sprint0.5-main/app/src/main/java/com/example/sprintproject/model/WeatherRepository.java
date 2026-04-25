package com.example.sprintproject.model;

public class WeatherRepository {
    private final WeatherService weatherService;

    public WeatherRepository() {
        weatherService = new WeatherService();
    }

    public void getCurrentWeather(WeatherCallback callback) {
        weatherService.fetchCurrentWeather(callback);
    }
}