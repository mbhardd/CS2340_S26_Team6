package com.example.sprintproject;

import org.junit.Test;
import org.junit.Before;
import com.example.sprintproject.viewmodel.IssueFeedViewModel;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class Misha_Unit_Tests {
    private IssueFeedViewModel viewModel;

    @Before
    public void setUp() {
        viewModel = new IssueFeedViewModel(true);
    }

    @Test
    public void testFormatPriority_high_returnsRedIndicator() {
        String result = viewModel.formatPriorityCheck("High");
        assertEquals("🔴 High", result);
    }

    @Test
    public void testFormatPriority_medium_returnsYellowIndicator() {
        String result = viewModel.formatPriorityCheck("Medium");
        assertEquals("🟡 Medium", result);
    }

    @Test
    public void testShouldShowEmptyState_emptyList_returnsTrue() {
        List<String> issues = new ArrayList<>();
        assertTrue(viewModel.showEmptyState(issues));
    }

    @Test
    public void testShouldShowEmptyState_nonEmptyList_returnsFalse() {
        List<String> issues = Arrays.asList("Issue1");
        assertFalse(viewModel.showEmptyState(issues));
    }

}