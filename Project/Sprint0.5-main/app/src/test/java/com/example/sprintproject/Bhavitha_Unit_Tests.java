package com.example.sprintproject;

import static org.junit.Assert.assertEquals;

import com.example.sprintproject.R;
import com.example.sprintproject.viewmodel.IssueFeedStatusLogic;

import org.junit.Test;

public class Bhavitha_Unit_Tests {

    @Test
    public void normalizeStatus_open_returnsNotStarted() {
        assertEquals("Not Started",
                IssueFeedStatusLogic.normalizeStatus("open"));
    }

    @Test
    public void getStatusColorRes_finished_returnsFinishedGreen() {
        assertEquals(R.color.finished_green,
                IssueFeedStatusLogic.getStatusColorRes("Finished"));
    }
}