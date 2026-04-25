package com.example.sprintproject;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.example.sprintproject.model.Issue;
import com.example.sprintproject.viewmodel.UpvoteSortStrategy;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class Sprint4_Ria_Tests {
    @Before
    public void setUp() {

    }

    @Test
    public void testUpvoteSortStrategy_descendingOrder() {
        Issue low = new Issue();
        low.setUpvoteCount(1);

        Issue mid = new Issue();
        mid.setUpvoteCount(5);

        Issue high = new Issue();
        high.setUpvoteCount(10);

        List<Issue> issues = Arrays.asList(low, high, mid);
        List<Issue> sorted = new UpvoteSortStrategy().apply(issues);

        assertEquals(10, sorted.get(0).getUpvoteCount());
        assertEquals(5, sorted.get(1).getUpvoteCount());
        assertEquals(1, sorted.get(2).getUpvoteCount());
    }

    @Test
    public void testToggleUpvote_upvotesAndUnupvotes() {
        Issue issue = new Issue();
        issue.setUpvoteCount(3);
        issue.setUpvoted(false);

        // First toggle — should upvote
        issue.toggleUpvote();
        assertTrue(issue.isUpvoted());
        assertEquals(4, issue.getUpvoteCount());

        // Second toggle — should remove upvote
        issue.toggleUpvote();
        assertFalse(issue.isUpvoted());
        assertEquals(3, issue.getUpvoteCount());
    }

}
