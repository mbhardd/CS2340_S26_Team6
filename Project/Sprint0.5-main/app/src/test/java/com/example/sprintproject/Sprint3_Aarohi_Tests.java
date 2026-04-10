package com.example.sprintproject;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;


import com.example.sprintproject.model.Issue;
import com.example.sprintproject.model.IssueStatus;
import com.example.sprintproject.viewmodel.PriorityStrategy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


//Unit Test Requirement (Implementation of 2 Unit Tests is Needed)
public class Sprint3_Aarohi_Tests {

    @Before
    public void setUp() {
    }

    @Test
    public void invalidTransitionTest() {
        IssueStatus current = IssueStatus.SUBMITTED;
        boolean result = current.canTransitionTo(IssueStatus.CLOSED);
        assertFalse(result);
    }


    @Test
    public void testPrioritySorting() {
        List<Issue> issues = new ArrayList<>();
        Issue low = new Issue();
        low.setPriority("Low");
        Issue high = new Issue();
        high.setPriority("High");
        Issue medium = new Issue();
        medium.setPriority("Medium");
        issues.add(low);
        issues.add(high);
        issues.add(medium);
        PriorityStrategy strategy = new PriorityStrategy();
        List<Issue> sorted = strategy.apply(issues);
        assertEquals("High", sorted.get(0).getPriority());
        assertEquals("Medium", sorted.get(1).getPriority());
        assertEquals("Low", sorted.get(2).getPriority());
    }


}
