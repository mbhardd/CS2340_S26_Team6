package com.example.sprintproject.viewmodel;

import com.example.sprintproject.model.Issue;

import java.util.List;

public interface IssueSortStrategy {
    List<Issue> apply(List<Issue> issues);
}
