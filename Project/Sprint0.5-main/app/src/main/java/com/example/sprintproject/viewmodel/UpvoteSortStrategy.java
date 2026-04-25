package com.example.sprintproject.viewmodel;

import com.example.sprintproject.model.Issue;

import java.util.ArrayList;
import java.util.List;

public class UpvoteSortStrategy implements IssueSortStrategy {


    @Override
    public List<Issue> apply(List<Issue> issues) {
        List<Issue> sorted = new ArrayList<>(issues);
        sorted.sort((a, b) ->
                Integer.compare(
                        b.getUpvoteCount(),
                        a.getUpvoteCount()
                )
        );
        return sorted;
    }
}




