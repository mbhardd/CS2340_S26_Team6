package com.example.sprintproject.viewmodel;

import com.example.sprintproject.R;

public final class IssueFeedStatusLogic {

    private IssueFeedStatusLogic() {
    }

    public static String normalizeStatus(String status) {
        if (status == null) {
            return "Submitted";
        }

        String normalized = status.trim().toLowerCase();

        switch (normalized) {
        case "submitted":
            return "Submitted";
        case "in_review":
            return "In Review";
        case "not started":
        case "not-started":
        case "open":
            return "Submitted";

        case "in progress":
        case "in_progress":
            return "In Progress";

        case "resolved":
            return "Resolved";
        case "closed":
            return "Closed";
        case "finished":
            return "Finished";

        default:
            return "Submitted";
        }
    }

    public static int getStatusColorRes(String status) {
        switch (normalizeStatus(status)) {
        case "In Progress":
            return R.color.in_progress_gold;
        case "Resolved":
        case "Closed":
            return R.color.finished_green;
        case "Submitted":
        case "In Review":
            return R.color.not_started_red;
        default:
            return R.color.not_started_red;
        }
    }
}