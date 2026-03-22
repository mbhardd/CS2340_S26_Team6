package com.example.sprintproject;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import com.example.sprintproject.model.Issue;
import com.example.sprintproject.viewmodel.IssueFeedViewModel;
import com.example.sprintproject.viewmodel.PriorityFilter;


import java.util.Arrays;
import java.util.List;

public class Ria_Unit_Tests {
    private IssueFeedViewModel viewModel;

    @Before
    public void setUp() {
        viewModel = new IssueFeedViewModel(true);
    }

    @Test
    public void testFormatPriority_sameInputTwice_sameResult() {
        String first = viewModel.formatPriorityCheck("Low");
        String second = viewModel.formatPriorityCheck("Low");

        assertEquals(first, second);
    }

    @Test
    public void apply_multipleIssues_filtersCorrectOnes() {
        List<Issue> issues = Arrays.asList(
                new Issue("title", "category", "High", "RS", "CT", "description", "uID", "status",
                        (long) 0.001),
                new Issue("title", "category", "Medium", "RS", "CT", "description", "uID", "status",
                        (long) 0.001),
                new Issue("title", "category", "High", "AP", "MI", "description", "uID", "status",
                        (long) 0.001)
        );

        PriorityFilter filter = new PriorityFilter("High");
        List<Issue> result = filter.apply(issues);

        assertEquals(2, result.size());
    }

}
