package com.example.sprintproject.viewmodel;

import androidx.lifecycle.ViewModel;

import com.example.sprintproject.model.AuthRepository;
import com.example.sprintproject.model.Issue;
import com.example.sprintproject.model.IssueDetail;
import com.example.sprintproject.model.WeatherCallback;
import com.example.sprintproject.model.WeatherData;
import com.example.sprintproject.model.WeatherRepository;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;



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
        IssueDetail detail = new IssueDetail(title, category, priority, initials, location,
                description);
        Long time = System.currentTimeMillis();
        Issue issue = new Issue(detail, uID, time);

        weatherRepository.getCurrentWeather(new WeatherCallback() {
            @Override
            public void onSuccess(WeatherData weatherData) {
                issue.setWeatherSummary(weatherData.getSummary());
                issue.setTemperature(weatherData.getTemperature());
                issue.setWeatherCondition(weatherData.getCondition());

                db.child("issues").push().setValue(issue);
            }

            @Override
            public void onFailure(String errorMessage) {
                issue.setWeatherSummary("Unavailable");
                issue.setTemperature(null);
                issue.setWeatherCondition("Unavailable");

                db.child("issues").push().setValue(issue);
            }
        });
    }
}