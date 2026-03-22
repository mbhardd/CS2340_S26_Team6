package com.example.sprintproject;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import android.view.View;

import com.example.sprintproject.model.Issue;
import com.example.sprintproject.view.IssueFeedAdapter;
import com.example.sprintproject.viewmodel.IssueFeedViewModel;

import org.junit.Before;
import org.junit.Test;

public class Ananya_Unit_Tests {
    private IssueFeedAdapter adapter;
    private Issue issue;
    private View itemView;

    @Before
    public void setUp() {
        adapter = new IssueFeedAdapter(null);
    }

    @Test
        public void toggleExpandedPosition_sameItemClicked_collapsesItem() {
            int result = adapter.toggleExpandedPosition(2, 2);
            assertEquals(-1, result);
    }

    @Test
        public void formatTimestamp_validTimestamp_returnsFormattedString() {
            Long timestamp = 1711234567890L;
            String result = adapter.formatTimestamp(timestamp);
        assertTrue(result.startsWith("Creation Time:"));
        assertTrue(result.length() > "Creation Time:".length());
    }

}
