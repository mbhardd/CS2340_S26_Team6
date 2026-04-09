package com.example.sprintproject.viewmodel;

import com.example.sprintproject.model.Issue;

import java.util.ArrayList;
import java.util.List;

public class RecentStrategy implements IssueSortStrategy {
    @Override
    public List<Issue> apply(List<Issue> issues) {
        List<Issue> sorted = new ArrayList<>(issues);
        sorted.sort((a, b) -> Long.compare(
                b.getTimestamp() != null ? b.getTimestamp() : 0,
                a.getTimestamp() != null ? a.getTimestamp() : 0
        ));
        return sorted;
    }
}
