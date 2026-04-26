package com.example.sprintproject.model;

public class IssueFactory {

    private IssueFactory() {}

    public static Issue createIssue(
            IssueDetail detail,
            String creatorUid,
            long timestamp
    ) {
        Issue issue = new Issue(detail, creatorUid, timestamp);

        // centralize defaults
        issue.setStatus("SUBMITTED");
        issue.setLastUpdated(timestamp);

        return issue;
    }

    public static void applyWeatherSuccess(Issue issue, WeatherData weatherData) {
        issue.setWeatherSummary(weatherData.getSummary());
        issue.setTemperature(weatherData.getTemperature());
        issue.setWeatherCondition(weatherData.getCondition());
    }

    public static void applyWeatherFailure(Issue issue) {
        issue.setWeatherSummary("Unavailable");
        issue.setTemperature(null);
        issue.setWeatherCondition("Unavailable");
    }
}