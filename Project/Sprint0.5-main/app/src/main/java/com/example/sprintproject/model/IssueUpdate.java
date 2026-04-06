package com.example.sprintproject.model;

public class IssueUpdate {
    private String id;
    private String authorEmail;
    private long timestamp;
    private String updateType;
    private String content;
    private String oldStatus;
    private String newStatus;

    public IssueUpdate() {
    }

    public IssueUpdate(String authorEmail, long timestamp, String updateType,
                       String content, String oldStatus, String newStatus) {
        this.authorEmail = authorEmail;
        this.timestamp = timestamp;
        this.updateType = updateType;
        this.content = content;
        this.oldStatus = oldStatus;
        this.newStatus = newStatus;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAuthorEmail() {
        return authorEmail;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public String getUpdateType() {
        return updateType;
    }

    public String getContent() {
        return content;
    }

    public String getOldStatus() {
        return oldStatus;
    }

    public String getNewStatus() {
        return newStatus;
    }
}
