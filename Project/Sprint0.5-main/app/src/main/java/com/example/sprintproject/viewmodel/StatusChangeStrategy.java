package com.example.sprintproject.viewmodel;

import com.example.sprintproject.model.IssueStatus;
import com.example.sprintproject.model.IssueUpdate;
import com.example.sprintproject.model.UpdateResult;
import com.example.sprintproject.model.UpdateType;
import com.example.sprintproject.model.User;

public class StatusChangeStrategy implements IssueUpdateStrategy {
    private IssueStatus oldStatus;
    private IssueStatus newStatus;

    public StatusChangeStrategy(IssueStatus oldStatus, IssueStatus newStatus) {
        this.oldStatus = oldStatus;
        this.newStatus = newStatus;

    }
    @Override
    public UpdateResult execute(String issueId, User user, String content) {
        if (!isValidNextStatus(oldStatus, newStatus)) {
            return new UpdateResult(false, "Invalid status transition", null, null);
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
        return new UpdateResult(true, "Status updated", update, newStatus);
    }

    public String formatStatus(IssueStatus status) {
        switch (status) {
        case SUBMITTED: return "Submitted";
        case IN_REVIEW: return "In Review";
        case IN_PROGRESS: return "In Progress";
        case RESOLVED: return "Resolved";
        case CLOSED: return "Closed";
        default: return status.name();
        }
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
}
