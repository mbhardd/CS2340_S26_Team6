package com.example.sprintproject.model;

public class UpdateResult {
    public final boolean success;
    public final String message;
    public final IssueUpdate update;
    public final IssueStatus newStatus;

    public UpdateResult(boolean success, String message, IssueUpdate update, IssueStatus newStatus) {
        this.success = success;
        this.message = message;
        this.update = update;
        this.newStatus = newStatus;
    }
}
