package com.example.sprintproject.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import com.example.sprintproject.model.AuthRepository;
import com.example.sprintproject.model.Issue;
import com.example.sprintproject.model.IssueRepository;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class AnalyticsViewModel extends ViewModel {
    private final IssueRepository repository;
    private AuthRepository authRepository;

    private final MediatorLiveData<Map<String, Integer>> categoryCounts = new MediatorLiveData<>();
    private final MediatorLiveData<Map<String, Integer>> statusCounts = new MediatorLiveData<>();
    private final MediatorLiveData<Map<String, Integer>> issuesOverTime = new MediatorLiveData<>();

    public AnalyticsViewModel() {
        repository = IssueRepository.getInstance();
        authRepository = AuthRepository.getInstance();

        LiveData<List<Issue>> issuesLiveData = repository.getIssues(
                authRepository.getCurrentUser().getUid());

        categoryCounts.setValue(new LinkedHashMap<>());
        statusCounts.setValue(new LinkedHashMap<>());
        issuesOverTime.setValue(new LinkedHashMap<>());

        categoryCounts.addSource(issuesLiveData, this::updateAnalyticsData);
        statusCounts.addSource(issuesLiveData, this::updateAnalyticsData);
        issuesOverTime.addSource(issuesLiveData, this::updateAnalyticsData);
    }

    private void updateAnalyticsData(List<Issue> issues) {
        categoryCounts.setValue(ChartDataHelper.buildCategoryCounts(issues));
        statusCounts.setValue(ChartDataHelper.buildStatusCounts(issues));
        issuesOverTime.setValue(ChartDataHelper.buildIssuesOverTimeCounts(issues));
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
}
