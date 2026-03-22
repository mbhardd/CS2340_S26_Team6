package com.example.sprintproject;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import com.example.sprintproject.model.Issue;
import com.example.sprintproject.viewmodel.AllIssuesFilter;
import com.example.sprintproject.viewmodel.IssueFeedViewModel;
import com.example.sprintproject.viewmodel.IssueFilterStrategy;

import java.util.Arrays;
import java.util.List;


//Unit Test Requirement (Implementation of 2 Unit Tests is Needed)
public class Aarohi_Unit_Tests {
    private IssueFeedViewModel viewModel;

    @Before
    public void setUp() {
        viewModel = new IssueFeedViewModel(true);
    }

    @Test
    public void testFormatPriorityCheckWithNull() {
        // Test with null priority
        String result = viewModel.formatPriorityCheck(null);
        assertEquals("", result);
    }


    @Test
    public void testFormatPriority_high_returnsRedIndicator() {
        String lowPriority = viewModel.formatPriorityCheck("Low");
        assertEquals("🟢 Low", lowPriority);
    }


}
