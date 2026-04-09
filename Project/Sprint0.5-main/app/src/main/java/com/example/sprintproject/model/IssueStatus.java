package com.example.sprintproject.model;

public enum IssueStatus {
    SUBMITTED,
    IN_REVIEW,
    IN_PROGRESS,
    RESOLVED,
    CLOSED;

    public boolean canTransitionTo(IssueStatus next) {
        switch (this) {
            case SUBMITTED:
                return next == IN_REVIEW;
            case IN_REVIEW:
                return next == IN_PROGRESS;
            case IN_PROGRESS:
                return next == RESOLVED;
            case RESOLVED:
                return next == CLOSED;
            default:
                return false;
        }
    }
}