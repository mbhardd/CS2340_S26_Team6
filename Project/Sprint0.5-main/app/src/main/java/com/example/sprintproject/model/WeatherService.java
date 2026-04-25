package com.example.sprintproject.model;

import android.os.Handler;
import android.os.Looper;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WeatherService {
    private static final String ATLANTA_WEATHER_URL =
            "https://api.open-meteo.com/v1/forecast"
                    + "?latitude=33.7756"
                    + "&longitude=-84.3963"
                    + "&current_weather=true"
                    + "&temperature_unit=fahrenheit";

    public void fetchCurrentWeather(WeatherCallback callback) {
        new Thread(() -> {
            try {
                URL url = new URL(ATLANTA_WEATHER_URL);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                connection.setRequestMethod("GET");
                connection.setConnectTimeout(5000);
                connection.setReadTimeout(5000);

                int responseCode = connection.getResponseCode();

                if (responseCode != HttpURLConnection.HTTP_OK) {
                    postFailure(callback, "Unable to fetch weather data");
                    return;
                }

                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(connection.getInputStream())
                );

                StringBuilder response = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }

                reader.close();

                JSONObject root = new JSONObject(response.toString());
                JSONObject currentWeather = root.getJSONObject("current_weather");

                double temperature = currentWeather.getDouble("temperature");
                int weatherCode = currentWeather.getInt("weathercode");

                String condition = convertWeatherCode(weatherCode);
                WeatherData weatherData = new WeatherData(temperature, condition);

                postSuccess(callback, weatherData);

            } catch (Exception e) {
                postFailure(callback, "Weather unavailable");
            }
        }).start();
    }

    private void postSuccess(WeatherCallback callback, WeatherData weatherData) {
        new Handler(Looper.getMainLooper()).post(() -> callback.onSuccess(weatherData));
    }

    private void postFailure(WeatherCallback callback, String errorMessage) {
        new Handler(Looper.getMainLooper()).post(() -> callback.onFailure(errorMessage));
    }

    private String convertWeatherCode(int code) {
        if (code == 0) {
            return "Clear";
        } else if (code <= 3) {
            return "Partly cloudy";
        } else if (code <= 48) {
            return "Foggy";
        } else if (code <= 67) {
            return "Rainy";
        } else if (code <= 77) {
            return "Snowy";
        } else if (code <= 82) {
            return "Showers";
        } else if (code <= 99) {
            return "Thunderstorm";
        } else {
            return "Unknown";
        }
    }
}
