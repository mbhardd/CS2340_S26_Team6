package com.example.sprintproject.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.sprintproject.model.Issue;
import com.example.sprintproject.model.IssueRepository;

import java.util.List;

public class IssueFeedViewModel extends ViewModel {
    private final IssueRepository repository = IssueRepository.getInstance();
    private final LiveData<List<Issue>> issues = repository.getIssues();

    public LiveData<List<Issue>> getIssues() {
        return issues;
    }
}
