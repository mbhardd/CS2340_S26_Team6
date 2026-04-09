package com.example.sprintproject.viewmodel;

import com.example.sprintproject.model.Issue;

import java.util.List;

public class MostRecentUpdateStrategy implements IssueSortStrategy {
    @Override
    public List<Issue> apply(List<Issue> issues) {
        issues.sort((a, b) -> Long.compare(
                b.getLastUpdated() != null ? b.getLastUpdated() : 0,
                a.getLastUpdated() != null ? a.getLastUpdated() : 0
        ));
        return issues;
    }
}