package com.example.sprintproject.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.example.sprintproject.model.Issue;
import com.example.sprintproject.model.IssueRepository;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

public class IssueFeedViewModel extends ViewModel {

    private IssueRepository repository;
    private MutableLiveData<List<Issue>> issues = new MutableLiveData<>();
    private List<Issue> fullIssueList = new ArrayList<>();
    private IssueFilterStrategy currentFilter = new AllIssuesFilter();

    // normal constructor thats used by app
    public IssueFeedViewModel() {
        repository = IssueRepository.getInstance();
        repository.getIssues().observeForever(new Observer<List<Issue>>() {
            @Override
            public void onChanged(List<Issue> issueList) {
                fullIssueList = issueList;
                applyFilter();
            }
        });
    }

    // test constructor preventing Firebase from running
    public IssueFeedViewModel(boolean isTest) {
    }

    public LiveData<List<Issue>> getIssues() {
        return issues;
    }

    public void setFilter(IssueFilterStrategy filter) {
        this.currentFilter = filter;
        applyFilter();
    }

    private void applyFilter() {
        List<Issue> filteredIssues = currentFilter.apply(fullIssueList);
        issues.setValue(filteredIssues);
    }


    public String formatPriorityCheck(String priority) {
        if (priority == null) {
            return "";
        }

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