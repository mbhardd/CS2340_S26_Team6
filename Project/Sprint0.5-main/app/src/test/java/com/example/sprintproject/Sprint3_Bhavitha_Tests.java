package com.example.sprintproject;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.example.sprintproject.model.Issue;
import com.example.sprintproject.model.IssueDetail;
import com.example.sprintproject.viewmodel.ChartDataHelper;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Sprint3_Bhavitha_Tests {

    @Test
    public void testBuildCategoryCounts_basicAggregation() {
        List<Issue> issues = new ArrayList<>();

        issues.add(new Issue(new IssueDetail("Leak", "Maintenance", "High", "AB",
                "Building A", "Water leak"), "u1", 1L));
        issues.add(new Issue(new IssueDetail("Broken Light", "Maintenance", "Low", "CD",
                "Building B", "Light out"), "u2", 2L));
        issues.add(new Issue(new IssueDetail("Unsafe Stairs", "Safety", "High", "EF",
                "Building C", "Loose railing"), "u3", 3L));

        Map<String, Integer> result = ChartDataHelper.buildCategoryCounts(issues);

        assertEquals(2, (int) result.get("Maintenance"));
        assertEquals(1, (int) result.get("Safety"));
    }

    @Test
    public void testBuildStatusCounts_handlesUnknownStatus() {
        List<Issue> issues = new ArrayList<>();

        issues.add(new Issue(new IssueDetail("Leak", "Maintenance", "High", "AB",
                "Building A", "Water leak"), "u1", 1L));
        issues.add(new Issue(new IssueDetail("Broken Light", "Maintenance", "Low", "CD",
                "Building B", "Light out"), "u2", 2L));

        Map<String, Integer> result = ChartDataHelper.buildStatusCounts(issues);

        assertTrue(result.containsKey("Unknown"));
        assertEquals(2, (int) result.get("Unknown"));
    }
}
