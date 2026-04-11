package com.example.sprintproject.model;

public class User {
    //Basic user class with attributes of email and isStaff
    //Password details are secure in fb only
    //Basic getters and setters for the email and isstaff attrribute
    private String email;
    private boolean isStaff;

    public User() { }

    public User(String email, boolean isStaff) {
        this.email = email;
        this.isStaff = isStaff;
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
}