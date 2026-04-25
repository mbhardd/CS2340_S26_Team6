package com.example.sprintproject.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.example.sprintproject.model.AuthRepository;
import com.example.sprintproject.model.Issue;
import com.example.sprintproject.model.IssueRepository;
import com.example.sprintproject.model.IssueUpdate;
import com.example.sprintproject.model.UpdateResult;
import com.example.sprintproject.model.User;

import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

public class IssueFeedViewModel extends ViewModel {

    private IssueRepository repository;

    private AuthRepository authRepository;
    private MutableLiveData<List<Issue>> issues = new MutableLiveData<>();
    private List<Issue> fullIssueList = new ArrayList<>();
    private IssueFilterStrategy currentFilter = new AllIssuesFilter();
    private IssueSortStrategy currentSort = new RecentStrategy();

    // normal constructor thats used by app
    public IssueFeedViewModel() {
        authRepository = AuthRepository.getInstance();
        repository = IssueRepository.getInstance();
        repository.getIssues(authRepository.getCurrentUser().getUid())
                .observeForever(new Observer<List<Issue>>() {
                    @Override
                    public void onChanged(List<Issue> issueList) {
                        fullIssueList = issueList;
                        applyFilterAndSort();
                        }
                    });
    }

    // test constructor preventing Firebase from running
    public IssueFeedViewModel(boolean isTest) {
    }

    public LiveData<List<Issue>> getIssues() {
        return issues;
    }

    public void toggleUpvote(String issueId, Runnable onComplete) {
        repository.toggleUpvote(issueId, authRepository.getCurrentUser().getUid(), onComplete);
    }

    public void setFilter(IssueFilterStrategy filter) {
        this.currentFilter = filter;
        applyFilterAndSort();
    }

    private void applyFilterAndSort() {
        List<Issue> filtered = currentFilter.apply(new ArrayList<>(fullIssueList));

        List<Issue> sorted = new ArrayList<>(filtered);

        sorted = currentSort.apply(sorted);

        System.out.println("Sorted list:");
        for (Issue i : sorted) {
            System.out.println(i.getPriority());
        }

        issues.setValue(sorted);
    }


    public void addComment(String issueId, User user, String content) {
        IssueUpdateStrategy strategy =
                    new CommentStrategy();

        executeStrategy(strategy, issueId, user, content);

    }

    public void executeStrategy(IssueUpdateStrategy strategy, String issueId, User user,
                                String content) {
        UpdateResult result = strategy.execute(issueId, user, content);
        repository.addUpdateToIssue(issueId, result.getUpdate());
        repository.updateLastUpdated(issueId, System.currentTimeMillis());
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

    public void setSort(IssueSortStrategy sort) {
        this.currentSort = sort;
        applyFilterAndSort();
    }

    public LiveData<List<IssueUpdate>> getUpdatesForIssue(String issueId) {
        return repository.getUpdatesForIssue(issueId);
    }
}