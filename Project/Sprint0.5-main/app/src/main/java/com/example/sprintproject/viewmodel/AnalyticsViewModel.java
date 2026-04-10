package com.example.sprintproject.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import com.example.sprintproject.model.Issue;
import com.example.sprintproject.model.IssueRepository;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;


public class AnalyticsViewModel extends ViewModel {
    private final IssueRepository repository;

    private final MediatorLiveData<Map<String, Integer>> categoryCounts = new MediatorLiveData<>();
    private final MediatorLiveData<Map<String, Integer>> statusCounts = new MediatorLiveData<>();
    private final MediatorLiveData<Map<String, Integer>> issuesOverTime = new MediatorLiveData<>();

    public AnalyticsViewModel() {
        repository = IssueRepository.getInstance();

        LiveData<List<Issue>> issuesLiveData = repository.getIssues();

        categoryCounts.setValue(new LinkedHashMap<>());
        statusCounts.setValue(new LinkedHashMap<>());
        issuesOverTime.setValue(new LinkedHashMap<>());

        categoryCounts.addSource(issuesLiveData, this::updateAnalyticsData);
        statusCounts.addSource(issuesLiveData, this::updateAnalyticsData);
        issuesOverTime.addSource(issuesLiveData, this::updateAnalyticsData);
    }

    private void updateAnalyticsData(List<Issue> issues) {
        categoryCounts.setValue(buildCategoryCounts(issues));
        statusCounts.setValue(buildStatusCounts(issues));
        issuesOverTime.setValue(buildIssuesOverTimeCounts(issues));
    }

    public LiveData<Map<String, Integer>> getCategoryCounts() {
        return categoryCounts;
    }

    public LiveData<Map<String, Integer>> getStatusCounts() {
        return statusCounts;
    }

    public LiveData<Map<String, Integer>> getIssuesOverTime() {
        return issuesOverTime;
    }

    private Map<String, Integer> buildCategoryCounts(List<Issue> issues){
        Map<String, Integer> counts = new LinkedHashMap<>();

        if (issues == null) {
            return counts;
        }

        for (Issue issue : issues) {
            String category = issue.getCategory();

            if (category == null || category.trim().isEmpty()) {
                category = "Unknown";
            }

            counts.put(category, counts.getOrDefault(category, 0) + 1);
        }

        return counts;
    }

    private Map<String, Integer> buildStatusCounts(List<Issue> issues) {
        Map<String, Integer> counts = new LinkedHashMap<>();

        if (issues == null) {
            return counts;
        }

        for (Issue issue : issues) {
            String status = issue.getStatus();

            if (status == null || status.trim().isEmpty()) {
                status = "Unknown";
            }

            counts.put(status, counts.getOrDefault(status, 0) + 1);
        }

        return counts;
    }

    private Map<String, Integer> buildIssuesOverTimeCounts(List<Issue> issues) {
        Map<String, Integer> sortedCounts = new TreeMap<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd", Locale.US);

        if (issues == null) {
            return new LinkedHashMap<>();
        }

        for (Issue issue : issues) {
            Long timestamp = issue.getTimestamp();

            if (timestamp == null) {
                continue;
            }

            String dayLabel = dateFormat.format(new Date(timestamp));
            sortedCounts.put(dayLabel, sortedCounts.getOrDefault(dayLabel, 0) + 1);
        }

        return new LinkedHashMap<>(sortedCounts);
    }

}
