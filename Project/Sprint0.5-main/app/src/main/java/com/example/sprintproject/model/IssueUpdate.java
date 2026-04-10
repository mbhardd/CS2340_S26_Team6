package com.example.sprintproject.model;
import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class IssueUpdate {
    private String id;
    private String authorEmail;
    private long timestamp;
    private String type;
    private String content;
    private String oldStatus;
    private String newStatus;

    public IssueUpdate() {
    }

    public IssueUpdate(String authorEmail, long timestamp, String type,
                       String content, String oldStatus, String newStatus) {
        this.authorEmail = authorEmail;
        this.timestamp = timestamp;
        this.type = type;
        this.content = content;
        this.oldStatus = oldStatus;
        this.newStatus = newStatus;
    }

    public String getId() {
        return id;
    }

    public String getType() {
        return type;
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
