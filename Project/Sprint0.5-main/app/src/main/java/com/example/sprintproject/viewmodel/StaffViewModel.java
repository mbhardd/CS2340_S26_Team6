package com.example.sprintproject.viewmodel;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.example.sprintproject.model.Issue;
import com.example.sprintproject.model.IssueRepository;
import com.example.sprintproject.model.IssueStatus;
import com.example.sprintproject.model.IssueUpdate;
import com.example.sprintproject.model.UpdateResult;
import com.example.sprintproject.model.UpdateType;
import com.example.sprintproject.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class StaffViewModel extends ViewModel {
    private final IssueRepository repository;
    private MutableLiveData<List<Issue>> issues = new MutableLiveData<>();
    private List<Issue> fullIssueList = new ArrayList<>();
    private final MutableLiveData<String> successMessage = new MutableLiveData<>();
    private final MutableLiveData<String> errorMessage = new MutableLiveData<>();

    //test constructor
    public StaffViewModel(IssueRepository repository) {
        this.repository = repository;
    }
    public StaffViewModel() {
        repository = IssueRepository.getInstance();
        repository.getIssues().observeForever(new Observer<List<Issue>>() {
            @Override
            public void onChanged(List<Issue> issueList) {
                fullIssueList = issueList;
                issues.setValue(issueList);
            }
        });
    }


    public LiveData<List<Issue>> getIssues() {
        return issues;
    }

    public LiveData<String> getSuccessMessage() {
        return successMessage;
    }

    public void clearSuccessMessage() {
        successMessage.setValue(null);
    }

    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }
    public void clearErrorMessage() {
        errorMessage.setValue(null);
    }

    public LiveData<List<IssueUpdate>> getUpdatesForIssue(String issueId) {
        return repository.getUpdatesForIssue(issueId);
    }

    public void changeStatus(String issueId, User user,
                             IssueStatus oldStatus,
                             IssueStatus newStatus) {

        IssueUpdateStrategy strategy =
                new StatusChangeStrategy(oldStatus, newStatus);

        executeStrategy(strategy, issueId, user, null);

    }

    public void addStaffNote(String issueId, User user, String content) {

        IssueUpdateStrategy strategy =
                new StaffUpdateStrategy();

        executeStrategy(strategy, issueId, user, content);

    }

    public void executeStrategy(IssueUpdateStrategy strategy,
                                String issueId,
                                User user,
                                String content) {

        UpdateResult result = strategy.execute(issueId, user, content);

        if (!result.success) {
            errorMessage.setValue(result.message);
            return;
        }
        if (result.newStatus != null) {
            repository.updateStatus(issueId, result.newStatus.name());
        }
        repository.addUpdateToIssue(issueId, result.update);
        repository.updateLastUpdated(issueId, System.currentTimeMillis());
        successMessage.setValue(result.message);
    }

    public boolean isValidNextStatus(IssueStatus current, IssueStatus next) {
        if (current == null || next == null) {
            return false;
        }

        switch (current) {
        case SUBMITTED:
            return next == IssueStatus.IN_REVIEW;
        case IN_REVIEW:
            return next == IssueStatus.IN_PROGRESS;
        case IN_PROGRESS:
            return next == IssueStatus.RESOLVED;
        case RESOLVED:
            return next == IssueStatus.CLOSED;
        case CLOSED:
            return false;
        default:
            return false;
        }
    }

    public String formatStatus(IssueStatus status) {
        switch (status) {
        case SUBMITTED:
            return "Submitted";
        case IN_REVIEW:
            return "In Review";
        case IN_PROGRESS:
            return "In Progress";
        case RESOLVED:
            return "Resolved";
        case CLOSED:
            return "Closed";
        default:
            return status.name();
        }
    }

    public void changeAssignedStaff(String issueId, String newStaff) {
        repository.updateAssignedStaff(issueId, newStaff);
    }


    public LiveData<List<User>> getStaffUsers() {
        return repository.getStaffUsers();
    }
}
