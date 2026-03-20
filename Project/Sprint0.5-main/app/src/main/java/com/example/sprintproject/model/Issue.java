package com.example.sprintproject.model;

public class Issue {
    private String id;
    private String title;
    private String category;
    private String priority;
    private String initials;
    private String location;
    private String description;
    private String creatorUid;
    private String status;
    private Long timestamp;

    public Issue() {

    }

    public Issue(String title, String category, String priority, String initials,
                 String location, String description,
                 String creatorUid, String status, Long timestamp) {
        this.title = title;
        this.category = category;
        this.priority = priority;
        this.initials = initials;
        this.location = location;
        this.description = description;
        this.creatorUid = creatorUid;
        this.status = status;
        this.timestamp = timestamp;
    }

    public String getId() {
        return id;
    }

    public void setID(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public String getCategory() {
        return category;
    }

    public String getPriority() {
        return priority;
    }

    public String getInitials() {
        return initials;
    }

    public String getLocation() {
        return location;
    }

    public String getDescription() {
        return description;
    }

    public String getCreatorUid() {
        return creatorUid;
    }

    public String getStatus() {
        return status;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public void setInitials(String initials) {
        this.initials = initials;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCreatorUid(String creatorUid) {
        this.creatorUid = creatorUid;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }
}