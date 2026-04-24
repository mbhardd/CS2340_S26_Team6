package com.example.sprintproject.model;

public interface WeatherCallback {
    void onSuccess(WeatherData weatherData);
    void onFailure(String errorMessage);
}