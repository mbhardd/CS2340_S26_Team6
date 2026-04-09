package com.example.sprintproject.viewmodel;

import com.example.sprintproject.model.Issue;

import java.util.ArrayList;
import java.util.List;

public class OpenIssuesFilter implements IssueFilterStrategy {
    @Override
    public List<Issue> apply(List<Issue> issues) {
        List<Issue> filtered = new ArrayList<>();
        for (Issue issue : issues) {
            if (issue.getStatus().equalsIgnoreCase("SUBMITTED") ||
                    issue.getStatus().equalsIgnoreCase("IN_REVIEW") ||
                    issue.getStatus().equalsIgnoreCase("IN_PROGRESS")) {
                filtered.add(issue);
            }
            if ("In Progress".equalsIgnoreCase(issue.getStatus())) {
                filtered.add(issue);
            }
        }
        return filtered;
    }
}