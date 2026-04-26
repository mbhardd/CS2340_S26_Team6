package com.example.sprintproject;

import static org.junit.Assert.assertEquals;

import com.example.sprintproject.R;
import com.example.sprintproject.viewmodel.IssueFeedStatusLogic;

import org.junit.Test;

public class Bhavitha_Unit_Tests {

    @Test
    public void normalizeStatus_open_returnsSubmitted() {
        assertEquals("Submitted", IssueFeedStatusLogic.normalizeStatus("open"));
    }

    @Test
    public void normalizeStatus_inProgress_returnsInProgress() {
        assertEquals("In Progress", IssueFeedStatusLogic.normalizeStatus("in_progress"));
    }
}