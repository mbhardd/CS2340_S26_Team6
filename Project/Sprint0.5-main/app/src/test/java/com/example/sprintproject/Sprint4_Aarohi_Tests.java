package com.example.sprintproject;

import org.junit.Test;

import static org.junit.Assert.*;


import com.example.sprintproject.model.Issue;
import com.example.sprintproject.viewmodel.WatchedIssuesFilter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


//Unit Test Requirement (Implementation of 2 Unit Tests is Needed)
public class Sprint4_Aarohi_Tests {


    @Test
    public void watchedIssuesFilter_liveSetMutation_reflectsUnwatch() {
        Issue watched = new Issue();
        watched.setID("issue_1");

        Issue unwatched = new Issue();
        unwatched.setID("issue_2");

        List<Issue> allIssues = new ArrayList<>();
        allIssues.add(watched);
        allIssues.add(unwatched);

        Set<String> watchedIds = new HashSet<>();
        watchedIds.add("issue_1");
        watchedIds.add("issue_2");

        WatchedIssuesFilter filter = new WatchedIssuesFilter(watchedIds);

        assertEquals(2, filter.apply(new ArrayList<>(allIssues)).size());

        watchedIds.clear();
        watchedIds.add("issue_1");

        List<Issue> result = filter.apply(new ArrayList<>(allIssues));
        assertEquals(1, result.size());
        assertEquals("issue_1", result.get(0).getId());
    }


    @Test
    public void watchedIssuesFilter_emptyWatchedSet_returnsEmptyList() {
        Issue issue1 = new Issue();
        issue1.setID("issue_1");

        Issue issue2 = new Issue();
        issue2.setID("issue_2");

        List<Issue> allIssues = new ArrayList<>();
        allIssues.add(issue1);
        allIssues.add(issue2);

        Set<String> watchedIds = new HashSet<>();
        WatchedIssuesFilter filter = new WatchedIssuesFilter(watchedIds);

        List<Issue> result = filter.apply(new ArrayList<>(allIssues));
        assertTrue(result.isEmpty());
    }


}
