package com.example.sprintproject.model;

public class IssueDetail {
    private final String title;
    private final String category;
    private final String priority;
    private final String initials;
    private final String location;
    private final String description;

    public IssueDetail(String title, String category, String priority,
                        String initials, String location, String description) {
        this.title = title;
        this.category = category;
        this.priority = priority;
        this.initials = initials;
        this.location = location;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public String getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }

    public String getInitials() {
        return initials;
    }

    public String getLocation() {
        return location;
    }

    public String getPriority() {
        return priority;
    }
}
