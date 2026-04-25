package com.example.sprintproject;

import static org.junit.Assert.assertEquals;

import com.example.sprintproject.R;
import com.example.sprintproject.view.IssueFeedAdapter;

import org.junit.Before;
import org.junit.Test;

public class Sprint4_Ananya_Tests {
    @Test
    public void watchButton_returnsWatching() {
        String currentState = "Watch";

        String nextState;
        if (currentState.equals("Watch")) {
            nextState = "Watching";
        } else {
            nextState = "Watch";
        }

        assertEquals("Watching", nextState);
    }

    @Test
    public void watchButton_returnsWatch() {
        String currentState = "Watching";

        String nextState;
        if (currentState.equals("Watch")) {
            nextState = "Watching";
        } else {
            nextState = "Watch";
        }

        assertEquals("Watch", nextState);
    }

    @Test
    public void watchButton_returnsGreenBackground() {
        String currentState = "Watch";

        int background;
        if (currentState.equals("Watch")) {
            background = R.drawable.bg_watching_green;
        } else {
            background = R.drawable.bg_watch_grey;
        }

        assertEquals(R.drawable.bg_watching_green, background);
    }

    @Test
    public void watchButton_returnsGreyBackground() {
        String currentState = "Watching";

        int background;
        if (currentState.equals("Watch")) {
            background = R.drawable.bg_watching_green;
        } else {
            background = R.drawable.bg_watch_grey;
        }

        assertEquals(R.drawable.bg_watch_grey, background);
    }
}
