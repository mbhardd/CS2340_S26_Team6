package com.example.sprintproject.viewmodel;

import com.example.sprintproject.model.Issue;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

public final class ChartDataHelper {

    private ChartDataHelper() {
    }

    public static Map<String, Integer> buildCategoryCounts(List<Issue> issues) {
        Map<String, Integer> counts = new LinkedHashMap<>();

        if (issues == null) {
            return counts;
        }

        for (Issue issue : issues) {
            String category = issue.getCategory();

            if (category == null || category.trim().isEmpty()) {
                category = "Unknown";
            }

            counts.put(category, counts.getOrDefault(category, 0) + 1);
        }

        return counts;
    }

    public static Map<String, Integer> buildStatusCounts(List<Issue> issues) {
        Map<String, Integer> counts = new LinkedHashMap<>();

        if (issues == null) {
            return counts;
        }

        for (Issue issue : issues) {
            String status = issue.getStatus();

            if (status == null || status.trim().isEmpty()) {
                status = "Unknown";
            }

            counts.put(status, counts.getOrDefault(status, 0) + 1);
        }

        return counts;
    }

    public static Map<String, Integer> buildIssuesOverTimeCounts(List<Issue> issues) {
        Map<String, Integer> sortedCounts = new TreeMap<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd", Locale.US);

        if (issues == null) {
            return new LinkedHashMap<>();
        }

        for (Issue issue : issues) {
            Long timestamp = issue.getTimestamp();

            if (timestamp == null) {
                continue;
            }

            String dayLabel = dateFormat.format(new Date(timestamp));
            sortedCounts.put(dayLabel, sortedCounts.getOrDefault(dayLabel, 0) + 1);
        }

        return new LinkedHashMap<>(sortedCounts);
    }
}