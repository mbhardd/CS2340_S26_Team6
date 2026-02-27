package com.example.sprintproject.model;

public class User {
    private String uid;
    private String email;
    private boolean isStaff;

    public User() { }

    public User(String uid, String email, boolean isStaff) {
        this.uid = uid;
        this.email = email;
        this.isStaff = isStaff;
    }

    public String getUid() {
        return uid;
    }

    public String getEmail() {
        return email;
    }

    public boolean isStaff() {
        return isStaff;
    }
}
