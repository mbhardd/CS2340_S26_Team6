package com.example.sprintproject.model;


import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
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

    private String assignedStaff;
    private Long timestamp;
    private Long lastUpdated;
    private String weatherSummary;
    private Double temperature;
    private String weatherCondition;
    private boolean isWatching = false;

    private boolean isUpvoted = false;
    private int upvoteCount = 0;


    public Issue() {

    }

    public Issue(IssueDetail detail, String uId, Long timestamp) {
        this.title = detail.getTitle();
        this.category = detail.getCategory();
        this.priority = detail.getPriority();
        this.initials = detail.getInitials();
        this.location = detail.getLocation();
        this.description = detail.getDescription();
        this.creatorUid = uId;
        this.status = "SUBMITTED";
        this.timestamp = timestamp;
        this.lastUpdated = timestamp;

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
    public Long getLastUpdated() {
        return lastUpdated;
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
    public void setLastUpdated(Long lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public String getAssignedStaff() {
        return assignedStaff;
    }
    public void setAssignedStaff(String assignedStaff) {
        this.assignedStaff = assignedStaff; }

    public String getWeatherSummary() {
        return weatherSummary;
    }

    public void setWeatherSummary(String weatherSummary) {
        this.weatherSummary = weatherSummary;
    }

    public Double getTemperature() {
        return temperature;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    public String getWeatherCondition() {
        return weatherCondition;
    }

    public void setWeatherCondition(String weatherCondition) {
        this.weatherCondition = weatherCondition;
    }
    public IssueStatus getStatusEnum() {
        try {
            return IssueStatus.valueOf(status.toUpperCase().replace(" ", "_"));
        } catch (Exception e) {
            return IssueStatus.SUBMITTED;
        }
    }

    public boolean isUpvoted() {
        return isUpvoted;
    }
    public void setUpvoted(boolean upvoted) {
        isUpvoted = upvoted;
    }

    public int getUpvoteCount() {
        return upvoteCount;
    }
    public void setUpvoteCount(int count) {
        upvoteCount = count;
    }
    public void toggleUpvote() {
        if (isUpvoted) {
            isUpvoted = false;
            upvoteCount--;
        } else {
            isUpvoted = true;
            upvoteCount++;
        }
    }

    public boolean isWatching() {
        return isWatching;
    }

    public void setWatching(boolean watching) {
        isWatching = watching;
    }

    public void toggleWatch() {
        isWatching = !isWatching;
    }

}