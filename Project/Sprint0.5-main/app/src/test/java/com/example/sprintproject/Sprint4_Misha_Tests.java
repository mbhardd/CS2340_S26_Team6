package com.example.sprintproject;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import androidx.lifecycle.MutableLiveData;

import com.example.sprintproject.model.Issue;
import com.example.sprintproject.model.IssueRepository;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class Sprint4_Misha_Tests {

    @Before
    public void setUp() {

    }

    @Test
    public void testToggleUpvote_changesStateCorrectly() {
        Issue issue = new Issue();
        issue.setUpvoteCount(0);
        issue.setUpvoted(false);

       // issue.toggleUpvote();

        assertTrue(issue.isUpvoted());
        assertEquals(1, issue.getUpvoteCount());

       // issue.toggleUpvote();

        assertFalse(issue.isUpvoted());
        assertEquals(0, issue.getUpvoteCount());
    }

    @Test
    public void testToggleWatch_changesState() {
        Issue issue = new Issue();
        issue.setWatching(false);

        issue.toggleWatch();
        assertTrue(issue.isWatching());

        issue.toggleWatch();
        assertFalse(issue.isWatching());
    }



}
