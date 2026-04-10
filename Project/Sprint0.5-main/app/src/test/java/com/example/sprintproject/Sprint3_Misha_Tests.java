package com.example.sprintproject;
import org.junit.Test;
import org.junit.Before;

import com.example.sprintproject.model.IssueStatus;
import com.example.sprintproject.viewmodel.IssueFeedViewModel;
import com.example.sprintproject.viewmodel.StaffViewModel;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Sprint3_Misha_Tests {

    private StaffViewModel viewModel;

    @Before
    public void setUp() {
        viewModel = new StaffViewModel(null);
    }

    @Test
    public void testValidTransition_SubmittedToInReview() {
        boolean result = viewModel.isValidNextStatus(
                IssueStatus.SUBMITTED,
                IssueStatus.IN_REVIEW
        );

        assertTrue(result);
    }

    @Test
    public void testInvalidTransition_SubmittedToClosed() {
        boolean result = viewModel.isValidNextStatus(
                IssueStatus.SUBMITTED,
                IssueStatus.CLOSED
        );

        assertFalse(result);
    }
}
