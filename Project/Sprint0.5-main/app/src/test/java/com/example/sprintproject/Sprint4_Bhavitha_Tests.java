package com.example.sprintproject;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.example.sprintproject.model.WeatherData;

import org.junit.Test;

public class Sprint4_Bhavitha_Tests {

    @Test
    public void weatherDataCreatesCorrectSummary() {
        WeatherData weatherData = new WeatherData(65.1, "Partly cloudy");

        assertEquals(65.1, weatherData.getTemperature(), 0.001);
        assertEquals("Partly cloudy", weatherData.getCondition());
        assertEquals("65.1°F, Partly cloudy", weatherData.getSummary());
    }

    @Test
    public void weatherDataHandlesUnavailableCondition() {
        WeatherData weatherData = new WeatherData(0.0, "Unavailable");

        assertNotNull(weatherData.getSummary());
        assertEquals("Unavailable", weatherData.getCondition());
        assertEquals("0.0°F, Unavailable", weatherData.getSummary());
    }
}