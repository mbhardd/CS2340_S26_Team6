package com.example.sprintproject.viewmodel;

import androidx.lifecycle.ViewModel;

import com.example.sprintproject.model.AuthRepository;
import com.example.sprintproject.model.WeatherCallback;
import com.example.sprintproject.model.WeatherData;
import com.example.sprintproject.model.WeatherRepository;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class IssueCreationViewModel extends ViewModel {

    private DatabaseReference db = FirebaseDatabase.getInstance().getReference();
    private AuthRepository authRepository;
    private WeatherRepository weatherRepository;

    public IssueCreationViewModel() {
        authRepository = AuthRepository.getInstance();
        weatherRepository = new WeatherRepository();
    }

    public void submitIssue(String title, String category, String priority, String location,
                            String description, String initials) {
        String uID = authRepository.getCurrentUser().getUid();

        Map<String, Object> issue = new HashMap<>();
        issue.put("title", title);
        issue.put("category", category);
        issue.put("priority", priority);
        issue.put("location", location);
        issue.put("description", description);
        issue.put("initials", initials);
        issue.put("status", "SUBMITTED");
        issue.put("creatorUid", uID);
        issue.put("timestamp", System.currentTimeMillis());
        issue.put("lastUpdated", System.currentTimeMillis());

        weatherRepository.getCurrentWeather(new WeatherCallback() {
            @Override
            public void onSuccess(WeatherData weatherData) {
                issue.put("weatherSummary", weatherData.getSummary());
                issue.put("temperature", weatherData.getTemperature());
                issue.put("weatherCondition", weatherData.getCondition());

                db.child("issues").push().setValue(issue);
            }

            @Override
            public void onFailure(String errorMessage) {
                issue.put("weatherSummary", errorMessage);
                issue.put("temperature", null);
                issue.put("weatherCondition", "Unavailable");

                db.child("issues").push().setValue(issue);
            }
        });
    }
}