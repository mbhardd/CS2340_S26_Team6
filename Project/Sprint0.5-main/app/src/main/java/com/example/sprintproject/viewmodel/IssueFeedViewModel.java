package com.example.sprintproject.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.example.sprintproject.model.Issue;
import com.example.sprintproject.model.IssueRepository;
import com.example.sprintproject.model.IssueStatus;
import com.example.sprintproject.model.IssueUpdate;
import com.example.sprintproject.model.UpdateType;
import com.example.sprintproject.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

public class IssueFeedViewModel extends ViewModel {

    private IssueRepository repository;
    private MutableLiveData<List<Issue>> issues = new MutableLiveData<>();
    private List<Issue> fullIssueList = new ArrayList<>();
    private IssueFilterStrategy currentFilter = new AllIssuesFilter();
    private IssueSortStrategy currentSort = new RecentStrategy();

    // normal constructor thats used by app
    public IssueFeedViewModel() {
        repository = IssueRepository.getInstance();
        repository.getIssues().observeForever(new Observer<List<Issue>>() {
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


    public void addComment(Issue issue, String comment) {
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        String email = "anonymous";

        if (firebaseUser != null && firebaseUser.getEmail() != null) {
            email = firebaseUser.getEmail();
        }
        IssueUpdate update = new IssueUpdate(
                email,
                System.currentTimeMillis(),
                UpdateType.COMMENT.name(),
                comment,
                null,
                null
        );

        repository.addUpdateToIssue(issue.getId(), update);

        repository.updateLastUpdated(issue.getId(), System.currentTimeMillis());
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


    public boolean updateIssueStatus(Issue issue, IssueStatus newStatus, User user) {

        if (!user.isStaff()) {
            return false;
        }

        IssueStatus current = issue.getStatusEnum();

        if (!current.canTransitionTo(newStatus)) {
            return false;
        }

        IssueUpdate update = new IssueUpdate(
                user.getEmail(),
                System.currentTimeMillis(),
                UpdateType.STATUS_CHANGE.name(),
                "Status changed to " + newStatus,
                current.name(),
                newStatus.name()
        );

        repository.updateStatusWithHistory(
                issue.getId(),
                newStatus.name(),
                update
        );

        return true;
    }

    public LiveData<List<IssueUpdate>> getUpdatesForIssue(String issueId) {
        return repository.getUpdatesForIssue(issueId);
    }
}