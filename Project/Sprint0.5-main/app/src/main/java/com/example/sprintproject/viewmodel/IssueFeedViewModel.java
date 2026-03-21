package com.example.sprintproject.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.sprintproject.model.Issue;
import com.example.sprintproject.model.IssueRepository;

import java.util.List;

public class IssueFeedViewModel extends ViewModel {

    private IssueRepository repository;
    private LiveData<List<Issue>> issues;

    // normal constructor thats used by app
    public IssueFeedViewModel() {
        repository = IssueRepository.getInstance();
        issues = repository.getIssues();
    }

    // test constructor preventing Firebase from running
    public IssueFeedViewModel(boolean isTest) {
    }

    public LiveData<List<Issue>> getIssues() {
        return issues;
    }

    public String formatPriorityCheck(String priority) {
        if (priority == null) return "";

        switch (priority) {
            case "High":
                return "🔴 High";
            case "Medium":
                return "🟡 Medium";
            case "Low":
                return "🟢 Low";
            default:
                return priority;
        }
    }

    public boolean showEmptyState(List<?> issues) {
        return issues == null || issues.isEmpty();
    }
}