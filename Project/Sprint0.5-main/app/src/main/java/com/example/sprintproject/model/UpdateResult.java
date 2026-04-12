package com.example.sprintproject.model;

public class UpdateResult {
    private final boolean success;
    private final String message;
    private final IssueUpdate update;
    private final IssueStatus newStatus;

    public UpdateResult(boolean success, String message, IssueUpdate update,
                        IssueStatus newStatus) {
        this.success = success;
        this.message = message;
        this.update = update;
        this.newStatus = newStatus;
    }

    public String getMessage() {
        return message;
    }

    public IssueUpdate getUpdate() {
        return update;
    }

    public boolean isSuccess() {
        return success;
    }

    public IssueStatus getNewStatus() {
        return newStatus;
    }
}
