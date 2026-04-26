package com.example.sprintproject;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.example.sprintproject.view.IssueFeedAdapter;

import org.junit.Before;
import org.junit.Test;

public class Ananya_Unit_Tests {
    private IssueFeedAdapter adapter;

    @Before
    public void setUp() {
        adapter = new IssueFeedAdapter(null, null);
    }

    @Test
        public void toggleExpandedPosition_sameItemClicked_collapsesItem() {
            int result = adapter.toggleExpandedPosition(2, 2);
            assertEquals(-1, result);
    }

    @Test
        public void testTimestamp_validTimestamp_returnsFormattedString() {
            Long timestamp = 1711234567890L;
            String result = adapter.formatTimestamp(timestamp);
        assertTrue(result.startsWith("Creation Time:"));
        assertTrue(result.length() > "Creation Time:".length());
    }

    @Test
    public void testCreatorUid_validUid_returnsFormattedUid() {
        String uid = "user123";
        String result = adapter.formatCreatorUid(uid);

        assertEquals("UID:  user123", result);
    }
}
