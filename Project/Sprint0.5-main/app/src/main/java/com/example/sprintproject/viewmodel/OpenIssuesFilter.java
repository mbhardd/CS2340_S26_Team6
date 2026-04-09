package com.example.sprintproject.viewmodel;

import com.example.sprintproject.model.Issue;

import java.util.ArrayList;
import java.util.List;

public class OpenIssuesFilter implements IssueFilterStrategy {
    @Override
    public List<Issue> apply(List<Issue> issues) {
        List<Issue> filtered = new ArrayList<>();

        for (Issue issue : issues) {
            String status = issue.getStatus();

            if (status != null && !status.equalsIgnoreCase("CLOSED")) {
                filtered.add(issue);
            }
        }

        return filtered;
    }
}