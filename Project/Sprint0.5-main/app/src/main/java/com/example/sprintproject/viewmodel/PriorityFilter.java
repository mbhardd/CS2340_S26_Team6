package com.example.sprintproject.viewmodel;

import com.example.sprintproject.model.Issue;

import java.util.ArrayList;
import java.util.List;

public class PriorityFilter implements IssueFilterStrategy {

    private String priority;

    public PriorityFilter(String priority) {
        this.priority = priority;
    }

    @Override
    public List<Issue> apply(List<Issue> issues) {
        List<Issue> filtered = new ArrayList<>();
        for (Issue issue : issues) {
            if (issue.getPriority().equalsIgnoreCase(priority)) {
                filtered.add(issue);
            }
        }
        return filtered;
    }
}