package com.example.sprintproject.viewmodel;

import com.example.sprintproject.model.IssueUpdate;
import com.example.sprintproject.model.UpdateResult;
import com.example.sprintproject.model.User;

public interface IssueUpdateStrategy {
    UpdateResult execute(String issueId, User user, String content);
}
