package com.example.sprintproject.viewmodel;

import com.example.sprintproject.model.Issue;

import java.util.ArrayList;
import java.util.List;

public class PriorityStrategy implements IssueSortStrategy {

    private int getPriorityValue(String priority) {
        if (priority == null) {
            return 0;
        }
        switch (priority.trim().toLowerCase()) {
        case "high": return 3;
        case "medium": return 2;
        case "low": return 1;
        default: return 0;
        }
    }

    @Override
    public List<Issue> apply(List<Issue> issues) {
        List<Issue> sorted = new ArrayList<>(issues);

        sorted.sort((a, b) ->
                Integer.compare(
                        getPriorityValue(b.getPriority()),
                        getPriorityValue(a.getPriority())
                )
        );

        return sorted;
    }
}
