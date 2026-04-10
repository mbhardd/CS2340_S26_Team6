package com.example.sprintproject.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.example.sprintproject.model.Issue;
import com.example.sprintproject.model.IssueRepository;
import com.example.sprintproject.model.IssueStatus;
import com.example.sprintproject.model.IssueUpdate;
import com.example.sprintproject.model.UpdateType;
import com.example.sprintproject.model.User;

import java.util.ArrayList;
import java.util.List;

public class StaffViewModel extends ViewModel {
    private final IssueRepository repository;
    private MutableLiveData<List<Issue>> issues = new MutableLiveData<>();
    private List<Issue> fullIssueList = new ArrayList<>();
    private final MutableLiveData<String> successMessage = new MutableLiveData<>();
    private final MutableLiveData<String> errorMessage = new MutableLiveData<>();

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

    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }

    public LiveData<List<IssueUpdate>> getUpdatesForIssue(String issueId) {
        return repository.getUpdatesForIssue(issueId);
    }



    public void addComment(String issueId, User user, String content) {
        if (content == null || content.trim().isEmpty()) {
            errorMessage.setValue("Comment cannot be empty");
            return;
        }

        IssueUpdate update = new IssueUpdate(
                user.getEmail(),
                System.currentTimeMillis(),
                UpdateType.COMMENT.name(),
                content.trim(),
                null,
                null
        );

        repository.addIssueUpdate(issueId, update);
        successMessage.setValue("Comment added");
    }

    public void addStaffNote(String issueId, User user, String content) {
        if (!user.isStaff()) {
            errorMessage.setValue("Only staff can add staff notes");
            return;
        }

        if (content == null || content.trim().isEmpty()) {
            errorMessage.setValue("Staff note cannot be empty");
            return;
        }

        IssueUpdate update = new IssueUpdate(
                user.getEmail(),
                System.currentTimeMillis(),
                UpdateType.STAFF_NOTE.name(),
                content.trim(),
                null,
                null
        );

        repository.addIssueUpdate(issueId, update);
        successMessage.setValue("Staff note added");
    }

    public void changeStatus(String issueId, User user, IssueStatus oldStatus, IssueStatus newStatus) {
        if (!user.isStaff()) {
            errorMessage.setValue("Only staff can change issue status");
            return;
        }

        if (!isValidNextStatus(oldStatus, newStatus)) {
            errorMessage.setValue("Invalid status transition");
            return;
        }

        String message = "Status changed from " + formatStatus(oldStatus)
                + " to " + formatStatus(newStatus);

        IssueUpdate update = new IssueUpdate(
                user.getEmail(),
                System.currentTimeMillis(),
                UpdateType.STATUS_CHANGE.name(),
                message,
                oldStatus.name(),
                newStatus.name()
        );

        repository.updateStatusWithHistory(issueId, newStatus.name(), update);
        successMessage.setValue("Status updated");
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

    private String formatStatus(IssueStatus status) {
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

    public LiveData<List<User>> getStaffUsers() {
        return repository.getStaffUsers();
    }
}
