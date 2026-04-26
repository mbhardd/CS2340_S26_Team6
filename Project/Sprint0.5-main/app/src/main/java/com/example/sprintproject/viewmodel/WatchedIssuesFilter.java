package com.example.sprintproject.viewmodel;

import com.example.sprintproject.model.Issue;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class WatchedIssuesFilter implements IssueFilterStrategy {

    private final Set<String> watchedIds;

    public WatchedIssuesFilter(Set<String> watchedIds) {
        this.watchedIds = watchedIds;
    }

    @Override
    public List<Issue> apply(List<Issue> issues) {
        List<Issue> result = new ArrayList<>();
        for (Issue issue : issues) {
            if (issue.getId() != null && watchedIds.contains(issue.getId())) {
                result.add(issue);
            }
        }
        return result;
    }
}