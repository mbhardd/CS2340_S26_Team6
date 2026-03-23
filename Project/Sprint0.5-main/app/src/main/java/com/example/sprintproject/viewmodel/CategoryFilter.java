package com.example.sprintproject.viewmodel;

import com.example.sprintproject.model.Issue;

import java.util.ArrayList;
import java.util.List;

public class CategoryFilter implements IssueFilterStrategy {

    private String category;

    public CategoryFilter(String category) {
        this.category = category;
    }

    @Override
    public List<Issue> apply(List<Issue> issues) {
        List<Issue> filtered = new ArrayList<>();

        for (Issue issue : issues) {
            if (issue.getCategory() != null && issue.getCategory().equalsIgnoreCase(category)) {
                filtered.add(issue);
            }
        }

        return filtered;
    }
}