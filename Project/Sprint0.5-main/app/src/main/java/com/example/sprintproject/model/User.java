package com.example.sprintproject.model;

import java.util.HashMap;
import java.util.Map;

public class User {
    //Basic user class with attributes of email and isStaff
    //Password details are secure in fb only
    //Basic getters and setters for the email and isStaff attribute
    private String email;
    private boolean isStaff;

    private Map<String, Boolean> upvotedIssues;

    public User() { }

    public User(String email, boolean isStaff) {
        this.email = email;
        this.isStaff = isStaff;
        upvotedIssues = new HashMap<>();
    }

    public String getEmail() {
        return email;
    }

    public boolean isStaff() {
        return isStaff;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setStaff(boolean staff) {
        isStaff = staff;
    }

    public Map<String, Boolean> getUpvotedIssues() {
        return upvotedIssues;
    }

    public void setUpvotedIssues(Map<String, Boolean> map) {
        upvotedIssues = map;
    }
}