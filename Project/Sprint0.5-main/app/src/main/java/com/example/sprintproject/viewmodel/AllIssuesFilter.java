package com.example.sprintproject.viewmodel;

import com.example.sprintproject.model.Issue;

import java.util.List;

public class AllIssuesFilter implements IssueFilterStrategy {
    @Override
    public List<Issue> apply(List<Issue> issues) {
        return issues;
    }
}
